package com.example.recipeapp.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.models.data_class.recipes.ResponseRecipes
import com.example.recipeapp.utils.*
import com.example.recipeapp.view.adapter.PopularRecipesAdapter
import com.example.recipeapp.view.adapter.RecentRecipesAdapter
import com.example.recipeapp.viewmodel.RecipeViewModel
import com.example.recipeapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var popularRecipesAdapter: PopularRecipesAdapter
    @Inject
    lateinit var recentRecipesAdapter: RecentRecipesAdapter
    private val recipeViewModel:RecipeViewModel by viewModels()
    private var autoScrollIndex = 0

    private val registerViewModel:RegisterViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUsername()
        setPopularRecipes()
        setRecentRecipes()
    }



    private fun showUsername(){

         lifecycleScope.launch {
             registerViewModel.readData().collect{

                 binding.usernameTxt.text="${getString(R.string.hello)} ${it.username} ${getEmojiByUnicode()}"
             }
         }


    }

    private fun getEmojiByUnicode(): String {
        return String(Character.toChars(0x1f44b))
    }



    private fun setPopularRecipes(){
        recipeViewModel.getPopularRecipes(popularRecipesQuery())
        binding.apply {
            setPopularRecyclerView()
            recipeViewModel.getRecipesFromDb().observe(viewLifecycleOwner){
               //Call from cache database
                if (it.isNotEmpty()){
                    it[Const.POPULAR_CACHE].response.results?.let {result->
                        setupLoading(false, binding.popularRv)
                        fillPopularAdapter(result.toMutableList())
                    }
                }else{
                    //Call from API
                    recipeViewModel.popularLiveData.observe(viewLifecycleOwner){

                        when(it){
                            is NetworkRequestState.Loading ->{
                                setupLoading(true,popularRv)
                            }

                            is NetworkRequestState.Success->{
                                setupLoading(false,popularRv)
                                it.data?.let {data ->
                                    if(data.results!!.isNotEmpty()) fillPopularAdapter(data.results.toMutableList())
                                }
                            }

                            is NetworkRequestState.Error -> {
                                setupLoading(false, popularRv)
                                binding.root.showSnackBar(it.message!!)
                            }
                        }
                    }
                }
            }

        }

    }

    private fun setRecentRecipes() {
        recipeViewModel.getRecentRecipes(recipeViewModel.recentQueries() )
        binding.apply {
            setRecentRecyclerView()
            recipeViewModel.getRecipesFromDb().observe(viewLifecycleOwner){
                if (it.isNotEmpty() && it.size>1 && !args.isUpdateData){
                    it[Const.RECENT_CACHE].response.results?.let {result ->
                        setupLoading(false, binding.recipesRv)
                        recentRecipesAdapter.setData(result)

                    }
                }else {
                    recipeViewModel.recentLiveData.observe(viewLifecycleOwner){

                        when(it){
                            is NetworkRequestState.Loading ->{
                                setupLoading(true,recipesRv)
                            }

                            is NetworkRequestState.Success->{
                                setupLoading(false,recipesRv)
                                it.data?.let {data ->
                                    if(data.results!!.isNotEmpty()) recentRecipesAdapter.setData(data.results)
                                }
                            }

                            is NetworkRequestState.Error -> {
                                setupLoading(false, recipesRv)
                                binding.root.showSnackBar(it.message!!)
                            }
                        }
                    }
                }
            }

        }
    }

    fun setRecentRecyclerView(){
        binding.recipesRv.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recipesRv.adapter=recentRecipesAdapter
        recentRecipesAdapter.setOnItemClickListener {

        }
    }

    private fun fillPopularAdapter(result: MutableList<ResponseRecipes.Result>) {
        popularRecipesAdapter.setData(result)
        autoScrollPopularRecipes(result)
    }

    private fun setPopularRecyclerView() {
        val snapHelper = LinearSnapHelper()
        binding.popularRv.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.popularRv.adapter=popularRecipesAdapter
        snapHelper.attachToRecyclerView(binding.popularRv)
        popularRecipesAdapter.setOnItemClickListener {

        }
    }

    private fun autoScrollPopularRecipes(lists: List<ResponseRecipes.Result>){
        lifecycleScope.launch {
            repeat(Const.REPEAT_TIME){
                delay(Const.DELAY_TIME)

                if (autoScrollIndex<lists.size) autoScrollIndex+=1
                else autoScrollIndex=0

                binding.popularRv.smoothScrollToPosition(autoScrollIndex)

            }

        }
    }




}