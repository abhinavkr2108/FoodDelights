package com.example.fooddelights.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelights.R
import com.example.fooddelights.apiclasses.CategoryMeals
import com.example.fooddelights.apiclasses.Meal
import kotlinx.android.synthetic.main.category_items_layout.view.*

class FavoritesAdapter: RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    lateinit var onItemClick: ((Meal)-> Unit)

    val favDifferCallback = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val favDiffer = AsyncListDiffer(this, favDifferCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.category_items_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val favMealItem = favDiffer.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(favMealItem.strMealThumb).into(imgCategoryMeal)
            tvCategoryMealName.text = favMealItem.strMeal
            setOnClickListener {
                onItemClick.invoke(favMealItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return favDiffer.currentList.size
    }

    inner class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}