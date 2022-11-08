package pe.pcs.crudsqlmvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.fabNuevo.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)
        }
    }

    override fun clickEditar(entidad: ProductoModel) {
        UtilsCommon.ocultarTeclado(requireView())
    }

    override fun clickEliminar(entidad: ProductoModel) {
        TODO("Not yet implemented")
    }
}