package pe.pcs.crudsqlmvvm.data.dao

import android.os.StrictMode
import pe.pcs.crudsqlmvvm.core.PreferencesKey
import pe.pcs.crudsqlmvvm.core.PreferencesProvider
import pe.pcs.crudsqlmvvm.core.UtilsSecurity
import java.sql.Connection
import java.sql.DriverManager

object Conexion {

    private var cnn: Connection? = null

    init {
        try {
            // StrictMode es informar de las violaciones de políticas relacionadas con los hilos y
            // la máquina virtual. Si se detecta dicha violación, obtenemos una alerta que nos lo indica
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

            // crea una instancia de nuestro driver JTDS
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()

            // Lee y asigna la cadena de conexion
            cnn = DriverManager.getConnection(
                UtilsSecurity.descifrarDato(
                    PreferencesProvider.getUnicaPreferencia(
                        PreferencesKey.CONFIGURAR_SERVER
                    )
                )
            )
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun getConexion(): Connection {
        return try {
            cnn!!
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

}