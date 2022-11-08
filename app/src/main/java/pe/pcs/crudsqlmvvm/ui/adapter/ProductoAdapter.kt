package pe.pcs.crudsqlmvvm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.crudsqlmvvm.core.UtilsCommon
import pe.pcs.crudsqlmvvm.data.model.ProductoModel
import pe.pcs.crudsqlmvvm.databinding.ItemsProductoBinding

class ProductoAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<ProductoModel,ProductoAdapter.BindViewHolder>(diffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: ProductoModel)
        fun clickEliminar(entidad: ProductoModel)
    }

    private object diffCallback : DiffUtil.ItemCallback<ProductoModel>() {
        override fun areItemsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsProductoBinding): RecyclerView.ViewHolder(binding.root) {

        fun enlazar(entidad: ProductoModel) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvCodigo.text = entidad.codigoBarra
            binding.tvPrecio.text = UtilsCommon.formatearDoubleString(entidad.precio)

            binding.ibEditar.setOnClickListener { iOnClickListener.clickEditar(entidad) }
            binding.ibEliminar.setOnClickListener { iOnClickListener.clickEliminar(entidad) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsProductoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.enlazar(getItem(position))
    }
}