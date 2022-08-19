package com.apis.recipefood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.apis.recipefood.activites.DetailMealActivity
import com.apis.recipefood.adapter.CategoriesAdapter
import com.apis.recipefood.adapter.MealAdapter
import com.apis.recipefood.constants.Constants
import com.apis.recipefood.databinding.FragmentHomeBinding
import com.apis.recipefood.pojo.CategoryMeals
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private  val mealViewModel :MealViewModel by viewModels()
    private lateinit var randomMeal:Meal
    private lateinit var mealAdapter: MealAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealAdapter = MealAdapter()

        //RV
        popularItemsRecyclerView()
        categoriesRecyclerView()

        //RM
       mealViewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        //PM
        mealViewModel.getPopularItems()
        observerPopularItems()
        onPopularItemClick()

        //CM
        mealViewModel.getCategories()
        observerCategories()




    }
    //RecyclerView
    private fun categoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvMealsCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter

        }
    }

    private fun popularItemsRecyclerView() {
        binding.rvMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = mealAdapter


        }
    }

    //<!-- CATEGORIAS -->
    private fun observerCategories() {
        mealViewModel.observerCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categories.forEach { _ ->
                categoriesAdapter.setCategoryList(categories)

            }
        }
    }


    //<!-- COMIDA POPULARES -->

    private fun observerPopularItems() {
       mealViewModel.observerPopularItemsLiveData().observe(viewLifecycleOwner)
       { mealList ->
           mealAdapter.setMeals(mealList = mealList as ArrayList<CategoryMeals>)

       }


    }

    private fun onPopularItemClick() {
        mealAdapter.onItemClick = { meal ->
            val intent = Intent(activity,DetailMealActivity::class.java)
            intent.putExtra(Constants.MEAL_ID,meal.idMeal)
            intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
            intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }






    //<!--COMIDA ALEATORIA -->

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