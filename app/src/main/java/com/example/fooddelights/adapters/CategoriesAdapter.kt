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
import com.example.fooddelights.ui.fragments.HomeFragmentDirections
import kotlinx.android.synthetic.main.home_category_layout.view.*

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    val differCallback = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val categoryDiffer = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_category_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val itemCategory = categoryDiffer.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(itemCategory.strCategoryThumb).into(imgCategory)
            tvCategoryName.text = itemCategory.strCategory
            setOnClickListener {
                val direction = HomeFragmentDirections.actionHomeFragmentToCategoryFoodItemsFragment(itemCategory)
                it.findNavController().navigate(direction)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryDiffer.currentList.size
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}