package com.example.fooddelights.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelights.R
import com.example.fooddelights.adapters.CategoriesAdapter
import com.example.fooddelights.adapters.FragmentCategoryAdapter
import com.example.fooddelights.databinding.FragmentCategoriesBinding
import com.example.fooddelights.databinding.FragmentCategoryFoodItemsBinding
import com.example.fooddelights.ui.activities.InfoActivity
import com.example.fooddelights.ui.activities.MainActivity
import com.example.fooddelights.viewmodels.HomeViewModel


class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    lateinit var catBinding: FragmentCategoriesBinding
    lateinit var homeViewModel: HomeViewModel
    private val categoriesAdapter = FragmentCategoryAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catBinding = FragmentCategoriesBinding.bind(view)
        homeViewModel = (activity as MainActivity).homeViewModel

        setUpRecyclerView()
        observeCategories()

    }

    private fun observeCategories() {
        homeViewModel.observeCategoriesLivedata().observe(viewLifecycleOwner, Observer {
            categoriesAdapter.categoryDiffer.submitList(it)
        })
    }




    private fun setUpRecyclerView() {
        catBinding.rvFav.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }
}