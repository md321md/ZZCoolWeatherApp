package com.zz.mycoolweatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zz.mycoolweatherapp.R
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment
import com.zz.mycoolweatherapp.ui.weather.WeatherActivity
import com.zz.mycoolweatherapp.util.InjectorUtil


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        var viewModel = ViewModelProvider(this, InjectorUtil.getMainModelFactory()).get(MainViewModel::class.java)


        if(viewModel.getWeatherCached()){
            startActivity(Intent(this,WeatherActivity::class.java))
            // 跳转weather详情页
        }else{
            // 静态引用fragment
            supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment()).commit()
        }

    }

    override fun onRestart() {
        super.onRestart()
        // 静态引用fragment
        supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment()).commitAllowingStateLoss()

    }

}