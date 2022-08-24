package com.apis.recipefood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.apis.recipefood.pojo.Meal

@Database (entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverters::class)
abstract class MealDataBase:RoomDatabase() {

    abstract fun mealDao():MealDao

    companion object {

        @Volatile
        private var INSTANCE: MealDataBase? = null
        @Synchronized
        fun getDatabase(context: Context): MealDataBase{
            if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                    context,
                    MealDataBase::class.java,
                    "mealdb"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDataBase
        }
    }
}