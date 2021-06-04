package com.psm.tablelayout.LocalData.Resenas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psm.tablelayout.LocalData.Perfil.PerfilDB
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ResenasViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<ResenasLocal>>
    private val repository: ResenasRepository

    init
    {
        val perfilDao = PerfilDB.getInstance(application).resenasDAO()
        repository =
            ResenasRepository(perfilDao)
        readAllData = repository.readAllData
    }

    fun insert(perfilLocal: ResenasLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(perfilLocal)
        }
    }

    fun delete(perfilLocal: ResenasLocal)
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

    fun deleteAllTableUsers()
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