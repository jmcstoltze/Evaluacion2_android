package cl.jmcontrerass.android.evaluacion2

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import cl.jmcontrerass.android.evaluacion2.db.ListadoDB
import cl.jmcontrerass.android.evaluacion2.db.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AppListadoUI() {
    // obtiene el contexto local de compose
    val contexto = LocalContext.current

    // establece y recuerda el estado de los productos y la acción
    val (productos, setProductos) = remember{ mutableStateOf(emptyList<Producto>()) }
    val (accion, setAccion) = remember{ mutableStateOf(Accion.LISTAR) }

    // efecto lanzado cuando cambia el estado de productos
    LaunchedEffect(productos) {
        withContext(Dispatchers.IO) {

            // accede a la base de datos para obtener todos los productos
            val db = ListadoDB.getInstance(contexto)
            setProductos(db.productoDao().getAll())
            Log.v("AppListadoUI", "LaunchedEffect")
        }
    }

    // manejo de acciones según el estado actual
    val onSave = {
        setAccion(Accion.LISTAR)
    }

    // determina la acción actual y ejecuta la acción correspondiente
    when(accion) {

        // invoca a la función producto formulario
        Accion.CREAR -> ProductoFormUI(null, onSave)

        // invoca a la función listado de productos
        else -> ProductosListadoUI (
            productos,
            onAdd = { setAccion(Accion.CREAR) },
        )
    }
}