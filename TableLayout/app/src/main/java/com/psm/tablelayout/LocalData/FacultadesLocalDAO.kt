package com.psm.tablelayout.LocalData

import androidx.room.*

@Dao
interface FacultadesLocalDAO
{
    @Query("SELECT * FROM FacultadesLocal")
    suspend fun getAll(): List<FacultadesLocal>

    @Query("SELECT * FROM FacultadesLocal WHERE facultadesID = :id")
    suspend fun getByID(id:Int):FacultadesLocal

    @Update
    suspend fun update(person: FacultadesLocal)

    @Insert
    suspend fun insert(people: List<FacultadesLocal>)

    @Delete
    suspend fun delete(person: FacultadesLocal)
}