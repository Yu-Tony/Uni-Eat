package com.psm.tablelayout.LocalData.Best

import androidx.lifecycle.LiveData

class BestRepository(private val bestDAO: BestDAO) {

val readAllData: LiveData<List<BestLocal>> = bestDAO.getAll()

    suspend fun insert(perfilLocal: BestLocal)
    {
        bestDAO.insert(perfilLocal)
    }

    suspend fun delete(perfilLocal: BestLocal)
    {
        bestDAO.delete(perfilLocal)
    }

    suspend fun deleteAllUsers()
    {
        bestDAO.deleteAllUsers()
    }

    suspend fun deleteAllTableUsers()
    {
        bestDAO.deleteAllTableUsers()
    }


    suspend fun getByID(perfilLocal: String)
    {
        perfilLocal?.let { bestDAO.getByID(it) }
    }
}