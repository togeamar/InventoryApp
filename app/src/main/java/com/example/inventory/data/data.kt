package com.example.inventory.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class data(
    @PrimaryKey(autoGenerate = true)
    val Id: Int=0,

    val name:String,
    val price:Double,
    val stock:Int
)

fun data.getformattedprice():String=
    NumberFormat.getCurrencyInstance().format(price)