package com.apis.recipefood.activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        showLoadingCase()




      categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(Constants.CATEGORY_NAME)!!)

        categoryMealsViewModel.observerMealsLiveData().observe(this, Observer { mealsList ->
            onResponseCase()
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


    private fun showLoadingCase() {
        binding.apply {
            loadingAnimation.visibility= View.VISIBLE

            tvCategoryCount.visibility= View.INVISIBLE
            rvMeals.visibility= View.INVISIBLE

        }

    }

    private fun onResponseCase() {
        binding.apply {
            loadingAnimation.visibility= View.INVISIBLE

            tvCategoryCount.visibility= View.VISIBLE
            rvMeals.visibility= View.VISIBLE


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