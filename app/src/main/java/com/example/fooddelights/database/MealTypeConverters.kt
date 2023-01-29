package com.example.fooddelights.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverters {
    @TypeConverter
    fun fromAnyToString(attributes: Any?): String{
        if (attributes==null){
            return ""
        }
        return attributes.toString()
    }

    @TypeConverter
    fun fromStringToAny(attributes: String?): Any{
        if (attributes==null){
            return ""
        }
        return attributes
    }
}