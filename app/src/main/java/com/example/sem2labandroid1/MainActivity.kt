package com.example.sem2labandroid1

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
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
import androidx.fragment.app.viewModels

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: ForecastAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        viewModel = WeatherViewModel(
            RetrofitClient.weatherService,
            resources
        )

        adapter = ForecastAdapter(ForecastDiffCallback(), viewModel)
        findViewById<RecyclerView>(R.id.rView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        viewModel.forecastData.observe(this) { data ->
            data?.let { adapter.submitList(it) }
        }

        viewModel.isCelsius.observe(this) { isCelsius ->
            findViewById<ToggleButton>(R.id.toggleTempUnit).isChecked = !isCelsius
            adapter.notifyDataSetChanged()
        }

        findViewById<ToggleButton>(R.id.toggleTempUnit).setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleTemperatureUnit()
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


}