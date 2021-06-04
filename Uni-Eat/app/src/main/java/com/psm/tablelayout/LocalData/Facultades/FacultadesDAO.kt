package com.psm.tablelayout.LocalData.Facultades

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FacultadesDAO
{
    @Query("SELECT * FROM Facultades_Table")
    fun getAll(): LiveData<List<FacultadesLocal>>

    @Query("SELECT * FROM Facultades_Table")
    suspend fun selectALL(): List<FacultadesLocal>

    @Query("SELECT * FROM Facultades_Table WHERE facultadesID = :id")
    suspend fun getByID(id:String): FacultadesLocal

    @Update
    suspend fun update(person: FacultadesLocal)

    @Insert
    suspend fun insert(people: FacultadesLocal)

    @Delete
    suspend fun delete(person: FacultadesLocal)

    @Query("DELETE FROM Facultades_Table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Facultades_Table'")
    suspend fun deleteAllTableFacu()

}