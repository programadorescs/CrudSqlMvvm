package pe.pcs.crudsqlmvvm.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.pcs.crudsqlmvvm.CrudSqlMvvmApp
import pe.pcs.crudsqlmvvm.core.PreferencesProvider
import pe.pcs.crudsqlmvvm.core.UtilsMessage
import pe.pcs.crudsqlmvvm.core.UtilsSecurity
import pe.pcs.crudsqlmvvm.databinding.DialogConfigurarServerBinding

class DialogConfigServer: DialogFragment() {

    private var mTitle: String = ""

    fun newInstance(mTitle: String): DialogConfigServer {
        val fragment = DialogConfigServer()
        fragment.mTitle = mTitle
        fragment.isCancelable = false
        return fragment
    }

    /*override fun onStart() {
        super.onStart()

        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = DialogConfigurarServerBinding.inflate(LayoutInflater.from(requireContext()))

        return MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(mTitle)
            setCancelable(false)

            setPositiveButton("Grabar") { dialog, _ ->

                if(binding.etIpServer.text.toString().trim().isEmpty()) {
                    UtilsMessage.showAlertOk(
                        "ADVERTENCIA",
                        "Todos los campos son requeridos",
                        requireContext()
                    )
                    return@setPositiveButton
                }

                try {
                    PreferencesProvider.setConfigurarServer(
                        CrudSqlMvvmApp.getAppContext(),
                        UtilsSecurity.cifrarDato(
                            "jdbc:jtds:sqlserver://${binding.etIpServer.text.toString().trim()}:${binding.etPuerto.text.toString().trim()};databaseName=demodb;user=${binding.etUsuarioServer.text.toString().trim()};password=${binding.etClave.text.toString().trim()};"
                        )
                    )

                    UtilsMessage.showToast(
                        "Datos del servidor configurados correctamente"
                    )

                    dialog.dismiss()
                } catch (e: Exception) {
                    UtilsMessage.showAlertOk(
                        "ERROR", e.message, requireContext()
                    )
                }
            }

            setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
        }.setView(binding.root).create()
    }
}