package pe.pcs.crudsqlmvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.crudsqlmvvm.data.dao.ProductoDao
import pe.pcs.crudsqlmvvm.data.model.ProductoModel

class ProductoViewModel: ViewModel() {

    private val _lista = MutableLiveData<List<ProductoModel>>()
    val lista: LiveData<List<ProductoModel>> = _lista

    private val _item = MutableLiveData<ProductoModel?>()
    val item: LiveData<ProductoModel?> = _item

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> = _progressBar

    private val _msgError = MutableLiveData<String>()
    val msgError: LiveData<String> = _msgError

    private val _operacionExitosa = MutableLiveData<Boolean>()
    val operacionExitosa: LiveData<Boolean> = _operacionExitosa

    fun limpiarMsgError() {
        _msgError.postValue("")
    }

    fun limpiarOperacionExitosa() {
        _operacionExitosa.postValue(false)
    }

    fun setItem(entidad: ProductoModel?) {
        _item.postValue(entidad)
    }

    init {
        listar("")
    }

    fun listar(dato: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _lista.postValue(ProductoDao.listar(dato))
                } catch (e: Exception) {
                    _msgError.postValue(e.message)
                } finally {
                    _progressBar.postValue(false)
                }
            }
        }
    }

    fun grabar(entidad: ProductoModel) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val rpta = if(entidad.id == 0)
                        ProductoDao.insertar(entidad)
                    else
                        ProductoDao.actualizar(entidad)

                    _lista.postValue(ProductoDao.listar(""))
                    rpta
                } catch (e: Exception) {
                    _msgError.postValue(e.message)
                    0
                } finally {
                    _progressBar.postValue(false)
                }
            }

            _operacionExitosa.postValue(result > 0)
        }
    }

    fun eliminar(entidad: ProductoModel) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val rpta = ProductoDao.eliminar(entidad)

                    _lista.postValue(ProductoDao.listar(""))
                    rpta
                } catch (e: Exception) {
                    _msgError.postValue(e.message)
                    0
                } finally {
                    _progressBar.postValue(false)
                }
            }

            _operacionExitosa.postValue(result > 0)
        }
    }
}