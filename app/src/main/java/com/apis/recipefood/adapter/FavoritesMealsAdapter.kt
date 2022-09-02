package com.apis.recipefood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.FavCardBinding
import com.apis.recipefood.databinding.MealsItemBinding
import com.apis.recipefood.pojo.CategoryMeals
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.pojo.MealList
import com.bumptech.glide.Glide

class FavoritesMealsAdapter:RecyclerView.Adapter<FavoritesMealsAdapter.FavoriteViewHolder>(){

    private var favoriteMeal=ArrayList<Meal>()
    private lateinit var onFavoriteClickListener:OnFavoriteClickListener
    private lateinit var onFavoriteLongClickListener: OnFavoriteLongClickListener

    inner class FavoriteViewHolder (val binding: FavCardBinding):RecyclerView.ViewHolder(binding.root)

    fun setFavoriteMealsList(favoriteMeal: ArrayList<Meal>){
        this.favoriteMeal=favoriteMeal
        notifyDataSetChanged()
    }

    fun getMealByPosition(position: Int):Meal{
        return favoriteMeal[position]
    }

    fun setOnFavoriteMealClickListener(onFavoriteClickListener: OnFavoriteClickListener){
        this.onFavoriteClickListener=onFavoriteClickListener
    }

    fun setOnFavoriteLongClickListe(onFavoriteLongClickListener: OnFavoriteLongClickListener){
        this.onFavoriteLongClickListener=onFavoriteLongClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val i = position
        holder.binding.apply {
            tvFavName.text=favoriteMeal[position].strMealThumb
            Glide.with(holder.itemView)
                .load(favoriteMeal[position].strMealThumb)
                .into(ivFavMeal)

        }


        holder.itemView.setOnClickListener {
            onFavoriteClickListener.onFavoriteClick(favoriteMeal[position])
        }

        holder.itemView.setOnLongClickListener {
            onFavoriteLongClickListener.onFavoriteLongClick(favoriteMeal[i])
            true
        }

    }



    override fun getItemCount(): Int {
       return favoriteMeal.size
    }

}

interface OnFavoriteLongClickListener {
    fun onFavoriteLongClick(meal: Meal)

}

interface OnFavoriteClickListener {
    fun onFavoriteClick(meal: Meal)

}
