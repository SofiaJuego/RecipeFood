package com.apis.recipefood.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apis.recipefood.pojo.Meal

@Database (entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverters::class)
abstract class MealDataBase:RoomDatabase() {

    abstract fun mealDao():MealDao
}