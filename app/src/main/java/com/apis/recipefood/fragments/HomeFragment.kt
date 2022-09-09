package com.apis.recipefood.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.apis.recipefood.R
import com.apis.recipefood.activites.CategoryMealsActivity
import com.apis.recipefood.activites.DetailMealActivity
import com.apis.recipefood.adapter.CategoriesHomeAdapter
import com.apis.recipefood.adapter.MealAdapter
import com.apis.recipefood.util.Constants
import com.apis.recipefood.databinding.FragmentHomeBinding
import com.apis.recipefood.fragments.bottomsheet.BottomSheetFragment
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private val mealMvvm : MealViewModel by viewModels()
    private lateinit var randomMeal: Meal
    private lateinit var mealAdapter: MealAdapter
    private lateinit var categoriesHomeAdapter: CategoriesHomeAdapter

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
        observerRandomMeal()
        onRandomMealClick()

        //PM
        mealMvvm.getPopularItems()
        observerPopularItems()
        onPopularItemClick()
        onPopularLongItemClick()

        //CM
        mealMvvm.getCategories()
        observerCategories()
        onCategoryClick()

        //Search
        onSearchIconClick()

        //Loading
        showLoadingCase()

    }

    private fun showLoadingCase() {
        binding.apply {
            loadingAnimation.visibility=View.VISIBLE

            llHeader.visibility= View.INVISIBLE
            tvQueTeGustariaComerHoy.visibility=View.INVISIBLE
            cardRandomMeal.visibility=View.INVISIBLE
            tvPopulares.visibility=View.INVISIBLE
            rvMealsPopular.visibility=View.INVISIBLE
            tvCategories.visibility=View.INVISIBLE
            cardCategoriasMeals.visibility=View.INVISIBLE
            rvMealsCategories.visibility=View.INVISIBLE
            ivSearch.visibility=View.INVISIBLE
        }

    }

    private fun onResponseCase() {
        binding.apply {
            loadingAnimation.visibility=View.INVISIBLE

            llHeader.visibility= View.VISIBLE
            tvQueTeGustariaComerHoy.visibility=View.VISIBLE
            cardRandomMeal.visibility=View.VISIBLE
            tvPopulares.visibility=View.VISIBLE
            rvMealsPopular.visibility=View.VISIBLE
            tvCategories.visibility=View.VISIBLE
            cardCategoriasMeals.visibility=View.VISIBLE
            rvMealsCategories.visibility=View.VISIBLE
            ivSearch.visibility=View.VISIBLE
        }

    }

    //Click en icono de buscar
    private fun onSearchIconClick() {

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

        }

    }


    //RecyclerView
    private fun categoriesRecyclerView() {
        categoriesHomeAdapter = CategoriesHomeAdapter()
        binding.rvMealsCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesHomeAdapter

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
        mealMvvm.observerCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categories.forEach { _ ->
                onResponseCase()
                categoriesHomeAdapter.setCategoryList(categories)

            }
        }
    }



    private fun onCategoryClick() {
        categoriesHomeAdapter.onItemClick = { category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(Constants.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }


    //<!-- COMIDA POPULARES -->

    private fun observerPopularItems() {
       mealMvvm.observerPopularItemsLiveData().observe(viewLifecycleOwner)
       { mealList ->
           onResponseCase()
           mealAdapter.setMeals(categoryMeals = mealList as ArrayList<Meal>)

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

    private fun onPopularLongItemClick() {
      mealAdapter.onItemLongClick={ meal ->
          val bottomSheetFragment=BottomSheetFragment.newInstance(meal.idMeal)
          bottomSheetFragment.show(childFragmentManager,"Meal info")

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
        mealMvvm.observerRamdonMealLiveData().observe(viewLifecycleOwner) { meal ->
            onResponseCase()
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.ivRandomMeal)

            this.randomMeal = meal
        }
    }

}