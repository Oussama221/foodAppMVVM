package com.bib.metoapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bib.metoapplication.pojo.Meal
import com.bib.metoapplication.pojo.MealList
import com.bib.metoapplication.retrofit.RetrofitInstance
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private var randomMealLiveData =  MutableLiveData<Meal>()
    fun getRundomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal = response.body()!!.meals[0]
                    Log.d("random",""+randomMeal.strMealThumb)
                    randomMealLiveData.value = randomMeal

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

    public fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}