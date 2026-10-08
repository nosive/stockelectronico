package com.example.stockelectronico.data

class ProductoRepository(
    private val productoDao: ProductoDao
) {

    fun obtenerProductos() =
        productoDao.obtenerProductos()

    suspend fun insertar(producto: Producto) {
        productoDao.insertar(producto)
    }

    suspend fun actualizar(producto: Producto) {
        productoDao.actualizar(producto)
    }

    suspend fun eliminar(producto: Producto) {
        productoDao.eliminar(producto)
    }

    suspend fun obtenerProducto(id: Int): Producto? {
        return productoDao.obtenerProducto(id)
    }

    fun buscarProductos(texto: String) =
        productoDao.buscarProductos(texto)
}