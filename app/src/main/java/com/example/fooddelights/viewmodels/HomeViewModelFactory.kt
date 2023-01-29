package com.example.fooddelights.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fooddelights.database.MealDatabase

class HomeViewModelFactory(private val app: Application, private val mealDatabase: MealDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(app,mealDatabase) as T
    }
}