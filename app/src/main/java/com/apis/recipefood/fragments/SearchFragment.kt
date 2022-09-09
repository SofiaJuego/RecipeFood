package com.apis.recipefood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apis.recipefood.adapter.SearchAdapter
import com.apis.recipefood.databinding.FragmentSearchBinding
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

        //RC
        searchRecyclerView()

        observeSearchLiveData()
        click()
        //onSearchClickDetail()

        binding.ivSearch.setOnClickListener {searchMeals()}








    }

    private fun searchMeals() {
        val searchQuery = binding.etSearch.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }else{
            binding.tvNoMealsResult.visibility = View.VISIBLE
        }
    }

   /* private fun onSearchClickDetail() {
            adapter.onItemClick = { meal ->
                val intent = Intent(activity,DetailMealActivity::class.java)
                intent.putExtra(Constants.MEAL_ID,meal.idMeal)
                intent.putExtra(Constants.MEAL_NAME,meal.strMeal)
                intent.putExtra(Constants.MEAL_THUMB,meal.strMealThumb)
                startActivity(intent)
            }


    }*/

    private fun observeSearchLiveData() {
        viewModel.observerSearchMealLiveData().observe(viewLifecycleOwner) { mealList ->
            adapter.differ.submitList(mealList)
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

    //buscar sin tener que apretar icono
    private fun click (){
        var searchJob:Job?=null
        binding.etSearch.addTextChangedListener{ search ->
            searchJob?.cancel()
            searchJob=lifecycleScope.launch{
                delay(0)
                viewModel.searchMeal(search.toString())
            }

        }}


}