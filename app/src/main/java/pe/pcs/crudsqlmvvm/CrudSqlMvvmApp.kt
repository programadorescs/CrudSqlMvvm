package pe.pcs.crudsqlmvvm

import android.app.Application
import android.content.Context

class CrudSqlMvvmApp: Application() {

    companion object {
        private var instancia: CrudSqlMvvmApp? = null

        fun getAppContext(): Context {
            return instancia!!.applicationContext
        }
    }

    init {
        instancia = this
    }
}