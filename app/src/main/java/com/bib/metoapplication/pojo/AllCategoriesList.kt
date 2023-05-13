package com.bib.metoapplication.pojo


import com.google.gson.annotations.SerializedName

data class AllCategoriesList(
    @SerializedName("categories")
    val categories: List<Category>
)