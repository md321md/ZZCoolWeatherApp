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
            startActivityForResult(Intent(this,WeatherActivity::class.java),1001)
            // 跳转weather详情页
        }else{
            // 静态引用fragment
            supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment()).commit()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.beginTransaction().replace(R.id.container, ChooseAreaFragment()).commit()
    }
}