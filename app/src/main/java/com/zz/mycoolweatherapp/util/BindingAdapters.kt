package com.zz.mycoolweatherapp.util

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.zz.mycoolweatherapp.R
import com.zz.mycoolweatherapp.data.model.weather.Weather
import com.zz.mycoolweatherapp.databinding.ForecastItemBinding
import java.util.zip.Inflater

@BindingAdapter("bind:loadBingPic")
fun ImageView.loadBingPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}
@BindingAdapter("bind:showForecast")
fun LinearLayout.showForecast(weather: Weather?) {
    this.removeAllViews()
    weather?.forecastList?.forEach {
        var view : LinearLayout =
            LayoutInflater.from(this.context).inflate(R.layout.forecast_item,this,false) as LinearLayout

        var binding : ForecastItemBinding = DataBindingUtil.bind<ForecastItemBinding>(view)!!
        binding.forecast = it
        this.addView(view)
    }
}