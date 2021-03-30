package com.zz.mycoolweatherapp.ui.weather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zz.mycoolweatherapp.R
import com.zz.mycoolweatherapp.databinding.ActivityWeatherBinding
import com.zz.mycoolweatherapp.util.InjectorUtil
import com.zz.mycoolweatherapp.util.LessDrawerListener
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title.*


class WeatherActivity : AppCompatActivity() {

    // databinding
    val binding by lazy {
        DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather)
    }
    // viewModel
    val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getWeatherModelFactory()).get(WeatherViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.weatherId.value = if(viewModel.isWeatherCached()) {
            viewModel.getWeatherCached()?.basic?.weatherId
        }else{
            intent.getStringExtra("weatherId")!!
        }

        viewModel.getWeather()

        navButton.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        drawerLayout.setDrawerListener(object : LessDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                //注意要清除 FLAG_TRANSLUCENT_STATUS flag
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary))
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                if (Build.VERSION.SDK_INT >= 21) {
                    val decorView = window.decorView
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    window.statusBarColor = Color.TRANSPARENT
                }
                super.onDrawerClosed(drawerView)
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearWeatherCached("weather")
    }

}