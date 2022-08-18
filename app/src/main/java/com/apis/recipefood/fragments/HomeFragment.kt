package com.apis.recipefood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.apis.recipefood.activites.DetailMealActivity
import com.apis.recipefood.constants.Constants
import com.apis.recipefood.databinding.FragmentHomeBinding
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private  val mealViewModel :MealViewModel by viewModels()
    private lateinit var randomMeal:Meal

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
            val intent = Intent(activity,DetailMealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID,randomMeal.idMeal)
            intent.putExtra(Constants.MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(Constants.MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)

        }

    }

    private fun observerRandomMeal() {
        mealViewModel.observerRamdonMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.ivRandomMeal)

            this.randomMeal = meal
        }
    }

}