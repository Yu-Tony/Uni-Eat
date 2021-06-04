package com.psm.tablelayout.LocalData.Categorias

import androidx.lifecycle.LiveData

class CategoriasRepository(private val categDAO: CategoriasDAO) {

val readAllData: LiveData<List<CategoriasLocal>> = categDAO.getAll()

    suspend fun insert(perfilLocal: CategoriasLocal)
    {
        categDAO.insert(perfilLocal)
    }

    suspend fun delete(perfilLocal: CategoriasLocal)
    {
        categDAO.delete(perfilLocal)
    }

    suspend fun deleteAllUsers()
    {
        categDAO.deleteAllUsers()
    }

    suspend fun deleteAllTableUsers()
    {
        categDAO.deleteAllTableUsers()
    }


    suspend fun getByID(perfilLocal: String)
    {
        perfilLocal?.let { categDAO.getByID(it) }
    }
}