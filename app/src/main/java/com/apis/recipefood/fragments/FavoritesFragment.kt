package com.apis.recipefood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.adapter.FavoritesMealsAdapter
import com.apis.recipefood.databinding.FragmentFavoritesBinding
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.viewmodel.MealViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding

    private val mealMvvm : MealViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoritesMealsAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter=FavoritesMealsAdapter()

       //RV
        favoritesRecyclerView()
        observerFavorites()
        swipeToDelete()



    }

    private fun observerFavorites() {
        mealMvvm.observerFavoritesMealsLiveData().observe(requireActivity()) { mealsList ->
            (mealsList as? ArrayList<Meal>)?.let { favoriteAdapter.setFavoriteList(it) }

        }
    }



    //Recycler View
    private fun favoritesRecyclerView() {
        favoriteAdapter= FavoritesMealsAdapter()
        binding.rvFavorites.apply {
            layoutManager=GridLayoutManager(context,1,GridLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

    //Eliminar receta de favoritos deslizando a la derecha
    private fun swipeToDelete(){

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.bindingAdapterPosition
                mealMvvm.deleteMeal(favoriteAdapter.getMealPosition(position))
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo",View.OnClickListener {
                        mealMvvm.insertMeal(favoriteAdapter.getMealPosition(position))
                    }).show()
                }


            }

        }).attachToRecyclerView(binding.rvFavorites)
    }




}


