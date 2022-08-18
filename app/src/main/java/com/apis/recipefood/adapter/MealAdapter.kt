package com.apis.recipefood.adapter

import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.PopularItemBinding
import com.apis.recipefood.pojo.CategoryMeals
import com.bumptech.glide.Glide

class MealAdapter:RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    lateinit var onItemClick: ((CategoryMeals) -> Unit)
    private var mealsList = ArrayList<CategoryMeals>()


    fun setMeals(mealList: ArrayList<CategoryMeals>){
        this.mealsList=mealList
        notifyDataSetChanged()
    }

    class MealViewHolder(var binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.ivPopularItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }
}