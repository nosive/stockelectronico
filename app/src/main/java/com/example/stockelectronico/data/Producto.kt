package com.example.stockelectronico.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val descripcion: String,

    val cantidad: Int,

    val precio: Double,

    val proveedor: String
)