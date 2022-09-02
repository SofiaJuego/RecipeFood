package com.apis.recipefood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.apis.recipefood.adapter.FavoritesMealsAdapter
import com.apis.recipefood.databinding.FragmentFavoritesBinding
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.viewmodel.MealViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    private val mealMvvm : MealViewModel by viewModels()
    private lateinit var favoritesMealsAdapter: FavoritesMealsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       //RV
       favoritesRecyclerView()


       //observerFavorites()




    }

    //Recycler View
    private fun favoritesRecyclerView() {
        favoritesMealsAdapter= FavoritesMealsAdapter()
        binding.rvFavorites.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        }
    }

    //private fun observerFavorites(){
      //  mealMvvm.observerFavoritesLiveData().obse}


}