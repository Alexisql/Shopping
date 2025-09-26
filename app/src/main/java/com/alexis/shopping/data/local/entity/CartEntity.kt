package com.alexis.shopping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
    val price: Double,
)
