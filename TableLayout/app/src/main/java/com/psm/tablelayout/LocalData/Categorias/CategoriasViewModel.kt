package com.psm.tablelayout.LocalData.Categorias

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psm.tablelayout.LocalData.Perfil.PerfilDB
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CategoriasViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<CategoriasLocal>>
    private val repository: CategoriasRepository

    init
    {
        val perfilDao = PerfilDB.getInstance(application).categoriasDAO()
        repository =
            CategoriasRepository(perfilDao)
        readAllData = repository.readAllData
    }

    fun insert(perfilLocal: CategoriasLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(perfilLocal)
        }
    }

    fun delete(perfilLocal: CategoriasLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(perfilLocal)
        }
    }

    fun deleteAllUsers()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun deleteAllTableCateg()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTableUsers()
        }
    }

    fun getByID(perfilLocal: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
        repository.getByID(perfilLocal)
    }

    }
}