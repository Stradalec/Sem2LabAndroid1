package com.example.sem2labandroid1

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: ForecastAdapter
    private var forecastData: List<ForecastItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        viewModel = WeatherViewModel(
            retrofit.create(OpenWeatherMapService::class.java),
            resources
        )

        adapter= ForecastAdapter(ForecastDiffCallback())
        findViewById<RecyclerView>(R.id.rView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }



        viewModel.forecastData.observe(this) { data ->
            data?.let { adapter.submitList(it) }
        }

        viewModel.toastMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                }.show()
                viewModel.onToastShown()
            }
        }

        findViewById<Button>(R.id.btnGetWeather).setOnClickListener {
            val city = findViewById<EditText>(R.id.etCity).text.toString()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city)
            }
        }

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (forecastData != null) {
            outState.putSerializable("forecastData", forecastData as Serializable)
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        forecastData = savedInstanceState?.getSerializable("forecastData") as? List<ForecastItem>


        viewModel.forecastData.observe(this) { data ->
            data?.let { adapter.submitList(it) }
        }
    }
}