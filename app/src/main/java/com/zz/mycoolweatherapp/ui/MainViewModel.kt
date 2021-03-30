package com.zz.mycoolweatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zz.mycoolweatherapp.data.PlaceRepository
import com.zz.mycoolweatherapp.data.WeatherRepository
import com.zz.mycoolweatherapp.data.model.place.Province
import kotlinx.coroutines.launch

class MainViewModel(private val weatherRepository : WeatherRepository) : ViewModel(){

    fun getWeatherCached() = weatherRepository.isWeatherCached()

//    lateinit var provinces: MutableList<Province>
//
//    var dataList =  mutableListOf<String>()
//
//    fun getProvinces() {
//        launch{
//            provinces = placeRepository.getProvinceList()
//            dataList.addAll( provinces.map { it.provinceName } )
//        }
//    }
//
//
//    private fun launch(block : suspend ()-> Unit) = viewModelScope.launch {
//        try {
//            // 执行block之前做一些事情
//            // 开始加载进度显示dialog
//
//            dataList.clear()
//            block()
//
//            // 关闭加载进度显示dialog
//
//
//        }catch (t : Throwable){
//            // 关闭加载进度显示dialog
//
//            // 显示错误提示等
//        }
//
//
//    }
}