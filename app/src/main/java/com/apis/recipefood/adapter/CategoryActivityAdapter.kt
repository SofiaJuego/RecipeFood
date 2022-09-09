package com.apis.recipefood.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.MealsItemBinding
import com.apis.recipefood.pojo.Meal
import com.bumptech.glide.Glide

//ADAPTER DE RECETAS DE LAS CATEGORIAS (ACTIVITY)
class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.MealsViewHolder>() {

    private var mealsList  = ArrayList<Meal>()

    var onItemClick : ((Meal) -> Unit)? = null




    @SuppressLint("NotifyDataSetChanged")
    fun setCategoriesList(mealsList: List<Meal>){
        this.mealsList=mealsList as ArrayList<Meal>
        notifyDataSetChanged()
    }


    class MealsViewHolder(var binding: MealsItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        return MealsViewHolder(MealsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.ivMeals)
        holder.binding.tvMealName.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }


}

