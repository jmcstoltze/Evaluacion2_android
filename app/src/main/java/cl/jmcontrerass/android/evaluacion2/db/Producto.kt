package cl.jmcontrerass.android.evaluacion2.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// clase que representa a la entidad Producto en la base de datos
@Entity
data class Producto(
    // clave primaria autoincremental
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    var nombre: String, // atributo nombre
    var comprado: Boolean // atributo comprado, que representa su estado
)