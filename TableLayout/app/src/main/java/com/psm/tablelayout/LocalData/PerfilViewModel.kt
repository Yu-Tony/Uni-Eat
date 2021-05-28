package com.psm.tablelayout.LocalData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PerfilViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<PerfilLocal>>
    private val repository: PerfilRepository

    init
    {
        val perfilDao = PerfilDB.getInstance(application).perfilDAO()
        repository = PerfilRepository(perfilDao)
        readAllData = repository.readAllData
    }

    fun insert(perfilLocal: PerfilLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert((perfilLocal))
        }
    }
}