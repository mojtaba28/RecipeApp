package com.example.recipeapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRegisterBinding
import com.example.recipeapp.models.data_class.register.BodyRegister
import com.example.recipeapp.utils.CheckNetworkStatus
import com.example.recipeapp.utils.Const.API_KEY_VALUE
import com.example.recipeapp.utils.NetworkRequestState
import com.example.recipeapp.utils.isValidEmail
import com.example.recipeapp.utils.showSnackBar
import com.example.recipeapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    //Binding
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var  body:BodyRegister

    @Inject
    lateinit var checkNetworkStatus:CheckNetworkStatus

    private val viewModel: RegisterViewModel by viewModels()
    private var email=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            coverImg.load(R.drawable.register_logo)
            getEmail()
            register()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getEmail(){
        binding.apply {
            emailEdt.addTextChangedListener(){

                if (isValidEmail(it.toString())) {
                    email = it.toString()
                    emailTxtLay.error = ""
                } else {
                    emailTxtLay.error = getString(R.string.email_is_not_valid)
                }

            }
        }
    }
    fun register(){

        binding.apply {
            registerBtn.setOnClickListener{

                val firstname = nameEdt.text.toString()
                val lastName = lastNameEdt.text.toString()
                val username = usernameEdt.text.toString()
                //Body
                body.email = email
                body.firstName = firstname
                body.lastName = lastName
                body.username = username

                lifecycleScope.launchWhenStarted {
                    checkNetworkStatus.checkNetworkAvailability().collect(){
                        if (it) viewModel.register(API_KEY_VALUE,body)
                        else root.showSnackBar(getString(R.string.check_your_network))
                    }
                }

                observeRegisterData()
            }
        }
    }

    private fun observeRegisterData(){
        viewModel.registerLiveData.observe(viewLifecycleOwner){
            when (it){
                is NetworkRequestState.Loading->{}
                is NetworkRequestState.Success->{
                    it.data?.let { data->
                        viewModel.saveData(data.username.toString(),data.hash.toString())
                        findNavController().popBackStack(R.id.registerFragment,true)
                        findNavController().navigate(R.id.actionToRecipe)
                    }

                }
                is NetworkRequestState.Error->{
                    binding.root.showSnackBar(it.message.toString())
                }
            }
        }
    }

}