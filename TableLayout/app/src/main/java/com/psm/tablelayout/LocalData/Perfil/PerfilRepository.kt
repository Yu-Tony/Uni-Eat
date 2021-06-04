package com.psm.tablelayout.LocalData.Perfil

import androidx.lifecycle.LiveData

class PerfilRepository(private val perfilDAO: PerfilDAO) {

val readAllData: LiveData<List<PerfilLocal>> = perfilDAO.getAll()

    suspend fun insert(perfilLocal: PerfilLocal)
    {
        perfilDAO.insert(perfilLocal)
    }

    suspend fun delete(perfilLocal: PerfilLocal)
    {
        perfilDAO.delete(perfilLocal)
    }

    suspend fun deleteAllUsers()
    {
        perfilDAO.deleteAllUsers()
    }

    suspend fun deleteAllTableUsers()
    {
        perfilDAO.deleteAllTableUsers()
    }


    suspend fun getByID(perfilLocal: String)
    {
        perfilLocal?.let { perfilDAO.getByID(it) }
    }
}