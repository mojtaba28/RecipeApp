package com.example.recipeapp.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.todkars.shimmer.ShimmerRecyclerView
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class RecipeActivity:AppCompatActivity(){

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context))
    }


}