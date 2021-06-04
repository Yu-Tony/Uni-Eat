package com.psm.tablelayout.LocalData.Perfil

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal

@Dao
interface PerfilDAO
{
    @Query("SELECT * FROM Perfil_Table")
    fun getAll():LiveData<List<PerfilLocal>>

    @Query("SELECT * FROM Perfil_Table WHERE userMail = :id")
    suspend fun getByID(id:String): PerfilLocal

    @Update
    suspend fun update(person: PerfilLocal)

    @Insert
    suspend fun insert(people: PerfilLocal)

    @Delete
    suspend fun delete(person: PerfilLocal)

    @Query("DELETE FROM Perfil_Table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Perfil_Table'")
    suspend fun deleteAllTableUsers()


}