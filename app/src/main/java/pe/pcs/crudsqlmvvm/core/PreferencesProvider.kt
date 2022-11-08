package pe.pcs.crudsqlmvvm.core

import android.content.Context
import pe.pcs.crudsqlmvvm.CrudSqlMvvmApp

object PreferencesProvider {

    fun setUsuarioLogin(contexto: Context, nombreUsuario: String) {
        contexto.getSharedPreferences(ConstantApp.PREFS_APP, Context.MODE_PRIVATE).edit().apply {
            putString(PreferencesKey.USER_LOGIN.value, nombreUsuario.trim())
            apply()
        }
    }

    fun setConfigurarServer(contexto: Context, cadenaConexion: String) {
        contexto.getSharedPreferences(ConstantApp.PREFS_APP, Context.MODE_PRIVATE).edit().apply {
            putString(PreferencesKey.CONFIGURAR_SERVER.value, cadenaConexion.trim())
            apply()
        }
    }

    fun setConfigurarServer(cadenaConexion: String) {
        CrudSqlMvvmApp.getAppContext().getSharedPreferences(ConstantApp.PREFS_APP, Context.MODE_PRIVATE).edit().apply {
            putString(PreferencesKey.CONFIGURAR_SERVER.value, cadenaConexion.trim())
            apply()
        }
    }

    fun getUnicaPreferencia(context: Context, key: PreferencesKey): String {
        val dato = context.getSharedPreferences(ConstantApp.PREFS_APP, Context.MODE_PRIVATE)
        return dato.getString(key.value, "") ?: ""
    }

    fun getUnicaPreferencia(key: PreferencesKey): String {
        val dato = CrudSqlMvvmApp.getAppContext().getSharedPreferences(ConstantApp.PREFS_APP, Context.MODE_PRIVATE)
        return dato.getString(key.value, "") ?: ""
    }

}