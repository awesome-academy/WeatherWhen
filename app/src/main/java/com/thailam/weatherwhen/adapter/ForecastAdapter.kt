package com.thailam.weatherwhen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thailam.weatherwhen.R
import com.thailam.weatherwhen.data.model.DisplayedForecast
import com.thailam.weatherwhen.utils.DateFormatUtils
import com.thailam.weatherwhen.utils.appendUnit
import kotlinx.android.synthetic.main.forecast_recycler_item.view.*

class ForecastAdapter : ListAdapter<DisplayedForecast, ForecastAdapter.ViewHolder>(ForecastDiffUtilCallback()) {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_recycler_item, parent, false)
        context = parent.context
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forecast: DisplayedForecast) {
            with(itemView) {
                textViewForecastsDay.text =
                    DateFormatUtils.longToDayOfWeek(context, forecast.epochDate.toLong(), true)
                textViewForecastsTemp.text = StringBuilder().appendUnit(
                    forecast.temperatureValue.toString(),
                    forecast.temperatureUnit
                )
            }
        }
    }
}

class ForecastDiffUtilCallback : DiffUtil.ItemCallback<DisplayedForecast>() {
    override fun areItemsTheSame(oldItem: DisplayedForecast, newItem: DisplayedForecast): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DisplayedForecast, newItem: DisplayedForecast): Boolean =
        oldItem.temperatureValue == newItem.temperatureValue && oldItem.currentCondition == newItem.currentCondition
}
