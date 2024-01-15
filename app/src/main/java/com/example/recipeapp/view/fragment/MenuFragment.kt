package com.example.recipeapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentMenuBinding
import com.example.recipeapp.viewmodel.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn


@AndroidEntryPoint
class MenuFragment : BottomSheetDialogFragment() {

    //Binding
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    //View Model
    private val viewModel:MenuViewModel by viewModels()
    //Variables
    private var chipCounter=1
    private var chipMealTitle = ""
    private var chipMealId = 0
    private var chipDietTitle = ""
    private var chipDietId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setupChip(viewModel.mealsList(), mealChipGroup)
            setupChip(viewModel.dietsList(), dietChipGroup)
            readData()
            onChipGroupClick()
            saveData()



        }
    }

    private fun readData() {
        viewModel.readData().asLiveData().observe(viewLifecycleOwner){
            binding.apply {
                chipMealTitle=it.meal
                chipDietTitle = it.diet
                updateChip(it.mealId,mealChipGroup)
                updateChip(it.dietId,dietChipGroup)
            }


        }
    }

    private fun saveData() {
        binding.apply {
            submitBtn.setOnClickListener {
                viewModel.saveData(chipMealTitle, chipMealId, chipDietTitle, chipDietId)
                findNavController().navigate(MenuFragmentDirections.actionMenuToRecipe().setIsUpdateData(true))
            }
        }
    }

    private fun onChipGroupClick() {
        binding.apply {
            mealChipGroup.setOnCheckedStateChangeListener{ group,checkedIds->
                var chip:Chip
                checkedIds.forEach {
                    chip=group.findViewById(it)
                    chipMealTitle=chip.text.toString().lowercase()
                    chipMealId=it
                }
            }

            dietChipGroup.setOnCheckedStateChangeListener{ group,checkedIds->
                var chip:Chip
                checkedIds.forEach {
                    chip=group.findViewById(it)
                    chipDietTitle=chip.text.toString().lowercase()
                    chipDietId=it

                }
            }

        }
    }

    private fun setupChip(list: MutableList<String>, view: ChipGroup) {
        list.forEach {
            val chip = Chip(requireContext())
            val drawable = ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.MenuChip)
            chip.setChipDrawable(drawable)
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            chip.id = chipCounter++
            chip.text = it
            view.addView(chip)
        }
    }

    private fun updateChip(id:Int,view:ChipGroup){
        if (id!=0)  view.findViewById<Chip>(id).isChecked=true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}