package com.zz.mycoolweatherapp.ui.area

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zz.mycoolweatherapp.data.PlaceRepository
import com.zz.mycoolweatherapp.data.model.place.City
import com.zz.mycoolweatherapp.data.model.place.County
import com.zz.mycoolweatherapp.data.model.place.Province
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment.Companion.LEVEL_CITY
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment.Companion.LEVEL_COUNTY
import com.zz.mycoolweatherapp.ui.area.ChooseAreaFragment.Companion.LEVEL_PROVINCE
import kotlinx.coroutines.launch

class ChooseAreaViewModel(private val placeRepository : PlaceRepository) : ViewModel( ) {

    // 数据刷新的标识
    var dataChanged = MutableLiveData<Int>()

    // 省市区等级得标识
    var level = MutableLiveData<Int>()

    // 是否选择到了天气
    var isWeathered = MutableLiveData<Boolean>()

    var province : Province? = null

    var city : City? = null

    var county : County? = null



    // progressDialog标识
    var isShowDialog = MutableLiveData<Boolean>()

    var dataList = mutableListOf<String>()

    var provinceList = mutableListOf<Province>()

    var cityList = mutableListOf<City>()

    var countyList = mutableListOf<County>()

    fun getProvinceList() {
        launch {
            provinceList = placeRepository.getProvinceList()
            dataList.addAll(provinceList.map { it.name })
            level.value = LEVEL_PROVINCE
        }
    }
    fun getCityList() = province?.let{
        launch {
            cityList = placeRepository.getCityList(it.id)
            dataList.addAll(cityList.map { it.name })
            level.value = LEVEL_CITY
        }
    }
    fun getCountyList() = city?.let {
        launch {
            countyList = placeRepository.getCountyList(it.provinceId,it.id)
            dataList.addAll(countyList.map { it.name })
            level.value = LEVEL_COUNTY
        }
    }
    private fun launch(block: suspend () -> Unit): Any = viewModelScope.launch {
        try {
            dataList.clear()
            isShowDialog.value = true
            block()
            dataChanged.value =dataChanged.value?.plus(1)
            isShowDialog.value = false

        }catch (t : Throwable){
            dataChanged.value =dataChanged.value?.plus(1)
            isShowDialog.value = false
        }
    }

    // 点击返回按钮
    fun onBack(){
        when(level.value){
            LEVEL_COUNTY -> {
                getCityList()
            }
            LEVEL_CITY -> {
                getProvinceList()
            }
        }
    }

}