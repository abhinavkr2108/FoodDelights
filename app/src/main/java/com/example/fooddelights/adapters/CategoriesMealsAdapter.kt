package com.example.fooddelights.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelights.R
import com.example.fooddelights.apiclasses.Category
import com.example.fooddelights.apiclasses.CategoryMeals
import com.example.fooddelights.ui.fragments.CategoriesFragmentDirections
import kotlinx.android.synthetic.main.category_items_layout.view.*

class CategoriesMealsAdapter : RecyclerView.Adapter<CategoriesMealsAdapter.CategoriesMealsViewHolder>(){

    lateinit var onItemClick: ((CategoryMeals)-> Unit)

    val categoryDifferCallback = object : DiffUtil.ItemCallback<CategoryMeals>(){
        override fun areItemsTheSame(oldItem: CategoryMeals, newItem: CategoryMeals): Boolean {
           return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: CategoryMeals, newItem: CategoryMeals): Boolean {
            return oldItem == newItem
        }
    }

    val categoryMealDiffer = AsyncListDiffer(this, categoryDifferCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesMealsViewHolder {
        return CategoriesMealsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_items_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoriesMealsViewHolder, position: Int) {
        val mealPosition = categoryMealDiffer.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(mealPosition.strMealThumb).into(imgCategoryMeal)
            tvCategoryMealName.text = mealPosition.strMeal
            setOnClickListener {
                onItemClick.invoke(mealPosition)
            }

        }
    }

    override fun getItemCount(): Int {
       return categoryMealDiffer.currentList.size
    }

    inner class CategoriesMealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}