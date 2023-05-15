package com.bib.metoapplication.pojo


import com.google.gson.annotations.SerializedName

data class FilterMealsByCategoryList(
    @SerializedName("meals")
    val meals: List<MealByCateory>
)