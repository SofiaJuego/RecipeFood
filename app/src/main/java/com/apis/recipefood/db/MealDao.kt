package com.apis.recipefood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apis.recipefood.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert (meal: Meal)

    @Delete
     suspend fun delete(meal: Meal)

     @Query("DELETE FROM Meal WHERE idMeal=:id")
     suspend fun deteleMealById(id: String)

    @Query("SELECT * FROM Meal")
    fun getAllMeals():LiveData<List<Meal>>

    @Query("SELECT * FROM Meal WHERE idMeal=:id")
    suspend fun getMealByid(id:String):Meal







}