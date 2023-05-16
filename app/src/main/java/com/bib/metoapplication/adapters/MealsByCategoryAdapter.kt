package com.bib.metoapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bib.metoapplication.databinding.MealsByCategoryBinding
import com.bib.metoapplication.pojo.Category
import com.bib.metoapplication.pojo.MealByCateory
import com.bumptech.glide.Glide


class MealsByCategoryAdapter() : RecyclerView.Adapter<MealsByCategoryAdapter.MealsByCategoryViewHolder>() {
    private var mealsByCategory = ArrayList<MealByCateory>()
    var onItemClick:((MealByCateory) -> Unit)? = null
    class MealsByCategoryViewHolder(var binding: MealsByCategoryBinding ) : RecyclerView.ViewHolder(binding.root) {}

    fun setMealsByCategory(mealByCateory: ArrayList<MealByCateory>){
        this.mealsByCategory = mealByCateory
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsByCategoryViewHolder {
        return MealsByCategoryAdapter.MealsByCategoryViewHolder(MealsByCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MealsByCategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsByCategory[position].strMealThumb).into(holder.binding.imgMealsByCategory)
        holder.binding.textMeal.text = mealsByCategory[position].strMeal
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(mealsByCategory[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsByCategory.size
    }
}