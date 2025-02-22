package com.example.sem2labandroid1

import androidx.recyclerview.widget.DiffUtil

class ForecastDiffCallback : DiffUtil.ItemCallback<ForecastItem>() {
    override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
        return oldItem == newItem &&
                oldItem.main.temp == newItem.main.temp &&
                oldItem.main.pressure == newItem.main.pressure
    }
}