package com.apis.recipefood.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.FavCardBinding
import com.apis.recipefood.pojo.Meal
import com.bumptech.glide.Glide

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    //private var mealsList:List<Meal>  = ArrayList()
    private var mealsList  = ArrayList<Meal>()
    lateinit var onItemClick : ((Meal) -> Unit)


    @SuppressLint("NotifyDataSetChanged")
    fun setMealsearch(mealsList: List<Meal>){
        this.mealsList= mealsList as ArrayList<Meal>
        notifyDataSetChanged()
    }

    class SearchViewHolder(var binding:FavCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(FavCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.ivFavMeal)
        holder.binding.tvFavName.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }

    }

    override fun getItemCount(): Int {
       return mealsList.size
    }


}