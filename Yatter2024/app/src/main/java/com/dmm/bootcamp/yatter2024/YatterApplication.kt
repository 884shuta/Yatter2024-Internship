package com.dmm.bootcamp.yatter2024

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import com.dmm.bootcamp.yatter2024.di.domainImplModule
import com.dmm.bootcamp.yatter2024.di.infraModule
import com.dmm.bootcamp.yatter2024.di.useCaseModule
import com.dmm.bootcamp.yatter2024.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YatterApplication : Application(), ImageLoaderFactory {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      // Reference Android context
      androidContext(this@YatterApplication)
      modules(
        modules = listOf(
          domainImplModule,
          infraModule,
          useCaseModule,
          viewModelModule,
        )
      )
    }
  }

  override fun newImageLoader(): ImageLoader {
    return ImageLoader.Builder(applicationContext)
      .crossfade(true)
      .logger(DebugLogger())
      .build()
  }
}