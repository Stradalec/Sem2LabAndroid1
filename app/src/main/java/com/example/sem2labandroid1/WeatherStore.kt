package com.example.sem2labandroid1

class WeatherStore {
    var weathers: List<ForecastItem>? = null

    fun updateWeathers(newWeathers: List<ForecastItem>) {
        weathers = newWeathers
    }
}