package com.psm.tablelayout.LocalData.Facultades

import androidx.lifecycle.LiveData


class FacultadesRepository(val facultadesDAO: FacultadesDAO) {

    val readAllData: LiveData<List<FacultadesLocal>> = facultadesDAO.getAll()


    suspend fun selectALL(): List<FacultadesLocal> {
        val allFacu = facultadesDAO.selectALL()
        return allFacu
    }

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


    suspend fun deleteAllTableFacu()
    {
        facultadesDAO.deleteAllTableFacu()
    }

    suspend fun getByID(facultadesLocal: String)
    {
        facultadesLocal?.let { facultadesDAO.getByID(it) }
    }
}