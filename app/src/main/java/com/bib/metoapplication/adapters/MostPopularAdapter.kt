package com.bib.metoapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bib.metoapplication.databinding.PopularItemViewBinding
import com.bib.metoapplication.pojo.CategoryMeals
import com.bumptech.glide.Glide

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.popularMealViewHolder>() {
    private var meallist = ArrayList<CategoryMeals>()
    lateinit var onItemClick:((CategoryMeals) -> Unit)
    fun setMeals(mealList: ArrayList<CategoryMeals>){
        this.meallist = mealList
        notifyDataSetChanged()
    }
    class popularMealViewHolder(var binding: PopularItemViewBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): popularMealViewHolder {
        return popularMealViewHolder(PopularItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: popularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(meallist[position].strMealThumb).into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meallist[position])
        }
    }

    override fun getItemCount(): Int {
       return meallist.size
    }
}