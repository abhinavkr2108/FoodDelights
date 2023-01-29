package com.example.fooddelights.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fooddelights.apiclasses.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverters::class)
abstract class MealDatabase: RoomDatabase() {
     abstract fun mealDao(): MealDao

     companion object{
         @Volatile
         var INSTANCE : MealDatabase? = null

         fun getInstance(context: Context): MealDatabase{
             if (INSTANCE==null){
                 synchronized(this){
                     INSTANCE = Room.databaseBuilder(context, MealDatabase::class.java, "meal.db").build()
                 }
             }
            return INSTANCE!!
         }
     }
}

