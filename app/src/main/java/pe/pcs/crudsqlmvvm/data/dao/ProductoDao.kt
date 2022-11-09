package pe.pcs.crudsqlmvvm.data.dao

import pe.pcs.crudsqlmvvm.data.model.ProductoModel

object ProductoDao {

    fun listar(dato: String): List<ProductoModel> {

        val lista = mutableListOf<ProductoModel>()

        try {
            val ps = Conexion.getConexion().prepareStatement(
                "SELECT id, codigobarra, descripcion, costo, precio, stock FROM Producto WHERE descripcion LIKE '%' + ? + '%';"
            )

            ps.setString(1, dato)

            val rs = ps.executeQuery()

            while (rs.next()) {
                lista.add(
                    ProductoModel().apply {
                        id = rs.getInt("id")
                        codigoBarra = rs.getString("codigobarra")
                        descripcion = rs.getString("descripcion")
                        costo = rs.getDouble("costo")
                        precio = rs.getDouble("precio")
                        stock = rs.getInt("stock")
                    }
                )
            }

            ps.close()

            return lista
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun obtenerRegistro(dato: String): ProductoModel {

        val entidad = ProductoModel()

        try {
            val ps = Conexion.getConexion().prepareStatement(
                "SELECT id, codigobarra, descripcion, costo, precio, stock FROM Producto WHERE codigobarra= ?;"
            )
            ps.setString(1, dato)

            val rs = ps.executeQuery()

            while (rs.next()) {
                entidad.id = rs.getInt("id")
                entidad.codigoBarra = rs.getString("codigobarra")
                entidad.descripcion = rs.getString("descripcion")
                entidad.costo = rs.getDouble("costo")
                entidad.precio = rs.getDouble("precio")
                entidad.stock = rs.getInt("stock")
            }

            ps.close()

            return entidad
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun insertar(entidad: ProductoModel): Int {
        try {
            val verificar = "SELECT isnull(Count(id), 0) FROM Producto WHERE codigoBarra=?;"
            val insertar ="INSERT INTO Producto (codigoBarra, descripcion, costo, precio, stock) VALUES (?, ?, ?, ?, ?);"

            var ps = Conexion.getConexion().prepareStatement(verificar)
            ps.setString(1, entidad.codigoBarra)

            val rs = ps.executeQuery()

            if(rs.next() && rs.getInt(1) > 0) {
                rs.close()
                throw Exception("El codigo de barras ya existe en la DB.")
            }

            ps = Conexion.getConexion().prepareStatement(insertar)
            ps.clearParameters()

            ps.setString(1, entidad.codigoBarra)
            ps.setString(2, entidad.descripcion)
            ps.setDouble(3, entidad.costo)
            ps.setDouble(4, entidad.precio)
            ps.setInt(5, entidad.stock)

            return if(ps.executeUpdate() > 0) 1 else 0
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun actualizar(entidad: ProductoModel): Int {
        try {
            val verificar = "SELECT isnull(Count(id), 0) FROM Producto WHERE id<>? AND codigoBarra=?;"
            val actualizar ="UPDATE Producto SET codigoBarra=?, descripcion=?, costo=?, precio=?, stock=? WHERE id= ?;"

            var ps = Conexion.getConexion().prepareStatement(verificar)
            ps.setInt(1, entidad.id)
            ps.setString(2, entidad.codigoBarra)

            val rs = ps.executeQuery()

            if(rs.next() && rs.getInt(1) > 0) {
                rs.close()
                throw Exception("El codigo de barras ya existe en la DB.")
            }

            ps = Conexion.getConexion().prepareStatement(actualizar)
            ps.clearParameters()
            ps.setString(1, entidad.codigoBarra)
            ps.setString(2, entidad.descripcion)
            ps.setDouble(3, entidad.costo)
            ps.setDouble(4, entidad.precio)
            ps.setInt(5, entidad.stock)
            ps.setInt(6, entidad.id)

            return if(ps.executeUpdate() > 0) 1 else 0
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun eliminar(entidad: ProductoModel): Int {
        try {
            val verificar = "SELECT isnull(Count(id), 0) FROM detalle_pedido WHERE idproducto=?;"
            val eliminar ="DELETE FROM Producto WHERE id= ?;"

            var ps = Conexion.getConexion().prepareStatement(verificar)
            ps.setInt(1, entidad.id)

            val rs = ps.executeQuery()

            if(rs.next() && rs.getInt(1) > 0) {
                rs.close()
                throw Exception("El registro se encuentra relacionado a en otra entidad, imposible eliminar.")
            }

            ps = Conexion.getConexion().prepareStatement(eliminar)
            ps.clearParameters()
            ps.setInt(1, entidad.id)

            return if(ps.executeUpdate() > 0) 1 else 0
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

}