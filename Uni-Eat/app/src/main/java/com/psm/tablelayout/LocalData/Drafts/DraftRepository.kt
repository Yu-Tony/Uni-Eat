package com.psm.tablelayout.LocalData.Drafts

import androidx.lifecycle.LiveData

class DraftRepository(private val draftDAO: DraftDAO) {

val readAllData: LiveData<List<DraftLocal>> = draftDAO.getAll()

    suspend fun insert(perfilLocal: DraftLocal)
    {
        draftDAO.insert(perfilLocal)
    }

    suspend fun delete(perfilLocal: DraftLocal)
    {
        draftDAO.delete(perfilLocal)
    }

    suspend fun deleteAllUsers()
    {
        draftDAO.deleteAllUsers()
    }

    suspend fun deleteAllTableUsers()
    {
        draftDAO.deleteAllTableUsers()
    }


    suspend fun getByID(perfilLocal: String)
    {
        perfilLocal?.let { draftDAO.getByID(it) }
    }
}