package com.example.fooddelights.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fooddelights.ui.activities.InfoActivity
import com.example.fooddelights.R
import com.example.fooddelights.adapters.CategoriesAdapter
import com.example.fooddelights.adapters.PopularItemsAdapter
import com.example.fooddelights.apiclasses.Meal
import com.example.fooddelights.databinding.FragmentHomeBinding
import com.example.fooddelights.ui.activities.MainActivity
import com.example.fooddelights.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class HomeFragment : Fragment(R.layout.fragment_home,) {


    lateinit var homeBinding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private val popularAdapter = PopularItemsAdapter()
    private val categoryAdapter = CategoriesAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel

        getRandomMeal()
        observeRandomMeal()

        homeBinding.imgRandom.setOnClickListener {
           // navigateToDetailsFragment()
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("MealID", randomMeal.idMeal)
            intent.putExtra("MealName", randomMeal.strMeal)
            intent.putExtra("MealThumb", randomMeal.strMealThumb)
            startActivity(intent)
        }

        homeBinding.imgBtnSearch.setOnClickListener {
           val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            it.findNavController().navigate(direction)
        }


        popularItemsRecyclerView()
        getPopularMeals()
        observePopularMeals()
        onPopularMealClicked()
        getCategories()
        setCategoriesRecyclerView()
        observeCategories()


    }

    private fun getRandomMeal() {
        homeViewModel.getRandomMeal()
    }

    private fun observeRandomMeal(){
        homeViewModel.observeRandomMealLivedata().observe(viewLifecycleOwner, Observer { meal->
            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(homeBinding.imgRandom)
            this.randomMeal = meal
        })
    }

    private fun getPopularMeals(){
        homeViewModel.getPopularItems()
    }

    private fun popularItemsRecyclerView(){
        homeBinding.rvPopularFoods.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    private fun observePopularMeals(){
        homeViewModel.observePopularItemsLivedata().observe(viewLifecycleOwner, Observer {
            popularAdapter.mealDiffer.submitList(it)
        })
    }

    private fun onPopularMealClicked(){
        popularAdapter.onItemClick = {
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("MealID", it.idMeal)
            intent.putExtra("MealName", it.strMeal)
            intent.putExtra("MealThumb", it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun getCategories(){
        homeViewModel.getCategories()
    }

    private fun setCategoriesRecyclerView(){
        homeBinding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observeCategories(){
        homeViewModel.observeCategoriesLivedata().observe(viewLifecycleOwner, Observer{
            categoryAdapter.categoryDiffer.submitList(it)
        })
    }
}