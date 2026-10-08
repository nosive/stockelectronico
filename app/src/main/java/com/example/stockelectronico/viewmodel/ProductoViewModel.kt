package com.example.stockelectronico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.stockelectronico.data.Producto
import com.example.stockelectronico.data.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    val productos = repository.obtenerProductos()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _productoSeleccionado = MutableStateFlow<Producto?>(null)
    val productoSeleccionado: StateFlow<Producto?> =
        _productoSeleccionado.asStateFlow()

    fun cambiarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    fun agregarProducto(producto: Producto) {

        viewModelScope.launch {

            repository.insertar(producto)
        }
    }

    fun actualizarProducto(producto: Producto) {

        viewModelScope.launch {

            repository.actualizar(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {

        viewModelScope.launch {

            repository.eliminar(producto)
        }
    }

    fun seleccionarProducto(producto: Producto) {

        _productoSeleccionado.value = producto
    }

    fun limpiarProductoSeleccionado() {

        _productoSeleccionado.value = null
    }
}


class ProductoViewModelFactory(
    private val repository: ProductoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}