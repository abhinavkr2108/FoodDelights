package com.example.fooddelights.retrofit

import com.example.fooddelights.apiclasses.AllCategories
import com.example.fooddelights.apiclasses.Meal
import com.example.fooddelights.apiclasses.MealCategory
import com.example.fooddelights.apiclasses.RandomMeal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<RandomMeal>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<RandomMeal>

    @GET("filter.php?")
    fun getPopularCategories(@Query("c")category: String): Call<MealCategory>

    @GET("categories.php")
    fun getAllCategories():Call<AllCategories>

    @GET("filter.php")
    fun getMealsByCategories(@Query("c")category: String): Call<MealCategory>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String): Call<MealCategory>
}