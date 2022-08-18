package com.apis.recipefood.activites


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    private lateinit var youtubeLink:String
    private val mealMvvm:MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomMealInformation()
        setInformationInView()

        loadingCase()
        mealMvvm.getDetailMeal(mealId)
        observerDetailMeal()

        onYoutubeImageClick()
    }
    //Cuando hago click en imagen de youtube
    private fun onYoutubeImageClick() {

        binding.ivYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun observerDetailMeal() {
        mealMvvm.observerDetailMealLiveData().observe(this
        ) { t ->
            onResponseCase()
            binding.tvCategory.text = "Categoría: ${t!!.strCategory}"
            binding.tvOrigen.text = "Origen: ${t.strArea}"
            binding.tvContenido.text = t.strInstructions

            youtubeLink = t.strYoutube

        }
    }


    //Obtengo la informacion en la view
    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.ivMealDetail)

        binding.collapsingToolbar.title= mealName
    }

    //Obtengo informacion de la comida aleatoria
    private fun getRandomMealInformation() {
        val intent= intent
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!
    }

    //Cuando la imagen de la api este cargando
    private fun loadingCase(){
        //Mostrar
        binding.progressBar.visibility = View.VISIBLE
        //Ocultar
        binding.btnFavorite.visibility = View.INVISIBLE
        binding.tvInstrucciones.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvOrigen.visibility = View.INVISIBLE
        binding.ivYoutube.visibility = View.INVISIBLE

    }

    //Cuando la api ya cargo la imagen correctamente
    private fun onResponseCase(){
        //Ocultar
        binding.progressBar.visibility = View.INVISIBLE
       //Mostrar
        binding.btnFavorite.visibility = View.VISIBLE
        binding.tvInstrucciones.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvOrigen.visibility = View.VISIBLE
        binding.ivYoutube.visibility = View.VISIBLE

    }


}