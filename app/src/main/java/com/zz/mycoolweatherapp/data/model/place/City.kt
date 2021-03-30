package com.zz.mycoolweatherapp.data.model.place

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "table_city")
class City (@ColumnInfo(name="name") val name:String, @PrimaryKey @ColumnInfo(name="id") val id:Int,var provinceId : Int)