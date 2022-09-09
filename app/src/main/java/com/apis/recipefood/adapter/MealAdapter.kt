package com.apis.recipefood.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.PopularItemBinding
import com.apis.recipefood.pojo.Meal
import com.bumptech.glide.Glide

class MealAdapter:RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    lateinit var onItemClick: ((Meal) -> Unit)
    var onItemLongClick : ((Meal) -> Unit)? = null
    private var mealsList = ArrayList<Meal>()


    fun setMeals(categoryMeals: ArrayList<Meal>){
        this.mealsList=categoryMeals
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
        holder.itemView.setOnLongClickListener{
            onItemLongClick?.invoke(mealsList[position])
            true
        }
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }
}