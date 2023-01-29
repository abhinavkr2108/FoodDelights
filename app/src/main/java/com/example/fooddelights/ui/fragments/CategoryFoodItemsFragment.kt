package com.example.fooddelights.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooddelights.R
import com.example.fooddelights.adapters.CategoriesAdapter
import com.example.fooddelights.adapters.CategoriesMealsAdapter
import com.example.fooddelights.apiclasses.Category
import com.example.fooddelights.apiclasses.CategoryMeals
import com.example.fooddelights.databinding.FragmentCategoryFoodItemsBinding
import com.example.fooddelights.databinding.FragmentHomeBinding
import com.example.fooddelights.ui.activities.InfoActivity
import com.example.fooddelights.viewmodels.CategoryViewModel


class CategoryFoodItemsFragment : Fragment(R.layout.fragment_category_food_items,) {

    lateinit var categoryBinding: FragmentCategoryFoodItemsBinding
    lateinit var categoryViewModel: CategoryViewModel
    lateinit var categoryAdapter: CategoriesAdapter
    private val mealItemsAdapter = CategoriesMealsAdapter()
    private val args: CategoryFoodItemsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryBinding = FragmentCategoryFoodItemsBinding.bind(view)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryAdapter = CategoriesAdapter()

        val categoryMeals = args.category?.strCategory

        if (categoryMeals != null) {
            getMealsByCategory(categoryMeals)
        }

        setUpRecyclerView()
        observeMealsByCategory()
        onItemClicked()

    }

    private fun setUpRecyclerView() {
        categoryBinding.rvMeals.apply {
            adapter = mealItemsAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun getMealsByCategory(categoryName: String) {
        categoryViewModel.getMealsByCategories(categoryName)
    }

    private fun observeMealsByCategory() {
        categoryViewModel.observeCategoryMeals().observe(viewLifecycleOwner, Observer {
            categoryBinding.tvCount.text = "Total Items: ${it.size.toString()}"
            mealItemsAdapter.categoryMealDiffer.submitList(it)
        })
    }

    private fun onItemClicked() {
        mealItemsAdapter.onItemClick = {
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("MealID", it.idMeal)
            intent.putExtra("MealName", it.strMeal)
            intent.putExtra("MealThumb", it.strMealThumb)
            startActivity(intent)
        }
    }


}

