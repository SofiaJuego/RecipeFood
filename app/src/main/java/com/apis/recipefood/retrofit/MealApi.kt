package com.apis.recipefood.retrofit


import com.apis.recipefood.pojo.CategoryList
import com.apis.recipefood.pojo.MealList
import com.apis.recipefood.pojo.MealsCategoriesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET ("random.php")
    fun getRandomMeals():Call<MealList>

    @GET("lookup.php?")
    fun getDetailsMeals(@Query("i")id:String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")categoryName:String):Call<CategoryList>

    @GET("categories.php")
    fun getCategories():Call<MealsCategoriesList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryMealName:String): Call<CategoryList>


}