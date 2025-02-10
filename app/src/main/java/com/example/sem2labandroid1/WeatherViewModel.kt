package com.example.sem2labandroid1

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherViewModel(

    private val service: OpenWeatherMapService,
    private val resources: Resources
) : ViewModel() {


    private val _forecastData = MutableLiveData<List<ForecastItem>>()
    val forecastData: LiveData<List<ForecastItem>> = _forecastData

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun fetchWeather(city: String) {
        val apiKey = resources.getString(R.string.key)
        val call = service.getForecast(city, "metric", apiKey)

        call.enqueue(object : Callback<Forecast> {
            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                when {
                    response.code() == 404 ->
                        _toastMessage.value = "Город '$city' не найден"
                    response.body() == null ->
                        _toastMessage.value = "Ошибка формата данных"
                }
                if (response.isSuccessful) {
                    response.body()?.list?.let {
                        _forecastData.value = it
                    }
                }
            }

            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                _toastMessage.value = "Ошибка сети: ${t.localizedMessage}"
            }
        })
    }

    fun onToastShown() {
        _toastMessage.value = null
    }
}

interface OpenWeatherMapService {
    @GET("forecast")
    fun getForecast(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<Forecast>
}
