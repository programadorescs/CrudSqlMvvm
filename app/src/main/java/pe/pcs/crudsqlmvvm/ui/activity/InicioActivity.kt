package pe.pcs.crudsqlmvvm.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.pcs.crudsqlmvvm.CrudSqlMvvmApp
import pe.pcs.crudsqlmvvm.R
import pe.pcs.crudsqlmvvm.core.PreferencesKey
import pe.pcs.crudsqlmvvm.core.PreferencesProvider
import pe.pcs.crudsqlmvvm.core.UtilsMessage
import pe.pcs.crudsqlmvvm.core.UtilsSecurity
import pe.pcs.crudsqlmvvm.databinding.ActivityInicioBinding
import pe.pcs.crudsqlmvvm.ui.dialog.DialogConfigServer

class InicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root) //R.layout.activity_inicio

        binding.btInicio.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btConfifurarServer.setOnClickListener {

            DialogConfigServer().newInstance("Configurar servidor").show(supportFragmentManager, "IPServer")

            /*try {
                PreferencesProvider.setConfigurarServer(
                    CrudSqlMvvmApp.getAppContext(),
                    UtilsSecurity.cifrarDato(
                        "jdbc:jtds:sqlserver://192.168.18.4:1433;databaseName=dbPuntoVenta;user=phenom_x7;password=PT9XMn500Y7JX;"
                    )
                )

                UtilsMessage.showAlertOk(
                    "FELICIDADES", "Datos encriptados correctamente", this
                )
            } catch (e: Exception) {
                UtilsMessage.showAlertOk(
                    "ERROR", e.message, this
                )
            }*/
        }
    }
}