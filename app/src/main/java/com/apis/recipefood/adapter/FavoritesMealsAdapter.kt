package com.apis.recipefood.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.FavCardBinding
import com.apis.recipefood.pojo.Meal
import com.bumptech.glide.Glide

class FavoritesMealsAdapter:RecyclerView.Adapter<FavoritesMealsAdapter.FavoriteViewHolder>() {

    private var favMeal  = ArrayList<Meal>()
    private var onItemClick : ((Meal) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteList(mealsList: List<Meal>){
        this.favMeal=mealsList as ArrayList<Meal>
        notifyDataSetChanged()
    }

    fun getMealPosition(position: Int):Meal{
        return favMeal[position]
    }

    class FavoriteViewHolder(val binding: FavCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Glide.with(holder.itemView).load(favMeal[position].strMealThumb)
            .into(holder.binding.ivFavMeal)
        holder.binding.tvFavName.text = favMeal[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(favMeal[position])
        }
    }

    override fun getItemCount(): Int {
        return favMeal.size
    }
}




