package com.psm.tablelayout.LocalData.Resenas

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal

@Dao
interface ResenasDAO
{
    @Query("SELECT * FROM Review_Table")
    fun getAll():LiveData<List<ResenasLocal>>

    @Query("SELECT * FROM Review_Table WHERE resenaID = :id")
    suspend fun getByID(id:String): ResenasLocal

    @Update
    suspend fun update(person: ResenasLocal)

    @Insert
    suspend fun insert(people: ResenasLocal)

    @Delete
    suspend fun delete(person: ResenasLocal)

    @Query("DELETE FROM Review_Table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Review_Table'")
    suspend fun deleteAllTableUsers()


}