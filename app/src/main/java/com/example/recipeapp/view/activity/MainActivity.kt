package com.example.recipeapp.view.activity

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.utils.RecipeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : RecipeActivity() {
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var navHost:NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost=supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        setBottomNavigation()
        onMenuBtnClicked()

    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    fun onMenuBtnClicked(){
        binding.mainFabMenu.setOnClickListener {
            navHost.navController.navigate(R.id.actionToMenu)
        }
    }

    fun setBottomNavigation(){
        binding.mainBottomNav.background=null
        binding.mainBottomNav.setupWithNavController(navHost.navController)
        navHost.navController.addOnDestinationChangedListener{ _ , destination, _ ->
            when(destination.id){
                R.id.splashFragment -> bottomViewVisibility(false)
                R.id.registerFragment -> bottomViewVisibility(false)
                else -> bottomViewVisibility(true)
            }

        }
    }

    private fun bottomViewVisibility (isVisible: Boolean) {
        binding.apply {
            if (isVisible) {
                mainBottomAppbar.isVisible = true
                mainFabMenu.isVisible = true
            } else {
                mainBottomAppbar.isVisible = false
                mainFabMenu.isVisible = false
            }
        }
    }

    private fun setNavHost(){
        navHost=supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
    }
}