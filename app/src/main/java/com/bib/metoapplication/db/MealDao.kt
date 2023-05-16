package com.bib.metoapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bib.metoapplication.pojo.Meal


@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>
}