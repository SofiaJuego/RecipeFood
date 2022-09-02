package com.apis.recipefood.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.apis.recipefood.adapter.CategoryMealsAdapter
import com.apis.recipefood.util.Constants
import com.apis.recipefood.databinding.ActivityCategoryMealsBinding
import com.apis.recipefood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    //Instacia de viewmodel
    private val  categoryMealsViewModel:MealViewModel by viewModels()
    lateinit var categoryMealsAdapter:CategoryMealsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealsRecyclerView()

      categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(Constants.CATEGORY_NAME)!!)

        categoryMealsViewModel.observerMealsLiveData().observe(this) { mealsList ->

            binding.tvCategoryCount.text="Result: ${mealsList!!.size}"
            categoryMealsAdapter.setMealsList(mealsList)


        }


    }

    private fun mealsRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}