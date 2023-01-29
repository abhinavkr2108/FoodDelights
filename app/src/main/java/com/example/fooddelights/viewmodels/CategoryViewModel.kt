package com.example.fooddelights.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fooddelights.apiclasses.CategoryMeals
import com.example.fooddelights.apiclasses.MealCategory
import com.example.fooddelights.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel(app: Application): AndroidViewModel(app) {

    private var categoriesByMeals = MutableLiveData<List<CategoryMeals>>()

    fun getMealsByCategories(categoryName: String){
        RetrofitInstance.api.getMealsByCategories(categoryName).enqueue(object : Callback<MealCategory>{
            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {
                response.body()?.let {
                    categoriesByMeals.postValue(it.meals)
                }
            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
                Log.d("CATEGORY_VIEW_MODEL", "${t.message}")
            }
        })
    }

    fun observeCategoryMeals() = categoriesByMeals
}