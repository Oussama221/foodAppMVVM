package com.bib.metoapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bib.metoapplication.databinding.CategoryItemBinding
import com.bib.metoapplication.pojo.Category
import com.bib.metoapplication.pojo.CategoryMeals
import com.bib.metoapplication.pojo.MealByCateory
import com.bumptech.glide.Glide

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    private var categoryList = ArrayList<Category>()
      var onItemClick:((Category) -> Unit)? = null
    class CategoryHolder(var binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) { }
    fun setCategories(categoryList : ArrayList<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.categoryItem)
        holder.binding.categoryItemName.text = categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}