package com.psm.tablelayout.LocalData.Drafts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psm.tablelayout.LocalData.Perfil.PerfilDB
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilRepository
import com.psm.tablelayout.LocalData.Resenas.ResenasLocal
import com.psm.tablelayout.LocalData.Resenas.ResenasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DraftViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<DraftLocal>>
    private val repository: DraftRepository

    init
    {
        val perfilDao = PerfilDB.getInstance(application).draftDAO()
        repository =
            DraftRepository(perfilDao)
        readAllData = repository.readAllData
    }

    fun insert(perfilLocal: DraftLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(perfilLocal)
        }
    }

    fun delete(perfilLocal: DraftLocal)
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