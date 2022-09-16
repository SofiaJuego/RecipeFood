package com.apis.recipefood.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.FavCardBinding
import com.apis.recipefood.pojo.Meal
import com.bumptech.glide.Glide


class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var mealList = ArrayList<Meal>()

    var onItemClick : ((Meal) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setMealList(mealList: List<Meal>){
        this.mealList= mealList as ArrayList<Meal>
        mealList.clear()
        mealList.addAll(mealList)
        notifyDataSetChanged()
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
            onItemClick!!.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }


}

interface SetOnMealClickListener {
    fun setOnClickListener(meal: Meal)

}




