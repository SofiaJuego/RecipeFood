package com.apis.recipefood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apis.recipefood.databinding.CategoryItemBinding
import com.apis.recipefood.pojo.Category
import com.bumptech.glide.Glide


class CategoriesAdapter :RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var onItemClick : ((Category) -> Unit)? = null

    private var categoriesList = ArrayList<Category>()

    fun setCategoryList(categoriesList: List<Category>){
        this.categoriesList=categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate
                (LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.ivCategory)
        holder.binding.tvCategoryName.text=categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}