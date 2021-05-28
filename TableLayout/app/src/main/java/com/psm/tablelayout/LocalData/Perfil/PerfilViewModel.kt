package com.psm.tablelayout.LocalData.Perfil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psm.tablelayout.LocalData.Perfil.PerfilDB
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PerfilViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<PerfilLocal>>
    private val repository: PerfilRepository

    init
    {
        val perfilDao = PerfilDB.getInstance(application).perfilDAO()
        repository =
            PerfilRepository(perfilDao)
        readAllData = repository.readAllData
    }

    fun insert(perfilLocal: PerfilLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(perfilLocal)
        }
    }

    fun delete(perfilLocal: PerfilLocal)
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

    fun getByID(perfilLocal: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
        repository.getByID(perfilLocal)
    }

    }
}