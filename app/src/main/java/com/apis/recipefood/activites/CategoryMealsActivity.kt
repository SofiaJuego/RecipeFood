package com.apis.recipefood.activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.apis.recipefood.adapter.CategoryMealsAdapter
import com.apis.recipefood.util.Constants
import com.apis.recipefood.databinding.ActivityCategoryMealsBinding
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var detailCategoryMeal: Meal
    //Instacia de viewmodel
    private val  categoryMealsViewModel:MealViewModel by viewModels()
    private lateinit var categoryMealsAdapter:CategoryMealsAdapter


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()

        onMealItemClick()




      categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(Constants.CATEGORY_NAME)!!)

        categoryMealsViewModel.observerMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text="Count: ${mealsList.size}"
            categoryMealsAdapter.setCategoriesList(mealsList)
            })


    }


    private fun onMealItemClick() {
       categoryMealsAdapter.onItemClick = {
           val intent = Intent(this,DetailMealActivity::class.java)
           intent.putExtra(Constants.MEAL_ID,it.idMeal)
           intent.putExtra(Constants.MEAL_NAME,it.strMeal)
           intent.putExtra(Constants.MEAL_THUMB,it.strMealThumb)
           startActivity(intent)
       }

    }




    //RecyclerView
    private fun recyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }




}