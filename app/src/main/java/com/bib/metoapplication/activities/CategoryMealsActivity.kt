package com.bib.metoapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bib.metoapplication.R
import com.bib.metoapplication.adapters.MealsByCategoryAdapter
import com.bib.metoapplication.databinding.ActivityFilterCategoryBinding
import com.bib.metoapplication.fragments.HomeFragment
import com.bib.metoapplication.pojo.MealByCateory
import com.bib.metoapplication.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterCategoryBinding
    private lateinit var categoryMealMVVM : CategoryMealsViewModel
    private lateinit var categoryName:String
    private lateinit var mealsByCategoryAdapter: MealsByCategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryMealMVVM =ViewModelProvider(this).get(CategoryMealsViewModel::class.java)
        mealsByCategoryAdapter = MealsByCategoryAdapter()
        getMealByCategoryFromIntent()
        prepareRvMealsByCategory()

        getMealsByCategory()
        observeMealsByCategory()


    }

    private fun prepareRvMealsByCategory() {
        binding.rvMealsByCategory.apply {
            layoutManager = GridLayoutManager(applicationContext,2,GridLayoutManager.VERTICAL,false)
            adapter = mealsByCategoryAdapter
        }

    }


    private fun observeMealsByCategory(){
        categoryMealMVVM.observerMealsByCategoory().observe(this,{
                mealByCategoryList ->
            mealsByCategoryAdapter.setMealsByCategory(mealByCategoryList as ArrayList<MealByCateory> /* = java.util.ArrayList<com.bib.metoapplication.pojo.MealByCateory> */)
            Log.i("size Of Meel",""+mealByCategoryList.size)
            binding.categoryNameAndAccount.text = "${categoryName} : ${mealByCategoryList.size}"
            mealByCategoryList.forEach {
                Log.d("Meals By Category ",""+it.strMeal)
            }
        })
    }
    private fun getMealsByCategory() {
        categoryMealMVVM.getMealByCategory(categoryName)
    }

    private fun getMealByCategoryFromIntent() {
        val i = intent
        categoryName =  i.getStringExtra(HomeFragment.CATEGORY_NAME)!!
    }
}