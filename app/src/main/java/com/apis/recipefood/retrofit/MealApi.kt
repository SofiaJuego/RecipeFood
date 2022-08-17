package com.apis.recipefood.retrofit


import com.apis.recipefood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET ("random.php")
    fun getRandomMeals():Call<MealList>

}