package com.apis.recipefood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.MealsItemBinding
import com.apis.recipefood.pojo.CategoryMeals
import com.bumptech.glide.Glide

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.MealsViewHolder>() {

    private var mealsList = ArrayList<CategoryMeals>()

    fun setMealsList(mealsList: List<CategoryMeals>){
        this.mealsList= mealsList as ArrayList<CategoryMeals>
        notifyDataSetChanged()
    }

    inner class MealsViewHolder(var binding: MealsItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(MealsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.ivMeals)
        holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }


}