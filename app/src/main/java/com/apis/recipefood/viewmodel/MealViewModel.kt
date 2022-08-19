package com.apis.recipefood.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apis.recipefood.pojo.*
import com.apis.recipefood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MealViewModel (application: Application) : AndroidViewModel(application){
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var detailsMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()


    //<!-- OBTENGO COMIDA ALEATORIA -->

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

    //<!-- OBTENGO DETALLE DE COMIDA -->

    fun getDetailMeal(id:String){
        RetrofitInstance.api.getDetailsMeals(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    detailsMealLiveData.value = response.body()!!.meals[0]
                }
                else return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("DetailMealActivity", t.message.toString())
            }

        })

    }
    //Escuchador detalle
    fun observerDetailMealLiveData():LiveData<Meal>{
        return detailsMealLiveData
    }


    //<!-- OBTENGO COMIDA POPULARES -->

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString()) }

        })
    }

    //Escuchador de items populares
    fun observerPopularItemsLiveData():LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }


    //<!-- OBTENGO CATEGORIAS -->

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<MealsCategoriesList>{
            override fun onResponse(call: Call<MealsCategoriesList>, response: Response<MealsCategoriesList>){
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }

            }

            override fun onFailure(call: Call<MealsCategoriesList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())

            }

        })
    }

    //Escuchador de lista de categorias
    fun observerCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }




}