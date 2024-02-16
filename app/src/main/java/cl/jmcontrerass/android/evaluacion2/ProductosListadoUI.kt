package cl.jmcontrerass.android.evaluacion2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.jmcontrerass.android.evaluacion2.db.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable

// representa el listado de productos al arrancar la app
fun ProductosListadoUI (
    productos:List<Producto>,
    onAdd:() -> Unit = {}
) {
    // columna principal que contiene la estructura de la interfaz de usuario
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // título de la pantalla principal
        Text(
            text = stringResource(id = R.string.titulo), // multilenguaje
            modifier = Modifier,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(20.dp)) // espaciado

        // línea divisoria entre ítems
        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(20.dp))

        // estructura de diseño usando el componente scaffold
        Scaffold(
            // configura el botón flotante de acción para agregar producto
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .padding(end = 40.dp)
                        .width(130.dp),
                    onClick = { onAdd() },
                    icon = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "agregar producto"
                        )
                    },
                    // llamado a recurso multilenguaje
                    text = { Text(stringResource(id = R.string.agregar)) }
                )
            }
        ) { contentPadding ->
            // verifica si la lista no está vacía
            if (productos.isNotEmpty()) {
                // contenedor de elementos visibles
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // iteración sobre la lista de productos mostrarla en pantalla
                    items(productos) { producto ->

                        // llama a función para trabajar con cada ítem de manera individual
                        ProductoItemUI(producto)
                    }
                }
            } else {
                // si no hay productos en la lista muestra un mensaje multilenguaje
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(id = R.string.mensaje1))
                }
            }
        }
    }
}