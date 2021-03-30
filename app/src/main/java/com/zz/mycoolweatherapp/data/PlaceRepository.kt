package com.zz.mycoolweatherapp.data

import com.zz.mycoolweatherapp.data.db.PlaceDao
import com.zz.mycoolweatherapp.data.network.CoolWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PlaceRepository private constructor(private val dao : PlaceDao, private val netWork: CoolWeatherNetWork){

    // withContext线程切换 这里线程切换到IO线程执行 等待执行完毕
    suspend fun getProvinceList() = withContext(Dispatchers.IO){
        var list= dao.getProvinceList()
        if(list.isEmpty()){
            list = netWork.fetchProvinceList()
            dao.saveProvinceList(list)
        }
        list
    }
    suspend fun getCityList(provinceId : Int) = withContext(Dispatchers.IO){
        var list= dao.getCityList(provinceId)
        if(list.isEmpty()){
            list = netWork.fetchCitiesList(provinceId)
            list.forEach { it.provinceId = provinceId }
            dao.saveCityList(list)
        }
        list
    }

    suspend fun getCountyList(provinceId : Int,cityId : Int) = withContext(Dispatchers.IO){
        var list= dao.getCountyList(cityId)
        if(list.isEmpty()){
            list = netWork.fetchCountiesList(provinceId,cityId)
            list.forEach { it.cityId = cityId }
            dao.saveCountyList(list)
        }
        list
    }

    companion object {

        private var placeRepository : PlaceRepository? = null

        fun getInstance(dao : PlaceDao,coolWeatherNetWork: CoolWeatherNetWork) : PlaceRepository{
            if(placeRepository == null){

                synchronized(PlaceRepository::class.java){

                    if(placeRepository == null){

                        placeRepository = PlaceRepository(dao,coolWeatherNetWork)

                    }

                }

            }

            return placeRepository!!
        }
    }
}