package com.apis.recipefood.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apis.recipefood.pojo.Meal
import com.apis.recipefood.pojo.MealList
import com.apis.recipefood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MealViewModel (application: Application) : AndroidViewModel(application){
    private var randomMealLiveData = MutableLiveData<Meal>()

    //OBTENGO COMIDA ALEATORIA
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeals().enqueue(object : Callback<MealList> {
            //Conectando con la api
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString()) }
        })
    }
    //Escuchador de comida aleatoria
    fun observerRamdonMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

}