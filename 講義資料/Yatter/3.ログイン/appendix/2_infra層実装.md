# [前の資料](./1_domain層実装.md)

# ログイン画面のinfra層実装
ログイン画面のinfra層実装を行います。  

今回infra層で実装する内容は次の2つです。  
- TokenPreferencesの実装
- YatterApi#loginの実装
- LoginServiceImplの実装
- CheckLoginServiceImplの実装

クラス図では次にあたります。  

![login_class_infra](../../image/3/login_class_infra.png)

## TokenPreferencesの実装

Yatterアプリでは、ログインAPI実行時のレスポンスを認証情報として利用します。  
現状のログインAPIは、ユーザー名が認証情報(アクセストークン)としてレスポンスに含まれています。  

APIから取得した認証情報をアプリ実行デバイスに保存するために、`TokenPreferences`を実装します。  
`TokenPrefrences`では、内部実装として[`SharedPreferences`](https://developer.android.com/training/data-storage/shared-preferences?hl=ja)というものを利用します。  
`SharedPreferences`とは、Key-Value形式でデバイス内にデータを保存するための仕組みです。  
アプリを終了した後も残り続け、アプリデータ削除もしくはアンインストールしない限りデータが残るためサーバーに保存しないような簡易的なアプリの設定を扱うために用いられます。  

`TokenPreferences`クラスを定義します。  
`SharedPreferences`ではPreferences名とKey-Value形式で扱うためのキー情報が必要なため固定値として持っておきます。  

`SharedPreferences`のインスタンスは`Context`から取得できるため引数に`Context`も追加します。  

```Kotlin
class TokenPreferences(context: Context) {
  companion object {
    private const val PREF_NAME = "token"
    private const val KEY_ACCESS_TOKEN = "access_token"
  }
}
```

続いて、contextからインスタンスを取得します。  
`Context.MODE_PRIVATE`とというモード指定をしていますが現状はこのモードしかサポートされていないため特に気にする必要はありません。  

```Kotlin
class TokenPreferences(context: Context) {
  ...
  private val sharedPreferences = context.getSharedPreferences(
    PREF_NAME,
    Context.MODE_PRIVATE
  )
}
```

SharedPreferencesに値を書き込み・取り出し・削除するためのメソッドを実装します。  

値を取り出すときは、`getString`のように`get~`なメソッド呼び出しを、書き込みや削除の場合は`edit`のブロックの中で`putString`のように`put~`なメソッド呼び出しで保存、`clear`メソッド呼び出しで削除を行います。

```Kotlin
class TokenPreferences(...) {
  ...
  fun getAccessToken(): String? = sharedPreferences.getString(
    KEY_ACCESS_TOKEN,
    ""
  )

  fun putAccessToken(token: String?) {
    sharedPreferences.edit {
      putString(
        KEY_ACCESS_TOKEN,
        token
      )
    }
  }

  fun clear() = sharedPreferences.edit {
    clear()
  }
}
```

ログインユーザーのアクセストークンを記録する`TokenPreferences`を実装できました。  

## YatterApi#loginの実装

続いてはYatterApi#loginの実装です。  

基本的な流れは`YatterApi#getPublicTimeline()`で行なった時と同様な流れです。  
ログインに必要なAPIは以下です。  

```
POST /auth/login
```

このAPIでは以下のようなリクエストボディとレスポンスが定義されています。  

```json
// リクエストボディ
{
  "username": "john",
  "password": "P@ssw0rd"
}

// レスポンス
{
  "username": "john"
}
```

リクエストボディで、ログインしたいユーザーの`username`と`password`を受け取り、ログイン可能であれば`usernamme`がレスポンスとして返されます。  
今回のYatter開発では`username`がAPI実行時に必要なアクセストークンの役割を担っていますので、Androidアプリ実装上はアクセストークンとして扱います。  

リクエストボディとレスポンスに必要なJsonクラスを定義します。  
今回は、`LoginRequestBodyJson`・`LoginResponseJson`という名前にしました。  

それぞれのJsonクラスをSwaggerのJson定義に合わせて実装します。  

```kotlin
// LoginRequestBodyJson.kt
data class LoginRequestBodyJson(
  val username: String,
  val password: String,
)

// LoginResponseJson.kt
data class LoginResponseJson(
  @Json(name = "username") val accessToken: String,
)
```

`LoginResponseJson`で、Jsonでは`username`として定義されている部分を`accessToken`として扱うように定義しています。  
これによりAndroidアプリ実装のコード上ではアクセストークンとして扱うことができます。  
アプリでの利用上の意味合いはアクセストークンなため、それに合わせた名前の方がわかりやすく、さらに今後`username`以外の値がアクセストークンとなっても修正の範囲をJsonクラスのみに留めることができます。  

Jsonクラスが定義できたら、`YatterApi`の実装です。  

ログインAPIは以下のように定義されているため、合わせた実装を行います。
```
POST /auth/login
```

```kotlin
// YatterApi.kt
@POST("auth/login")
suspend fun login(
  @Body requestBody: LoginRequestBodyJson,
): LoginResponseJson
```

これでYatterApiの定義は完了したため、呼び出すことでログインAPIと通信を行うことができます。  

## LoginServiceImplの実装
ログイン処理を実装する`LoginServiceImpl`の実装に移ります。  

まずは、`LoginServiceImpl`ファイルの作成です。  
domain層実装時に定義した`LoginService`インターフェースを継承してメソッドをオーバーライドします。  

```Kotlin
package com.dmm.bootcamp.yatter2024.infra.domain.service

class LoginServiceImpl(
  private val tokenPreferences: TokenPreferences,
) : LoginService {
  override suspend fun execute(
    username: Username,
    password: Password,
  ) {
    TODO("Not yet implemented")
  }
}
```

DomainServiceは純粋な振る舞いを定義しますので、`LoginService`では渡されたユーザー名とパスワードをもとにログイン処理を行うことに注力して、ユーザー名とパスワードの値が問題ないかという部分の処理は呼び出し元で行うようにします。  

Yatterではログイン処理として、APIにユーザー名とパスワードを送信し、アクセストークン（現在はユーザー名）がレスポンスとしてエラー無く返るとログイン完了としています。  
そのため、`LoginServiceImpl`の処理としては、
1. リクエストボディに必要なJsonクラスをインスタンス化
2. `YatterAPI`でAPIと通信
3. アプリ終了後でも再ログインを不要にするためにレスポンスのトークンを`TokenPreferences`に保存

という手順にします。  

今後、API接続してさらに厳格なログイン処理を行う場合は`LoginServiceImpl`の処理を変更してください。  

```Kotlin
override suspend fun execute(,,,) {
  val requestJson = LoginRequestBodyJson( // 1
    username.value,
    password.value,
  )
  val response = yatterApi.login(requestJson) // 2

  tokenPreferences.putAccessToken(response.accessToken) // 3
}
```

`LoginServiceImpl`も単体テストを記述してみましょう。  

<details>
<summary>LoginServiceImplのテスト例</summary>

```Kotlin
class LoginServiceImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val tokenPreferences = mockk<TokenPreferences>()
  private val subject = LoginServiceImpl(
    yatterApi = yatterApi,
    tokenPreferences = tokenPreferences,
  )

  @Test
  fun loginSuccess() = runTest {
    val username = Username("username")
    val password = Password("Password1%")

    val accessToken = "token"
    justRun {
      tokenPreferences.putAccessToken(any())
    }

    coEvery {
      yatterApi.login(any())
    } returns LoginResponseJson(accessToken)

    subject.execute(username, password)

    coVerify {
      yatterApi.login(
        LoginRequestBodyJson(
          username.value,
          password.value,
        )
      )
    }

    coVerify {
      tokenPreferences.putAccessToken(accessToken)
    }
  }
}
```
</details>

つづいて、`CheckLoginServiceImpl`クラスの実装です。  
次の様に実装します。

```Kotlin
// infra/domain/service/CheckLoginServiceImpl.kt

class CheckLoginServiceImpl(
  private val tokenPreferences: TokenPreferences
) : CheckLoginService {
  override suspend fun execute(): Boolean {
    return tokenPreferences.getAccessToken().isNullOrEmpty().not()
  }
}

```

CheckLoginServiceImplの単体テストも書いてみましょう。
今回のテストでは次の観点の確認ができると良いです。  

- TokenPreferencesに値が保存されていなければ(null or 空文字)false
- TokenPreferencesに値が保存されていればtrue

<details>
<summary>CheckLoginServiceImplのテスト実装例</summary>

```Kotlin
class CheckLoginServiceImplSpec {
  private val tokenPreferences = mockk<TokenPreferences>()
  private val subject = CheckLoginServiceImpl(tokenPreferences)

  @Test
  fun getTrueWhenSavedUsername() = runTest {
    val accessToken = "accessToken"

    coEvery {
      tokenPreferences.getAccessToken()
    } returns accessToken

    val result: Boolean = subject.execute()

    assertThat(result).isTrue()

    verify {
      tokenPreferences.getAccessToken()
    }
  }

  @Test
  fun getFalseWhenUnsavedUsername() = runTest {
    val accessToken = ""

    coEvery {
      tokenPreferences.getAccessToken()
    } returns accessToken

    val result: Boolean = subject.execute()

    assertThat(result).isFalse()

    verify {
      tokenPreferences.getAccessToken()
    }
  }
}
```

</details>

---

ここまでで`TokenPreferences`と`LoginService`、`CheckLoginService`とが属するinfra層の実装は完了しました。  

続いてusecase層の実装に移ります。  

# [次の資料](./3_usecase層実装.md)
