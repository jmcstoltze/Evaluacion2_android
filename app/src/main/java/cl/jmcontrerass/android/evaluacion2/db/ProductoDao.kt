package cl.jmcontrerass.android.evaluacion2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// interfaz de objeto de acceso de datos para la entidad Producto de la base de datos
@Dao
interface ProductoDao {

    // consulta para obtener el conteo total de productos de la DB
    @Query("SELECT COUNT(*) FROM producto")
    fun count():Int

    // conteo para obtener los productos ordenados por estado de compra y nombre
    @Query("SELECT * FROM producto ORDER BY comprado ASC, nombre ASC")
    fun getAll():List<Producto>

    // consulta para obtener producto por id
    @Query("SELECT * FROM producto WHERE id = :id")
    fun findById(id:Int):Producto

    // consulta para obtener producto por nombre
    @Query("SELECT * FROM producto WHERE nombre = :nombre")
    fun getByName(nombre:String):Producto

    // Operación de inserción de producto
    @Insert
    fun insert(producto:Producto):Long

    // operación de inserción de varios productos
    @Insert
    fun insertAll(vararg productos:Producto)

    // operación de actualización de productos
    @Update
    fun update(vararg productos:Producto)

    // operación de eliminación de uno o más productos
    @Delete
    fun delete(producto:Producto)
}