package com.apis.recipefood.retrofit


import com.apis.recipefood.pojo.CategoryList
import com.apis.recipefood.pojo.MealList
import com.apis.recipefood.pojo.MealsCategoriesList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET ("random.php")
   suspend fun getRandomMeals():Response<MealList>

    @GET("lookup.php?")
    suspend fun getDetailsMeals(@Query("i")id:String): Response<MealList>

    @GET("filter.php?")
    suspend fun getPopularItems(@Query("c")categoryName:String):Response<CategoryList>

    @GET("categories.php")
    suspend fun getCategories():Response<MealsCategoriesList>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") categoryMealName:String): Response<CategoryList>

    @GET("search.php?")
    suspend fun getSearchMealByName(@Query("s")search:String):Response<MealList>


}