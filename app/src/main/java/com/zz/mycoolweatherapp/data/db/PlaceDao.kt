package com.zz.mycoolweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zz.mycoolweatherapp.data.model.place.City
import com.zz.mycoolweatherapp.data.model.place.County
import com.zz.mycoolweatherapp.data.model.place.Province

@Dao
interface PlaceDao {
    // 数据库中获取省列表
    @Query("SELECT * FROM table_province ORDER BY id ASC")
    fun getProvinceList() : MutableList<Province>

    // 省列表持久到数据库中
    @Insert
    fun saveProvinceList(provinceList:List<Province>)

    // 数据库中获取市列表
    @Query("SELECT * FROM table_city WHERE provinceId = (:provinceId)")
    fun getCityList(provinceId: Int) : MutableList<City>

    // 市列表持久到数据库中
    @Insert
    fun saveCityList(cityList:List<City>)

    // 数据库中获取区列表
    @Query("SELECT * FROM table_county WHERE cityId = (:cityId)")
    fun getCountyList(cityId : Int) : MutableList<County>

    // 区列表持久到数据库中
    @Insert
    fun saveCountyList(countyList:List<County>)
}