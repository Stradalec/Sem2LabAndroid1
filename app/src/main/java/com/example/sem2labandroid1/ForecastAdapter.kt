package com.example.sem2labandroid1

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ForecastAdapter(private val diffCallback: ForecastDiffCallback) :
    ListAdapter<ForecastItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return if (viewType == 0) ViewHolderHot(view) else ViewHolderCold(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecastItem = getItem(position)
        if (holder is ViewHolderHot) {
            holder.bind(forecastItem)
            holder.itemView.setPadding(0,0, 0, 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#FFC080"))
        } else if (holder is ViewHolderCold) {
            holder.bind(forecastItem)
            holder.itemView.setPadding(5,5, 5, 5)
            holder.itemView.setBackgroundColor(Color.parseColor("#80B1FF"))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val forecastItem = getItem(position)
        return if (forecastItem.main.temp > 0) 0 else 1
    }

    class ViewHolderHot(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecastItem: ForecastItem) {
            itemView.findViewById<TextView>(R.id.date).text = forecastItem.dt_txt
            itemView.findViewById<TextView>(R.id.temperature).text = forecastItem.main.temp.toString()
            val iconUrl = "https://openweathermap.org/img/wn/${forecastItem.weather[0].icon}@2x.png"
            Glide.with(itemView.context)
                .load(iconUrl)
                .into(itemView.findViewById(R.id.temperature_icon))
        }
    }

    class ViewHolderCold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecastItem: ForecastItem) {
            itemView.findViewById<TextView>(R.id.date).text = forecastItem.dt_txt
            itemView.findViewById<TextView>(R.id.temperature).text = forecastItem.main.temp.toString()
            val iconUrl = "https://openweathermap.org/img/wn/${forecastItem.weather[0].icon}@2x.png"
            Glide.with(itemView.context)
                .load(iconUrl)
                .into(itemView.findViewById(R.id.temperature_icon))
        }
    }


}