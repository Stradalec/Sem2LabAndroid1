package com.example.sem2labandroid1

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitClient {

    val weatherService: OpenWeatherMapService by lazy {

        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherMapService::class.java)
    }
}
interface OpenWeatherMapService {
    companion object {
        const val app_id = "ab1700c1546c934f4cdc73aeab7eb4d3"
        const val constUnits = "metric"
    }
    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("units") units: String = constUnits,
        @Query("appid") apiKey: String = app_id
    ): Call<Forecast>
}