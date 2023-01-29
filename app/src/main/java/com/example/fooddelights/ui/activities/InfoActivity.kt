package com.example.fooddelights.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.fooddelights.R
import com.example.fooddelights.apiclasses.Meal
import com.example.fooddelights.database.MealDatabase
import com.example.fooddelights.databinding.ActivityInfoBinding
import com.example.fooddelights.viewmodels.MealViewModel
import com.example.fooddelights.viewmodels.MealViewModelFactory

class InfoActivity : AppCompatActivity() {
    lateinit var mealId: String
    lateinit var mealName: String
    lateinit var mealThumb: String
    lateinit var youtubeLink: String
    lateinit var infoBinding: ActivityInfoBinding
    lateinit var mealViewModel: MealViewModel
    lateinit var mealDatabase: MealDatabase
    lateinit var mealViewModelFactory: MealViewModelFactory
    private var mealToSave: Meal? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        infoBinding = DataBindingUtil.setContentView<ActivityInfoBinding>(this,
            R.layout.activity_info
        )

        mealDatabase = MealDatabase.getInstance(this)
        mealViewModelFactory = MealViewModelFactory(application, mealDatabase)
        mealViewModel = ViewModelProvider(this, mealViewModelFactory).get(MealViewModel::class.java)

        getMealInformation()
        setMealInformation()
        isLoading()
        getMealDetails(mealId)
        observeMealDetails()

        infoBinding.btnVideo.setOnClickListener {
            openYoutubeVideo()
        }

        infoBinding.fabFavorite.setOnClickListener(View.OnClickListener {
            saveToFavorite()
        })

    }

    private fun saveToFavorite() {
        mealToSave?.let {
            mealViewModel.insertMeal(it!!)
            Toast.makeText(this, "Meal Saved Successfully", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getMealInformation() {
        val intent = intent
        mealId = intent.getStringExtra("MealID").toString()
        mealName = intent.getStringExtra("MealName").toString()
        mealThumb = intent.getStringExtra("MealThumb").toString()
        youtubeLink = intent.getStringExtra("MealThumb").toString()
    }

    private fun setMealInformation() {
        Glide.with(applicationContext).load(mealThumb).into(infoBinding.imgMealDetails)
        infoBinding.apply {
            collapseToolBar.title = mealName
            collapseToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
            collapseToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
        }
    }

    private fun getMealDetails(id: String){
        mealViewModel.getMealDetails(id)
    }

    private fun isLoading(){
        infoBinding.apply {
            imgProgressBar.visibility = View.VISIBLE
            fabFavorite.visibility = View.INVISIBLE
            tvInstructionsSteps.visibility = View.INVISIBLE
            tvInstructions.visibility = View.INVISIBLE
            btnVideo.visibility = View.INVISIBLE
            tvCategory.visibility = View.INVISIBLE
            tvLocation.visibility = View.INVISIBLE
        }
    }

    private fun hasLoaded(){
        infoBinding.apply {
            imgProgressBar.visibility = View.GONE
            fabFavorite.visibility = View.VISIBLE
            tvInstructionsSteps.visibility = View.VISIBLE
            tvInstructions.visibility = View.VISIBLE
            btnVideo.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvLocation.visibility = View.VISIBLE
        }
    }

    private fun observeMealDetails(){
        mealViewModel.observeMealDetailsLiveData().observe(this, Observer {
            hasLoaded()
            infoBinding.apply {
                tvCategory.text = it.strCategory
                tvLocation.text = it.strArea
                tvInstructionsSteps.text = it.strInstructions
                youtubeLink = it.strYoutube.toString()
                mealToSave = it
            }
        })
    }

    private fun openYoutubeVideo(){
        val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
        startActivity(youtubeIntent)
    }
}