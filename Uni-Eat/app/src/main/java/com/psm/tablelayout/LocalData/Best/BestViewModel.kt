package com.psm.tablelayout.LocalData.Best

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psm.tablelayout.LocalData.Perfil.PerfilDB
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BestViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<BestLocal>>
    private val repository: BestRepository

    init
    {
        val perfilDao = PerfilDB.getInstance(application).bestDAO()
        repository =
            BestRepository(perfilDao)
        readAllData = repository.readAllData
    }

    fun insert(perfilLocal: BestLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(perfilLocal)
        }
    }

    fun delete(perfilLocal: BestLocal)
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

    fun deleteAllTableBest()
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