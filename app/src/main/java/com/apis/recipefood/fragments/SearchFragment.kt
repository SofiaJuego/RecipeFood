package com.apis.recipefood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.apis.recipefood.activites.DetailMealActivity
import com.apis.recipefood.adapter.SearchAdapter
import com.apis.recipefood.databinding.FragmentSearchBinding
import com.apis.recipefood.util.Constants.Companion.MEAL_ID
import com.apis.recipefood.util.Constants.Companion.MEAL_NAME
import com.apis.recipefood.util.Constants.Companion.MEAL_THUMB
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel:MealViewModel by viewModels()
    private lateinit var adapter: SearchAdapter
    private var mealId=""
    private var mealStr=""
    private var mealThumb=""

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
        //searchRecyclerView()

       //observeSearchLiveData()
        onSearchClickDetail()
        onIconSearchClick()










    }

    private fun onIconSearchClick() {
        binding.ivSearch.setOnClickListener {
            viewModel.searchMeal(binding.etSearch.text.toString())
        }
    }


    private fun onSearchClickDetail() {
            binding.cardSearch.setOnClickListener {
                val intent = Intent(context, DetailMealActivity::class.java)
                intent.putExtra(MEAL_ID,mealId)
                intent.putExtra(MEAL_NAME,mealStr)
                intent.putExtra(MEAL_THUMB,mealThumb)
                startActivity(intent)
            }


    }

    fun search(){
        binding.cardSearch.setOn
    }

    /*private fun observeSearchLiveData() {
        viewModel.observerSearchMealLiveData().observe(viewLifecycleOwner
        ) { t ->
            if (t == null) {
                binding.tvNoMealsResult.visibility = View.VISIBLE
            } else {
                binding.apply {
                    mealId = t. idMeal
                    mealStr = t.strMeal.toString()
                    mealThumb = t.strMealThumb.toString()

                    Glide.with(requireContext().applicationContext)
                        .load(t.strMealThumb)
                        .into(ivSearchMeal)

                    tvSearchName.text = t.strMeal
                    cardSearch.visibility = View.VISIBLE
                }
            }
        }
    }*/
   /* //RecyclerView
    private fun searchRecyclerView() {
        adapter= SearchAdapter()
        binding.rvSearch.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=adapter
        }
    }*/




}