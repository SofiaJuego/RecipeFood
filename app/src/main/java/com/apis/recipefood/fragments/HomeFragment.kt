package com.apis.recipefood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.apis.recipefood.R
import com.apis.recipefood.databinding.FragmentHomeBinding
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private  val mealViewModel :MealViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       mealViewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()


    }
    //navegacion en imagen random donde lleva a activity de detalles de la receta
    private fun onRandomMealClick() {
        binding.ivRandomMeal.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_detailMealActivity)
        }

    }

    private fun observerRandomMeal() {
        mealViewModel.observerRamdonMealLiveData().observe(viewLifecycleOwner) { t ->
            Glide.with(this@HomeFragment)
                .load(t!!.strMealThumb)
                .into(binding.ivRandomMeal)
        }
    }

}