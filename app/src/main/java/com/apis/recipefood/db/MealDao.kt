package com.apis.recipefood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.pojo.MealList

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert (meal: Meal)

    @Delete
     suspend fun delete(meal: Meal)

    @Query("SELECT * FROM Meal")
    suspend fun getAllMeals():LiveData<List<Meal>>

    @Query("SELECT * FROM Meal WHERE idMeal=:id")
    suspend fun getMealByid(id:String):MealList







}