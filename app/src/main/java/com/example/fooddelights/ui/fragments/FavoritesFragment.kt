package com.example.fooddelights.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelights.R
import com.example.fooddelights.adapters.FavoritesAdapter
import com.example.fooddelights.databinding.FragmentFavoritesBinding
import com.example.fooddelights.ui.activities.InfoActivity
import com.example.fooddelights.ui.activities.MainActivity
import com.example.fooddelights.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment(R.layout.fragment_favorites,) {

    lateinit var homeViewModel: HomeViewModel
    lateinit var favoriteBinding: FragmentFavoritesBinding
    private val favoritesAdapter = FavoritesAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = (activity as MainActivity).homeViewModel
        favoriteBinding = FragmentFavoritesBinding.bind(view)

        setUpRecyclerView()
        observeLiveData()
        onItemClicked()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val mealItem = favoritesAdapter.favDiffer.currentList[position]
                homeViewModel.deleteMeal(mealItem)
                Snackbar.make(view, "Meal Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                       homeViewModel.insertMeal(mealItem)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(favoriteBinding.rvFav)
        }


    }

    private fun setUpRecyclerView(){
        favoriteBinding.rvFav.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun onItemClicked() {
        favoritesAdapter.onItemClick = {
            val intent = Intent(activity, InfoActivity::class.java)
            intent.putExtra("MealID", it.idMeal)
            intent.putExtra("MealName", it.strMeal)
            intent.putExtra("MealThumb", it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeLiveData() {
        homeViewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner, Observer {
            favoritesAdapter.favDiffer.submitList(it)
        })
    }
}