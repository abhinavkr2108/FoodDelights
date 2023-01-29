package com.example.fooddelights.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fooddelights.R
import com.example.fooddelights.database.MealDatabase
import com.example.fooddelights.databinding.ActivityMainBinding
import com.example.fooddelights.viewmodels.HomeViewModel
import com.example.fooddelights.viewmodels.HomeViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var mealDatabase: MealDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        mealDatabase = MealDatabase.getInstance(this)
        homeViewModelFactory = HomeViewModelFactory(application,mealDatabase)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }
}