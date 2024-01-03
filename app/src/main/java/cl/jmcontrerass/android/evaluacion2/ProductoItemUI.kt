package cl.jmcontrerass.android.evaluacion2

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.jmcontrerass.android.evaluacion2.db.ListadoDB
import cl.jmcontrerass.android.evaluacion2.db.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Item producto perteneciente a la lista
@Composable
fun ProductoItemUI(
    producto: Producto,
    onClick:() -> Unit = {}
) {
    // obtiene el contexto actual y crea corrutina
    val contexto  = LocalContext.current
    val alcanceCorrutina = rememberCoroutineScope()

    // intent para lanzar la actividad principal nuevamente
    val intent: Intent = Intent(contexto, MainActivity::class.java)

    // contenedor horizontal o fila con los elementos de cada producto
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        // Si el producto está marcado como comprado
        if(producto.comprado) {
            // muestra imagen del drawable comprado
            Image(
                painter = painterResource (
                    id = R.drawable.icono_comprado
                ),
                contentDescription = "Icono comprado",
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    // determina la acción al hacer click en la imagen
                    .clickable {
                        alcanceCorrutina.launch( Dispatchers.IO ) {
                            // actualiza el estado del producto
                            val dao = ListadoDB.getInstance(contexto).productoDao()
                            producto.comprado = false
                            dao.update(producto)
                            onClick()
                            // reinicia la app para que se muestren los cambios
                            contexto.startActivity(intent)
                        }
                    }
            )
        } else {
            // si el producto no está comprado muestra otro drawable
            Image(
                painter = painterResource (
                    id = R.drawable.icono_carrito
                ),
                contentDescription ="Icono por comprar",
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    // acción al hacer click en la imagen
                    .clickable {
                        alcanceCorrutina.launch( Dispatchers.IO ) {
                            // actualiza el estado del producto
                            val dao = ListadoDB.getInstance(contexto).productoDao()
                            producto.comprado = true
                            dao.update(producto)
                            onClick()
                            // reinicia para mostrar los cambios
                            contexto.startActivity(intent)
                        }
                    }
            )
        }
        // espacio entre imagen y texto
        Spacer(modifier = Modifier.width(20.dp))
        // muestra el nombre del producto
        Text(
            text = producto.nombre,
            modifier = Modifier.weight(2f), // doble del espacio disponible
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // Icono de eliminación con acción al click
        Icon(
            Icons.Filled.Delete,
            contentDescription = "Eliminar producto",
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .clickable {
                    alcanceCorrutina.launch( Dispatchers.IO ) {
                        // elimina el producto de la base de datos
                        val dao = ListadoDB.getInstance(contexto).productoDao()
                        dao.delete(producto)
                        onClick()
                        // reinicia para mostrar los cambios de la lista
                        contexto.startActivity(intent)
                    }
                }
        )
    }
    // espaciados y lineas divisorias entre los elementos de la lista
    Spacer(modifier = Modifier.height(20.dp))
    Divider (
        color = Color.Gray,
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(20.dp))
}