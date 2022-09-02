package com.apis.recipefood.repository

import com.apis.recipefood.retrofit.MealApi
import javax.inject.Inject

open class MealRepository
@Inject
constructor(private val mealApi: MealApi){


    suspend fun randomMeal()=mealApi.getRandomMeals()
    suspend fun detailMeal(id:String)=mealApi.getDetailsMeals(id)
    suspend fun popularMeal(categoryName:String)=mealApi.getPopularItems(categoryName)
    suspend fun categoriesMeal()=mealApi.getCategories()
    suspend fun mealByCategory(categoryMealName:String)=mealApi.getMealsByCategory(categoryMealName)



}

