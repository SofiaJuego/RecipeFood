package com.apis.recipefood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apis.recipefood.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert (meal: Meal)

    @Delete
     fun delete(meal: Meal)

    @Query("SELECT * FROM Meal")
    fun getAllMeals():LiveData<List<Meal>>




}