package com.example.stockelectronico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stockelectronico.data.AppDatabase
import com.example.stockelectronico.data.ProductoRepository
import com.example.stockelectronico.ui.AgregarProductoScreen
import com.example.stockelectronico.ui.ProductosScreen
import com.example.stockelectronico.ui.theme.StockElectronicoTheme
import com.example.stockelectronico.viewmodel.ProductoViewModel
import com.example.stockelectronico.viewmodel.ProductoViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        val repository = ProductoRepository(
            database.productoDao()
        )

        setContent {

            StockElectronicoTheme {

                val viewModel: ProductoViewModel = viewModel(
                    factory = ProductoViewModelFactory(repository)
                )

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "productos"
                ) {

                    // INVENTARIO
                    composable("productos") {

                        ProductosScreen(
                            viewModel = viewModel,

                            onAgregar = {

                                viewModel.limpiarProductoSeleccionado()

                                navController.navigate("agregar")
                            },

                            onEditar = { producto ->

                                viewModel.seleccionarProducto(producto)

                                navController.navigate("editar")
                            }
                        )
                    }

                    // AGREGAR PRODUCTO
                    composable("agregar") {

                        AgregarProductoScreen(
                            viewModel = viewModel,

                            producto = null,

                            onVolver = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // EDITAR PRODUCTO
                    composable("editar") {

                        val producto by viewModel.productoSeleccionado
                            .collectAsState(initial = null)

                        AgregarProductoScreen(
                            viewModel = viewModel,

                            producto = producto,

                            onVolver = {

                                viewModel.limpiarProductoSeleccionado()

                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}