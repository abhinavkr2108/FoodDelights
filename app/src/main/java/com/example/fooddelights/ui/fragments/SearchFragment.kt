package com.example.fooddelights.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooddelights.R
import com.example.fooddelights.adapters.CategoriesMealsAdapter
import com.example.fooddelights.adapters.FavoritesAdapter
import com.example.fooddelights.adapters.PopularItemsAdapter
import com.example.fooddelights.databinding.FragmentFavoritesBinding
import com.example.fooddelights.databinding.FragmentSearchBinding
import com.example.fooddelights.ui.activities.InfoActivity
import com.example.fooddelights.ui.activities.MainActivity
import com.example.fooddelights.viewmodels.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var serachBinding: FragmentSearchBinding
    private lateinit var homeViewModel: HomeViewModel
    private val searchAdapter = CategoriesMealsAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serachBinding = FragmentSearchBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel

        setUPRecyclerView()

        var searchMeal: Job? = null
        serachBinding.searchNotes.addTextChangedListener {
            searchMeal?.cancel()
            searchMeal = lifecycleScope.launch {
                delay(1000)
                homeViewModel.searchMeals(it.toString())
            }
        }

        observeSearchMeals()
        onItemClicked()



    }

    private fun observeSearchMeals() {
        homeViewModel.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer{
            searchAdapter.categoryMealDiffer.submitList(it)
        })
    }

    private fun onItemClicked() {
        searchAdapter.onItemClick = {
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("MealID", it.idMeal)
            intent.putExtra("MealName", it.strMeal)
            intent.putExtra("MealThumb", it.strMealThumb)
            startActivity(intent)
        }
    }


    private fun setUPRecyclerView() {
        serachBinding.rvSearch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        }
    }
}