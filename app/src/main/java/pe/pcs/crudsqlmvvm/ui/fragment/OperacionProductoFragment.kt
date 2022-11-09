package pe.pcs.crudsqlmvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import pe.pcs.crudsqlmvvm.R
import pe.pcs.crudsqlmvvm.core.UtilsCommon
import pe.pcs.crudsqlmvvm.core.UtilsMessage
import pe.pcs.crudsqlmvvm.data.model.ProductoModel
import pe.pcs.crudsqlmvvm.databinding.FragmentOperacionProductoBinding
import pe.pcs.crudsqlmvvm.ui.viewmodel.ProductoViewModel


class OperacionProductoFragment : Fragment() {

    private lateinit var binding: FragmentOperacionProductoBinding
    private val viewModel: ProductoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperacionProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.item.value != null) {
            binding.etCodigoBarra.setText(viewModel.item.value!!.codigoBarra)
            binding.etDescripcion.setText(viewModel.item.value!!.descripcion)
            binding.etCosto.setText(UtilsCommon.formatearDoubleString(viewModel.item.value!!.costo))
            binding.etPrecio.setText(UtilsCommon.formatearDoubleString(viewModel.item.value!!.precio))
            binding.etStock.setText(viewModel.item.value!!.stock.toString())
        }

        viewModel.msgError.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) {
                UtilsMessage.showAlertOk(
                    "ERROR", it, requireContext()
                )

                viewModel.limpiarMsgError()
            }
        }

        viewModel.progressBar.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.operacionExitosa.observe(viewLifecycleOwner) {
            if(it) {
                UtilsMessage.showToast(
                    "¡FELICIDADES! La operacion se realizo con éxito"
                )
                viewModel.limpiarOperacionExitosa()

                UtilsCommon.limpiarEditText(requireView())
                binding.etCodigoBarra.requestFocus()
            }
        }

        binding.fabGrabar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if(binding.etCodigoBarra.text.toString().trim().isEmpty() ||
                binding.etDescripcion.text.toString().trim().isEmpty() ||
                binding.etCosto.text.toString().trim().isEmpty() ||
                binding.etPrecio.text.toString().trim().isEmpty() ||
                binding.etStock.text.toString().trim().isEmpty()) {
                UtilsMessage.showToast("Todos los campos son requeridos")
                return@setOnClickListener
            }

            viewModel.grabar(
                ProductoModel(
                    viewModel.item.value?.id ?: 0,
                    binding.etCodigoBarra.text.toString().trim(),
                    binding.etDescripcion.text.toString().trim(),
                    binding.etCosto.text.toString().trim().toDouble(),
                    binding.etPrecio.text.toString().trim().toDouble(),
                    binding.etStock.text.toString().trim().toInt()
                )
            )
        }
    }
}