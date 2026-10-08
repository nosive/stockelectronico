package com.example.stockelectronico.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockelectronico.data.Producto
import com.example.stockelectronico.viewmodel.ProductoViewModel

@Composable
fun AgregarProductoScreen(
    viewModel: ProductoViewModel,
    producto: Producto? = null,
    onVolver: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var proveedor by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = if (producto == null) {
                "AGREGAR PRODUCTO"
            } else {
                "EDITAR PRODUCTO"
            }
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = {
                cantidad = it
            },
            label = {
                Text("Cantidad")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precio,
            onValueChange = {
                precio = it
            },
            label = {
                Text("Precio")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = proveedor,
            onValueChange = {
                proveedor = it
            },
            label = {
                Text("Proveedor")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                val cantidadNumero =
                    cantidad.toIntOrNull() ?: 0

                val precioNumero =
                    precio.toDoubleOrNull() ?: 0.0

                if (producto == null) {

                    val nuevoProducto = Producto(
                        nombre = nombre,
                        descripcion = descripcion,
                        cantidad = cantidadNumero,
                        precio = precioNumero,
                        proveedor = proveedor
                    )

                    viewModel.agregarProducto(nuevoProducto)

                } else {

                    val productoActualizado = producto.copy(
                        nombre = nombre,
                        descripcion = descripcion,
                        cantidad = cantidadNumero,
                        precio = precioNumero,
                        proveedor = proveedor
                    )

                    viewModel.actualizarProducto(productoActualizado)
                }

                onVolver()
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = if (producto == null) {
                    "Guardar producto"
                } else {
                    "Guardar cambios"
                }
            )
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Volver")
        }
    }
}