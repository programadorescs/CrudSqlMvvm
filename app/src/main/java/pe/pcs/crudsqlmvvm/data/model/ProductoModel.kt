package pe.pcs.crudsqlmvvm.data.model

data class ProductoModel(
    var id: Int = 0,
    var codigoBarra: String = "",
    var descripcion: String = "",
    var costo: Double = 0.0,
    var precio: Double = 0.0,
    var stock: Int = 0
)
