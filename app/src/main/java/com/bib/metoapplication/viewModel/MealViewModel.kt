package com.bib.metoapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bib.metoapplication.db.MealDataBase
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.pojo.MealList
import com.bib.metoapplication.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDataBase: MealDataBase
) : ViewModel() {
    private var mealDetailsLiveData =  MutableLiveData<Meal>()
     fun getMealDetails(id : String){
         RetrofitInstance.api.getMealById(id).enqueue(object : Callback<MealList> {
             override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                 if (response.body() != null){
                     mealDetailsLiveData.value = response.body()!!.meals[0]
                 }else{
                     return
                 }
             }

             override fun onFailure(call: Call<MealList>, t: Throwable) {
                 TODO("Not yet implemented")
                 Log.i("error",""+t.toString())
             }
         })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().insertMeal(meal)
        }
    }




}