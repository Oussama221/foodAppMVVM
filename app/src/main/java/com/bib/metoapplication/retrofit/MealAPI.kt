package com.bib.metoapplication.retrofit

import com.bib.metoapplication.pojo.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName: String) : Call<CategoryList>

    @GET("categories.php")
    fun getAllCategories() : Call<AllCategoriesList>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<FilterMealsByCategoryList>

    @GET("search.php?")
    fun  getMealByName(@Query("s") categoryName: String) : Call<MealList>
}