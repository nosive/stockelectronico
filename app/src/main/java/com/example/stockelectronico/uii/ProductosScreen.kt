package com.example.stockelectronico.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockelectronico.data.Producto
import com.example.stockelectronico.viewmodel.ProductoViewModel

@Composable
fun ProductosScreen(
    viewModel: ProductoViewModel,
    onAgregar: () -> Unit,
    onEditar: (Producto) -> Unit
) {

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val productos = viewModel.productos.collectAsState(
        initial = emptyList()
    ).value

    val productosFiltrados = productos.filter { producto ->

        producto.nombre.contains(
            textoBusqueda,
            ignoreCase = true
        ) ||
                producto.proveedor.contains(
                    textoBusqueda,
                    ignoreCase = true
                )
    }

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(
                onClick = onAgregar
            ) {
                Text("+")
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "INVENTARIO"
            )

            OutlinedTextField(

                value = textoBusqueda,

                onValueChange = {
                    textoBusqueda = it
                },

                label = {
                    Text("Buscar producto")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onAgregar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar producto")
            }

            if (productosFiltrados.isEmpty()) {

                Text(
                    text = if (productos.isEmpty()) {
                        "No hay productos registrados"
                    } else {
                        "No se encontraron productos"
                    }
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(productosFiltrados) { producto ->

                        ProductoItem(
                            producto = producto,
                            onEditar = {
                                onEditar(producto)
                            },
                            onEliminar = {
                                viewModel.eliminarProducto(producto)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductoItem(
    producto: Producto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Text(
            text = "Producto: ${producto.nombre}"
        )

        Text(
            text = "Descripción: ${producto.descripcion}"
        )

        Text(
            text = "Cantidad: ${producto.cantidad}"
        )

        Text(
            text = "Precio: $${producto.precio}"
        )

        Text(
            text = "Proveedor: ${producto.proveedor}"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onEditar
            ) {
                Text("Editar")
            }

            Button(
                onClick = onEliminar
            ) {
                Text("Eliminar")
            }
        }
    }
}