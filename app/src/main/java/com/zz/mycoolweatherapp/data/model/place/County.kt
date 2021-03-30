package com.zz.mycoolweatherapp.data.model.place

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "table_county")
class County (@ColumnInfo(name="name") val name : String,   @ColumnInfo(name="weather_id") val weather_id : String , @PrimaryKey val id : Int,var cityId : Int)