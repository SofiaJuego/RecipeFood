package com.apis.recipefood.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.apis.recipefood.adapter.CategoryMealsAdapter
import com.apis.recipefood.constants.Constants
import com.apis.recipefood.databinding.ActivityCategoryMealsBinding
import com.apis.recipefood.viewmodel.MealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private val categoryMealsViewModel: MealViewModel by viewModels()
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
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}