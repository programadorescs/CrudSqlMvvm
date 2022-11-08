package pe.pcs.crudsqlmvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pe.pcs.crudsqlmvvm.R
import pe.pcs.crudsqlmvvm.core.PreferencesKey
import pe.pcs.crudsqlmvvm.core.PreferencesProvider
import pe.pcs.crudsqlmvvm.core.UtilsMessage
import pe.pcs.crudsqlmvvm.core.UtilsSecurity
import pe.pcs.crudsqlmvvm.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabNuevo.setOnClickListener {
            try {
                UtilsMessage.showAlertOk(
                    "CADENA ENCRIPTADA",
                    PreferencesProvider.getUnicaPreferencia(
                        requireContext(),
                        PreferencesKey.CONFIGURAR_SERVER
                    ),
                    requireContext()
                )

                UtilsMessage.showAlertOk(
                    "CADENA CONEXION",
                    UtilsSecurity.descifrarDato(
                        PreferencesProvider.getUnicaPreferencia(
                            PreferencesKey.CONFIGURAR_SERVER
                        )
                    ),
                    requireContext()
                )
            } catch (e: Exception) {
                UtilsMessage.showAlertOk(
                    "ERROR", e.message, requireContext()
                )
            }
        }
    }
}