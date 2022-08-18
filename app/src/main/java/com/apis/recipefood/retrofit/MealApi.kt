package com.apis.recipefood.retrofit


import com.apis.recipefood.pojo.CategoryList
import com.apis.recipefood.pojo.MealList
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

}