package com.example.fooddelights.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fooddelights.apiclasses.*
import com.example.fooddelights.database.MealDatabase
import com.example.fooddelights.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(app: Application, val mealDatabase: MealDatabase): AndroidViewModel(app) {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()
    private var searchMealLiveData = MutableLiveData<List<CategoryMeals>>()


    /**
     * Function to get a random meal: Calls Function in the interface
     */
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object: Callback<RandomMeal>{
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                if (response.body()!= null){
                    val randomMeal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                Log.d("ERROR", "An error occurred:  ${t.message.toString()}")
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularCategories("Seafood").enqueue(object : Callback<MealCategory>{
            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {
                if (response.body()!= null){
                    val categorylist = response.body()!!.meals
                    popularItemsLiveData.value = categorylist
                }
            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
                Log.d("ERROR", "An error occurred:  ${t.message.toString()}")
            }
        })
    }
    
    fun getCategories(){
        RetrofitInstance.api.getAllCategories().enqueue(object : Callback<AllCategories> {
            override fun onResponse(call: Call<AllCategories>, response: Response<AllCategories>) {
                response.body()?.let {
                    categoriesLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<AllCategories>, t: Throwable) {
                Log.d("ERROR", "An error occurred:  ${t.message.toString()}")
            }
        })
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }

    fun searchMeals(searchQuery: String) {
        RetrofitInstance.api.searchMeals(searchQuery).enqueue( object : Callback<MealCategory>{
            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {
                if (response.body()!= null){
                    var mealList = response.body()!!.meals
                    mealList?.let {
                        searchMealLiveData.postValue(mealList)
                    }
                }
            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
                Log.d("SEARCH_ERROR", "An error occurred:  ${t.message.toString()}")
            }
        })
    }

    fun observeRandomMealLivedata() = randomMealLiveData
    fun observePopularItemsLivedata() = popularItemsLiveData
    fun observeCategoriesLivedata() = categoriesLiveData
    fun observeFavoriteMealsLiveData() = favoriteMealLiveData
    fun observeSearchMealLiveData()= searchMealLiveData

}