package com.apis.recipefood.activites


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.apis.recipefood.constants.Constants.Companion.MEAL_ID
import com.apis.recipefood.constants.Constants.Companion.MEAL_NAME
import com.apis.recipefood.constants.Constants.Companion.MEAL_THUMB
import com.apis.recipefood.databinding.ActivityDetailMealBinding
import com.apis.recipefood.viewmodel.MealViewModel
import com.bumptech.glide.Glide

class DetailMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealBinding

    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private val mealMvvm:MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomMealInformation()
        setInformationInView()

        mealMvvm.getDetailMeal(mealId)
        observerDetailMeal()
    }


    @SuppressLint("SetTextI18n")
    private fun observerDetailMeal() {
        mealMvvm.observerDetailMealLiveData().observe(this
        ) { t ->
            binding.tvCategory.text = "Categoría: ${t!!.strCategory}"
            binding.tvOrigen.text = "Origen: ${t.strArea}"
            binding.tvContenido.text = t.strInstructions

        }
    }


    //Obtengo la informacion en la view
    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.ivMealDetail)
    }

    //Obtengo informacion de la comida aleatoria
    private fun getRandomMealInformation() {
        val intent= intent
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
    }
}