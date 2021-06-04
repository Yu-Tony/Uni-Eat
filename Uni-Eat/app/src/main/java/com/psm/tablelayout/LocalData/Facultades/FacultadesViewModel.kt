package com.psm.tablelayout.LocalData.Facultades

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.psm.tablelayout.LocalData.Perfil.PerfilDB
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FacultadesViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<FacultadesLocal>>
    val repository: FacultadesRepository

    init
    {
        val faultadDAO = PerfilDB.getInstance(application).facultadesDAO()
        repository =
            FacultadesRepository(faultadDAO)
        readAllData = repository.readAllData
    }


    fun selectALL()
    {
        viewModelScope.launch(Dispatchers.IO) {
          repository.selectALL()

        }
    }

    fun insert(facultadesLocal: FacultadesLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(facultadesLocal)
        }
    }

    fun delete(facultadesLocal: FacultadesLocal)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(facultadesLocal)
        }
    }

    fun deleteAllUsers()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun deleteAllTableFacu()
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTableFacu()
        }
    }

    fun getByID(perfilLocal: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getByID(perfilLocal)
        }

    }
}