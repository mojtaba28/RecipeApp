package com.example.recipeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

@HiltAndroidApp
open class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        setFontWithCalligraphy()

    }

    private fun setFontWithCalligraphy(){
        ViewPump.init(
            ViewPump.builder().addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/atlas_regular.ttf")
                        .build()
                )
            ).build()
        )
    }
}