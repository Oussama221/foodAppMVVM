package com.bib.metoapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bib.metoapplication.pojo.FilterMealsByCategoryList
import com.bib.metoapplication.pojo.MealByCateory
import com.bib.metoapplication.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel() : ViewModel() {
    val mealListLiveData = MutableLiveData<List<MealByCateory>>()
    fun getMealByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<FilterMealsByCategoryList>{
            override fun onResponse(call: Call<FilterMealsByCategoryList>, response: Response<FilterMealsByCategoryList>) {
               // TODO("Not yet implemented")
                response.body()?.let {
                    filterMealsByCategoryList ->
                    mealListLiveData.postValue(filterMealsByCategoryList.meals)
                }
            }

            override fun onFailure(call: Call<FilterMealsByCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    public fun observerMealsByCategoory() : LiveData<List<MealByCateory>>{
        return mealListLiveData
    }
}