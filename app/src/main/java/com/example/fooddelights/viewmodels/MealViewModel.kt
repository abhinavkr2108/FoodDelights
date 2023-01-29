package com.example.fooddelights.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fooddelights.apiclasses.Meal
import com.example.fooddelights.apiclasses.RandomMeal
import com.example.fooddelights.database.MealDatabase
import com.example.fooddelights.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(application: Application, private val mealDatabase: MealDatabase) : AndroidViewModel(application){
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<RandomMeal>{
            override fun onResponse(call: Call<RandomMeal>, response: Response<RandomMeal>) {
                if (response.body()!=null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<RandomMeal>, t: Throwable) {
                Log.d("MealViewModel", "Error: ${t.message.toString()}")
            }
        })
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }





    fun observeMealDetailsLiveData(): LiveData<Meal> = mealDetailsLiveData
}