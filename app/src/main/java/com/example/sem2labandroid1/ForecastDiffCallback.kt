package com.example.sem2labandroid1

import androidx.recyclerview.widget.DiffUtil

class ForecastDiffCallback : DiffUtil.ItemCallback<ForecastItem>() {
    override fun areItemsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
        return oldItem.dt_txt == newItem.dt_txt
    }

    override fun areContentsTheSame(oldItem: ForecastItem, newItem: ForecastItem): Boolean {
        return oldItem == newItem
    }
}