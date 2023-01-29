package com.example.fooddelights.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.fooddelights.R
import com.example.fooddelights.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment(R.layout.fragment_details) {
    lateinit var detailsBinding: FragmentDetailsBinding
    lateinit var mealId: String
    lateinit var mealName: String
    lateinit var mealThumb: String
    private val args = arguments
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsBinding = FragmentDetailsBinding.bind(view)

        getMealInformation()
        setMealInformation()
    }

    private fun getMealInformation() {
        mealId = requireArguments().getString("MealID").toString()
        mealName = requireArguments().getString("MealName").toString()
        mealThumb = requireArguments().getString("MealThumb").toString()
    }

    private fun setMealInformation(){
        Glide.with(this@DetailsFragment).load(mealThumb).into(detailsBinding.imgMealDetails)
        detailsBinding.apply {
            collapseToolBar.title = mealName
            collapseToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
            collapseToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
        }
    }
}