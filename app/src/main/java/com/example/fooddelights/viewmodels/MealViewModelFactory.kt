package com.example.fooddelights.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fooddelights.database.MealDatabase

class MealViewModelFactory(private val app: Application, private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(app,mealDatabase) as T
    }
}