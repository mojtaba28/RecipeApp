package com.example.recipeapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recipeapp.BuildConfig
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentSplashBinding
import com.example.recipeapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : Fragment() {


    private var _binding:FragmentSplashBinding?=null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    //inject viewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackgroundImage()
        setVersionText()
        setAutoNavigate()
    }

    fun setVersionText(){
        binding.versionTxt.text= "${getString(R.string.version)} : ${BuildConfig.VERSION_NAME}"
    }

    fun setBackgroundImage(){
        binding.bgImg.load(R.drawable.bg_splash)
    }

    fun setAutoNavigate(){

        lifecycleScope.launch {
            delay(6000)
            // check user info
            viewModel.readData().collect{

                findNavController().popBackStack(R.id.splashFragment, true)
                if (it.username.isNotEmpty()) {
                    findNavController().navigate(R.id.actionToRecipe)
                } else {
                    findNavController().navigate(R.id.actionToRegister)
                }
            }

        }
    }


}