package com.psm.tablelayout.LocalData.Best

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal

@Dao
interface BestDAO
{
    @Query("SELECT * FROM Best_Table")
    fun getAll():LiveData<List<BestLocal>>

    @Query("SELECT * FROM Best_Table WHERE resenaID = :id")
    suspend fun getByID(id:String): BestLocal

    @Update
    suspend fun update(person: BestLocal)

    @Insert
    suspend fun insert(people: BestLocal)

    @Delete
    suspend fun delete(person: BestLocal)

    @Query("DELETE FROM Best_Table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Best_Table'")
    suspend fun deleteAllTableUsers()


}