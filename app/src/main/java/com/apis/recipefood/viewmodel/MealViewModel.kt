package com.apis.recipefood.viewmodel



import android.util.Log
import androidx.lifecycle.*
import com.apis.recipefood.db.MealDao
import com.apis.recipefood.pojo.*
import com.apis.recipefood.repository.DataBaseRepository
import com.apis.recipefood.repository.MealRepository
import com.apis.recipefood.retrofit.MealApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel
@Inject
constructor( private val repository: MealRepository, private val dbRepository: DataBaseRepository) : ViewModel() {

    @Inject
    lateinit var mealDao: MealDao
    @Inject
    lateinit var mealApi: MealApi

    private val randomMealLiveData = MutableLiveData<Meal>()
    private var detailsMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryMeals>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()

    private var mealsLiveData = MutableLiveData<List<CategoryMeals>>()

    //<!-- OBTENGO COMIDA ALEATORIA -->

      fun getRandomMeal()=viewModelScope.launch{
        repository.randomMeal().let { response ->
            if (response.isSuccessful) {
                randomMealLiveData.postValue(response.body()!!.meals[0])
            } else {
                Log.d("HomeFragment", "getRandomMeal:Error response ${response.message()}")
            }
        }
      }
    //Escuchador de comida aleatoria
    fun observerRamdonMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    //<!-- OBTENGO DETALLE DE COMIDA -->

    fun getDetailMeal(id:String)=viewModelScope.launch{
        repository.detailMeal(id).let { response ->
            if (response.isSuccessful){
                detailsMealLiveData.value = response.body()!!.meals[0]
            } else {
                Log.d("DetailMealActivity", "getDetailMeal: Error response ${response.message()}")}
        }


    }
    //Escuchador detalle
    fun observerDetailMealLiveData():LiveData<Meal>{
        return detailsMealLiveData
    }


    //<!-- OBTENGO COMIDA POPULARES -->

    fun getPopularItems()= viewModelScope.launch{
       repository.popularMeal("Seafood").let {  response ->
           if (response.isSuccessful){
               popularItemsLiveData.postValue(response.body()!!.meals)
           }else{Log.d("HomeFragment", "getRandomMeal:Error response ${response.message()}")}
       }
    }

    //Escuchador de items populares
    fun observerPopularItemsLiveData():LiveData<List<CategoryMeals>>{
        return popularItemsLiveData
    }


    //<!-- OBTENGO CATEGORIAS -->

    fun getCategories()=viewModelScope.launch {
        repository.categoriesMeal().let { response ->
            if (response.isSuccessful) {
                categoriesLiveData.postValue(response.body()!!.categories)
            } else {
                Log.d("HomeFragment", "getCategories:Error response ${response.message()}")
            }
        }

    }

    //Escuchador de lista de categorias
    fun observerCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    //<!-- OBTENGO COMIDA POR CATEGORIA -->
    fun getMealsByCategory(categoryName:String)=viewModelScope.launch{
        repository.mealByCategory(categoryName).let{ response ->
            if (response.isSuccessful){
                mealsLiveData.postValue(response.body()!!.meals)
            } else{
                Log.d("CategoryMealsActivity", "getMealByCategory:Error response ${response.message()}")

            }

                }
            }
    fun observerMealsLiveData():LiveData<List<CategoryMeals>>{
        return mealsLiveData
    }


    //<!--COMIDA FAVORITA-->

    fun observerFavoritesLiveData(meal: Meal) {
        viewModelScope.launch {
        dbRepository.allMeals()
    }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            dbRepository.insertFavorite(meal)
        }

    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDao.delete(meal)
        }
    }



}