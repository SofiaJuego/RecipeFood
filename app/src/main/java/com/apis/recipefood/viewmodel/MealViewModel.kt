package com.apis.recipefood.viewmodel



import android.util.Log
import androidx.lifecycle.*
import com.apis.recipefood.pojo.*
import com.apis.recipefood.repository.DataBaseRepository
import com.apis.recipefood.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MealViewModel
@Inject
constructor( private val repository: MealRepository, private val dbRepository: DataBaseRepository) : ViewModel() {

    private val randomMealLiveData = MutableLiveData<Meal>()
    private var detailsMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<Meal>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var mealsLiveData = MutableLiveData<List<Meal>>()
    private var favoriteMealsLiveData= dbRepository.allMeals()
    private var bottomSheetLiveData = MutableLiveData<Meal>()
    private var searchMealLiveData=MutableLiveData<Meal>()



    init {
        getRandomMeal()
    }
    //<!-- OBTENGO COMIDA ALEATORIA -->

      private fun getRandomMeal()=viewModelScope.launch{
        repository.randomMeal().let { response ->
            if (response.isSuccessful) {
                randomMealLiveData.postValue(response.body()!!.meals[0])
            } else {
                Log.d("HomeFragment", "getRandomMeal:Error response ${response.message()}") } } }
    //Escuchador de comida aleatoria
    fun observerRamdonMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }


    //<!-- OBTENGO DETALLE DE COMIDA -->

    fun getDetailMeal(id:String)=viewModelScope.launch{
        repository.detailMeal(id).let { response ->
            if (response.isSuccessful){
                detailsMealLiveData.postValue(response.body()!!.meals[0])
            } else {
                Log.d("DetailMealActivity", "getDetailMeal: Error response ${response.message()}")} } }
    //Escuchador detalle
    fun observerDetailMealLiveData():LiveData<Meal>{
        return detailsMealLiveData
    }


    //<!-- OBTENGO COMIDA POPULARES -->

    fun getPopularItems()= viewModelScope.launch{
       repository.popularMeal("Seafood").let {  response ->
           if (response.isSuccessful){
               popularItemsLiveData.postValue(response.body()?.meals)
           }else{Log.d("HomeFragment", "getRandomMeal:Error response ${response.message()}")}
       }
    }

    //Escuchador de items populares
    fun observerPopularItemsLiveData():LiveData<List<Meal>>{
        return popularItemsLiveData
    }


    //<!-- OBTENGO CATEGORIAS -->

    fun getCategories()=viewModelScope.launch {
        repository.categoriesMeal().let { response ->
            if (response.isSuccessful) {
                categoriesLiveData.postValue(response.body()?.categories)
            } else {
                Log.d("HomeFragment", "getCategories:Error response ${response.message()}")
            }
        }

    }

    //Escuchador de lista de categorias
    fun observerCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    //<!-- OBTENGO COMIDAS POR CATEGORIA -->
    fun getMealsByCategory(categoryName:String)=viewModelScope.launch{
        repository.mealByCategory(categoryName).let{ response ->
            if (response.isSuccessful){
                mealsLiveData.postValue(response.body()?.meals)
            } else{
                Log.d("CategoryMealsActivity", "getMealByCategory:Error response ${response.message()}")

            }

                }
            }
    fun observerMealsLiveData():LiveData<List<Meal>>{
        return mealsLiveData
    }
    //Bottomsheet dialog
    fun getMealById(id: String)=viewModelScope.launch{
        repository.detailMeal(id).let { response ->
            if (response.isSuccessful) {
                bottomSheetLiveData.value = response.body()?.meals?.first()

            } else {
                Log.e("HomeFragment", "getMealByDetail:Error response ${response.message()}")
            }

        }
    }

    fun observerBottomSheetMeal():LiveData<Meal> {
        return bottomSheetLiveData }

    //<!--COMIDA FAVORITA-->

    fun observerFavoritesMealsLiveData():LiveData<List<Meal>> {
        return favoriteMealsLiveData
    }



    fun insertMeal(meal: Meal){
        viewModelScope.launch (Dispatchers.IO){
            dbRepository.insertFavorite(meal)
            withContext(Dispatchers.IO){

            }
        }

    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
           dbRepository.deleteFavorite(meal)
        }
    }
    //<!--BUSCAR RECETAS-->


    fun searchMeal(search:String)=viewModelScope.launch{
        repository.searchBymeals(search).let { response ->
            if (response.isSuccessful){
            searchMealLiveData.postValue(response.body()?.meals!![0])

            }else {
                Log.e("HomeFragment", "getSearch:Error response ${response.message()}")
            }
        }

    }

    fun observerSearchMealLiveData():LiveData<Meal> = searchMealLiveData



}