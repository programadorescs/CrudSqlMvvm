package pe.pcs.crudsqlmvvm.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.pcs.crudsqlmvvm.R
import pe.pcs.crudsqlmvvm.core.UtilsCommon
import pe.pcs.crudsqlmvvm.core.UtilsMessage
import pe.pcs.crudsqlmvvm.data.model.ProductoModel
import pe.pcs.crudsqlmvvm.databinding.FragmentProductoBinding
import pe.pcs.crudsqlmvvm.ui.adapter.ProductoAdapter
import pe.pcs.crudsqlmvvm.ui.viewmodel.ProductoViewModel


class ProductoFragment : Fragment(), ProductoAdapter.IOnClickListener {

    private lateinit var binding: FragmentProductoBinding
    private val viewModel: ProductoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())

        binding.rvLista.adapter = ProductoAdapter(this)

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

        viewModel.lista.observe(viewLifecycleOwner) {
            (binding.rvLista.adapter as ProductoAdapter).submitList(it)
        }

        viewModel.operacionExitosa.observe(viewLifecycleOwner) {
            if(it) {
                UtilsMessage.showToast(
                    "¡FELICIDADES! La operacion se realizo con éxito"
                )
                viewModel.limpiarOperacionExitosa()
            }
        }

        binding.tilBuscar.setEndIconOnClickListener {
            UtilsCommon.ocultarTeclado(it)
            binding.etBuscar.setText("")
        }

        binding.etBuscar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.listar(p0.toString().trim())
            }

        })

        binding.fabNuevo.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            viewModel.setItem(null)
            Navigation.findNavController(it).navigate(R.id.action_nva_producto_to_operacionProductoFragment)
        }
    }

    override fun clickEditar(entidad: ProductoModel) {
        UtilsCommon.ocultarTeclado(requireView())

        viewModel.setItem(entidad)
        Navigation.findNavController(requireView()).navigate(R.id.action_nva_producto_to_operacionProductoFragment)
    }

    override fun clickEliminar(entidad: ProductoModel) {
        UtilsCommon.ocultarTeclado(requireView())

        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("ELIMINAR")
            setMessage("¿Esta seguro de eliminar el registro: ${entidad.descripcion}")
            setCancelable(false)

            setPositiveButton("SI") { dialog, _ ->
                viewModel.eliminar(entidad)
                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}