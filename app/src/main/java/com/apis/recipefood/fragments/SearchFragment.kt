package com.apis.recipefood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apis.recipefood.activites.DetailMealActivity
import com.apis.recipefood.adapter.SearchAdapter
import com.apis.recipefood.databinding.FragmentSearchBinding
import com.apis.recipefood.util.Constants
import com.apis.recipefood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel:MealViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SearchAdapter()

        searchRecyclerView()
        observeSearchLiveData()
        onSearchClickDetail()

        binding.ivSearch.setOnClickListener {searchMeals()}

        //buscar sin tener que apretar icono
        var searchJob:Job?=null
        binding.etSearch.addTextChangedListener{ search ->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch{
                delay(100)
                viewModel.searchMeal(search.toString())
            }

        }




    }

    private fun onSearchClickDetail() {
            adapter.onItemClick = { meal ->
                val intent = Intent(activity,DetailMealActivity::class.java)
                intent.putExtra(Constants.MEAL_ID,meal.idMeal)
                intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
                intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
                startActivity(intent)
            }


    }


    private fun observeSearchLiveData() {
        viewModel.observerSearchMealLiveData().observe(viewLifecycleOwner) { mealList ->
           adapter.setMealsearch(mealList)
        }
    }

    //validacion
    private fun searchMeals() {
        val search = binding.etSearch.text.toString()
        if (search.isNotEmpty()){
            viewModel.searchMeal(search)
        } else{
            binding.tvNoMealsResult.visibility = View.VISIBLE
        }
    }
    //RecyclerView
    private fun searchRecyclerView() {
        adapter= SearchAdapter()
        binding.rvSearch.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=adapter
        }
    }


}