package com.apis.recipefood.repository


import com.apis.recipefood.db.MealDataBase
import com.apis.recipefood.pojo.Meal
import javax.inject.Inject

class DataBaseRepository
@Inject constructor(private val mealDataBase: MealDataBase){

    suspend fun insertFavorite (meal:Meal) {
        mealDataBase.mealDao().insert(meal)
    }

    suspend fun deleteFavorite(meal: Meal){
        mealDataBase.mealDao().delete(meal)
    }

    suspend fun allMeals(){
        mealDataBase.mealDao().getAllMeals()
    }

    suspend fun getById(id:String){
        mealDataBase.mealDao().getMealByid(id)

    }
}