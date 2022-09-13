package com.apis.recipefood.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.FavCardBinding
import com.apis.recipefood.pojo.Meal
import com.bumptech.glide.Glide


class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var mealList:List<Meal> = ArrayList()
    private lateinit var setOnMealClickListener:SetOnMealClickListener

    fun setMealList(mealList: List<Meal>){
        this.mealList=mealList
        notifyDataSetChanged()
    }

    fun setOnClickListener(setOnMealClickListener: SetOnMealClickListener){
        this.setOnMealClickListener= setOnMealClickListener
    }




    class SearchViewHolder(val binding: FavCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(FavCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
       holder.binding.apply {
           tvFavName.text=mealList[position].strMeal
           Glide.with(holder.itemView)
               .load(mealList[position].strMealThumb)
               .into(ivFavMeal)
       }

        holder.itemView.setOnClickListener {
            setOnMealClickListener.setOnClickListener(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }


}

interface SetOnMealClickListener {
    fun setOnClickListener(meal: Meal)

}




