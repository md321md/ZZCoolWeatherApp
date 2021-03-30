package com.zz.mycoolweatherapp.data.db

import androidx.room.*
import com.zz.mycoolweatherapp.CoolWeatherApplication
import com.zz.mycoolweatherapp.data.model.place.City
import com.zz.mycoolweatherapp.data.model.place.County
import com.zz.mycoolweatherapp.data.model.place.Province

@Database(entities = arrayOf(City::class, County::class,Province::class), version = 1, exportSchema = false)
abstract class CoolWeatherDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object{
        private var instance : CoolWeatherDatabase ?= null
        fun getInstance() : CoolWeatherDatabase{
            if(instance == null){
                synchronized(CoolWeatherDatabase::class.java){
                    if(instance == null){
                        instance = Room.databaseBuilder(
                            CoolWeatherApplication.context,
                            CoolWeatherDatabase::class.java, "zzcoolweather"
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }


}