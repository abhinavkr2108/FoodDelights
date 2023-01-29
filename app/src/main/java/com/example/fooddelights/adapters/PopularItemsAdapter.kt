package com.example.fooddelights.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelights.R
import com.example.fooddelights.apiclasses.CategoryMeals

import com.example.fooddelights.databinding.PopularItemsLayoutBinding
import com.example.fooddelights.ui.activities.InfoActivity
import com.example.fooddelights.ui.fragments.HomeFragment
import kotlinx.android.synthetic.main.popular_items_layout.view.*

class PopularItemsAdapter: RecyclerView.Adapter<PopularItemsAdapter.PopularItemsViewHolder>() {

    private lateinit var layoutBinding: PopularItemsLayoutBinding
    lateinit var onItemClick: ((CategoryMeals)-> Unit)
    private val differCallback= object : DiffUtil.ItemCallback<CategoryMeals>(){
        override fun areItemsTheSame(oldItem: CategoryMeals, newItem: CategoryMeals): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: CategoryMeals, newItem: CategoryMeals): Boolean {
            return oldItem == newItem
        }
    }

    val mealDiffer = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemsViewHolder {
        return PopularItemsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.popular_items_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PopularItemsViewHolder, position: Int) {

        val item = mealDiffer.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(item.strMealThumb).into(imgPopularItems)
            setOnClickListener {
                onItemClick.invoke(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return mealDiffer.currentList.size
    }

    inner class PopularItemsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    }
}