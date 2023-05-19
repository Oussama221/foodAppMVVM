package com.bib.metoapplication.viewModel

import android.icu.text.StringSearch
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bib.metoapplication.db.MealDataBase
import com.bib.metoapplication.pojo.*
import com.bib.metoapplication.retrofit.RetrofitInstance
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel( private val mealDataBase: MealDataBase):ViewModel() {

    private var randomMealLiveData =  MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    private var AllCategoryLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealLiveData = mealDataBase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private val searchMealLiveData = MutableLiveData<List<Meal>>()

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
               // TODO("Not yet implemented")
                Log.i("error",""+t.toString())
            }
        })
    }

    fun getAllCategories(){
        RetrofitInstance.api.getAllCategories().enqueue(object :Callback<AllCategoriesList>{
            override fun onResponse(call: Call<AllCategoriesList>, response: Response<AllCategoriesList>) {
               // TODO("Not yet implemented")
                if (response != null){
                    AllCategoryLiveData.value = response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<AllCategoriesList>, t: Throwable) {
               // TODO("Not yet implemented")
            }

        })
    }

   fun getPopularMeals(){
       RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<CategoryList>{
           override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               if (response.body() != null){
                   popularItemsLiveData.value = response.body()!!.meals
               }
           }

           override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               TODO("Not yet implemented")
           }

       })
   }

    public fun getMealById(id:String){
        RetrofitInstance.api.getMealById(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun searchMeal(search: String) = RetrofitInstance.api.getMealByName(search).enqueue(object : Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealList = response.body()?.meals
            mealList.let {
                searchMealLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })
    public fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
    public fun observePopularMealLiveData():LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }

    public  fun observeAllCategories(): LiveData<List<Category>>{
        return AllCategoryLiveData
    }

    public fun observeFavoriteMeal() : LiveData<List<Meal>>{
        return favoriteMealLiveData
    }

    public fun observeBottomSheetMealById() : LiveData<Meal>{
        return bottomSheetMealLiveData
    }

    public fun observeSearchMealLiveData() : LiveData<List<Meal>>{
        return searchMealLiveData
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().deleteMeal(meal)
        }
    }
}




