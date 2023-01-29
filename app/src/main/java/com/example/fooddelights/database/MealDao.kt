package com.example.fooddelights.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fooddelights.apiclasses.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal:Meal)

    @Query("SELECT * FROM MealInformation")
    fun getAllMeals(): LiveData<List<Meal>>


}