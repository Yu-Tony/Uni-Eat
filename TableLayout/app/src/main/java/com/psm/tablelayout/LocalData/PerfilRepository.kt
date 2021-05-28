package com.psm.tablelayout.LocalData

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room

class PerfilRepository(private val perfilDAO: PerfilDAO) {

val readAllData: LiveData<List<PerfilLocal>> = perfilDAO.getAll()

    suspend fun insert(perfilLocal: PerfilLocal)
    {
        perfilDAO.insert(perfilLocal)
    }
}