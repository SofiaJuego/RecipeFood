package com.apis.recipefood.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apis.recipefood.db.MealDataBase

class ViewModelFactory(private val mealDataBase: MealDataBase):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelFactory(mealDataBase) as T
    }

}