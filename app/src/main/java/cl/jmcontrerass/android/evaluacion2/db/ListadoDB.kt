package cl.jmcontrerass.android.evaluacion2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// representa las entidades de la base de datos
@Database(entities = [Producto::class], version = 1)

// base de datos Room
abstract class ListadoDB : RoomDatabase() {

    // método que permite acceder al objeto de acceso de datos DAO
    abstract fun productoDao():ProductoDao

    // permite obtener la instancia única de la base de datos
    companion object {
        @Volatile
        private var BASE_DATOS : ListadoDB? = null

        // función para obtener una intancia de la base de datos
        fun getInstance(contexto: Context):ListadoDB {
            return BASE_DATOS ?: synchronized(this) {

                // si la instancia es nula crea una nueva
                Room.databaseBuilder (
                    contexto.applicationContext,
                    ListadoDB::class.java,
                    "Listado.db"
                )
                    // permite la eventual eliminación o recreación de la base de datos
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {BASE_DATOS = it}
            }
        }
    }
}