package com.psm.tablelayout.LocalData.Resenas

import androidx.lifecycle.LiveData

class ResenasRepository(private val resDAO: ResenasDAO) {

val readAllData: LiveData<List<ResenasLocal>> = resDAO.getAll()

    suspend fun insert(perfilLocal: ResenasLocal)
    {
        resDAO.insert(perfilLocal)
    }

    suspend fun delete(perfilLocal: ResenasLocal)
    {
        resDAO.delete(perfilLocal)
    }

    suspend fun deleteAllUsers()
    {
        resDAO.deleteAllUsers()
    }

    suspend fun deleteAllTableUsers()
    {
        resDAO.deleteAllTableUsers()
    }


    suspend fun getByID(perfilLocal: String)
    {
        perfilLocal?.let { resDAO.getByID(it) }
    }
}