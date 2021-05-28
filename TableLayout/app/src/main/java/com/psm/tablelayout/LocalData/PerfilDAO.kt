package com.psm.tablelayout.LocalData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psm.tablelayout.CardsLong.Perfil

@Dao
interface PerfilDAO
{
    @Query("SELECT * FROM Perfil_Table")
    fun getAll():LiveData<List<PerfilLocal>>

    @Query("SELECT * FROM Perfil_Table WHERE userID = :id")
    suspend fun getByID(id:Int):PerfilLocal

    @Update
    suspend fun update(person: PerfilLocal)

    @Insert
    suspend fun insert(people: PerfilLocal)

    @Delete
    suspend fun delete(person: PerfilLocal)
}