package cl.jmcontrerass.android.evaluacion2

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.jmcontrerass.android.evaluacion2.db.ListadoDB
import cl.jmcontrerass.android.evaluacion2.db.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// formulario para ingresar el producto a la lista
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormUI(
    p:Producto?,
    onSave:() -> Unit = {}
) {
    // obtiene el contexto actual de la aplicación
    val contexto = LocalContext.current as ComponentActivity
    // estado para el nombre del producto
    val (nombre, setNombre) = remember { mutableStateOf(p?.nombre ?: "")}
    // corrutina para tareas asincrónicas
    val coroutineScope = rememberCoroutineScope()
    // para manejar el estado del snackabar
    val snackbarHostState = remember { SnackbarHostState() }
    // intent para volver a la actividad principal
    val intent: Intent = Intent(contexto, MainActivity::class.java)

    // estructura utilizando scaffold
    Scaffold(
        // snackbar que mostrará diversos mensajes
        snackbarHost = { SnackbarHost (snackbarHostState)},

        // botón flotante para reiniciar la app y volver a la lista
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .width(130.dp),
                onClick = { contexto.startActivity(intent) },
                icon = {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Volver al listado"
                    )
                },
                // texto multilenguaje
                text = { Text(stringResource(id = R.string.volver)) }

            )
        }
    ) { paddingValues ->
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // imagen genérica del producto
            Image (
                painter = painterResource (
                    id = R.drawable.icono_carrito),
                contentDescription = "Imagen de producto",
                modifier = Modifier
                    .size(width = 250.dp, height = 250.dp
                )
            )
            // campo para ingresar el nombre del producto
            TextField(
                value = nombre,
                onValueChange = { setNombre(

                    // capitaliza el texto ingresado con la primera letra mayúscula
                    it.lowercase().replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase()
                        else it.toString() }) },
                label = {Text(stringResource(id= R.string.mensaje0))} // multilenguaje
            )
            Spacer(modifier = Modifier.height(20.dp))
            // botón para crear el producto
            Button (
                onClick = {

                // considera la opción de que no se ingrese el nombre
                if (nombre.isNotBlank()) {
                    coroutineScope.launch(Dispatchers.IO) {
                        val dao = ListadoDB.getInstance(contexto).productoDao()
                        val comprado = false
                        val existe = dao.getByName(nombre)

                        // considera la opción de que el producto ingresado ya exista
                        if (existe == null) {
                            val producto = Producto(p?.id ?: 0, nombre, comprado)
                            // si no existe lo agrega a la base de datos
                            dao.insert(producto)

                            // mensaje multilenguaje en el snackbar
                            val msj: String = contexto.getString(R.string.mensaje2)
                            snackbarHostState.showSnackbar(msj)

                            // reinicia la activida principal
                            contexto.startActivity(intent)

                        } else {

                            // mensaje en caso de que el producto exista
                            val msjExiste: String = contexto.getString(R.string.existe)
                            snackbarHostState.showSnackbar(msjExiste)
                        }
                    }
                } else {
                    coroutineScope.launch(Dispatchers.IO) {

                        // mensaje en caso de que no se ingrese el nombre del producto
                        val msjError: String = contexto.getString(R.string.error)
                        snackbarHostState.showSnackbar(msjError)
                    }
                }
            }) {
                Text(

                    // botón multilenguaje para crear el producto
                    text = stringResource(id = R.string.crear),
                    fontSize = 16.sp
                )
            }
        }
    }
}