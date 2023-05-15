package com.bib.metoapplication.pojo


import com.google.gson.annotations.SerializedName

data class MealByCateory(
    @SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)