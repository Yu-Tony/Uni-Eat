package com.psm.tablelayout.LocalData.Facultades

import androidx.lifecycle.LiveData


class FacultadesRepository(private val facultadesDAO: FacultadesDAO) {

    val readAllData: LiveData<List<FacultadesLocal>> = facultadesDAO.getAll()

    suspend fun insert(facultadesLocal: FacultadesLocal)
    {
        facultadesDAO.insert(facultadesLocal)
    }

    suspend fun delete(facultadesLocal: FacultadesLocal)
    {
        facultadesDAO.delete(facultadesLocal)
    }

    suspend fun deleteAllUsers()
    {
        facultadesDAO.deleteAllUsers()
    }

    suspend fun getByID(facultadesLocal: String)
    {
        facultadesLocal?.let { facultadesDAO.getByID(it) }
    }
}