package com.zz.mycoolweatherapp.data.model.place

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "table_province")
class Province(@ColumnInfo(name="name") val name:String,  @PrimaryKey @ColumnInfo(name="id") val id : Int)
