package com.example.stockelectronico.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Insert
    suspend fun insertar(producto: Producto)

    @Query("SELECT * FROM productos ORDER BY id DESC")
    fun obtenerProductos(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun obtenerProducto(id: Int): Producto?

    @Update
    suspend fun actualizar(producto: Producto)

    @Delete
    suspend fun eliminar(producto: Producto)

    @Query("""
        SELECT * FROM productos
        WHERE nombre LIKE '%' || :texto || '%'
        OR proveedor LIKE '%' || :texto || '%'
    """)
    fun buscarProductos(texto: String): Flow<List<Producto>>
}