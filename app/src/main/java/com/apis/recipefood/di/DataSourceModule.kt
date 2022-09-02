package com.apis.recipefood.di

import android.content.Context
import androidx.room.Room
import com.apis.recipefood.db.MealDataBase
import com.apis.recipefood.retrofit.MealApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


//Fuente de datos

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    //Retrofit Instance

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl()= "https://www.themealdb.com/api/json/v1/1/"

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl")baseUrl:String):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }
    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): MealApi =
        retrofit.create(MealApi::class.java)


    //Room Instance
    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context)
    =
        Room.databaseBuilder(
            context,
            MealDataBase::class.java,"mealdb")
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMealDao(insRoom:MealDataBase) = insRoom.mealDao()




}