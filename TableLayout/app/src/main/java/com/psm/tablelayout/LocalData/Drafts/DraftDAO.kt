package com.psm.tablelayout.LocalData.Drafts

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal

@Dao
interface DraftDAO
{
    @Query("SELECT * FROM Draft_Table")
    fun getAll():LiveData<List<DraftLocal>>

    @Query("SELECT * FROM Draft_Table WHERE resenaID = :id")
    suspend fun getByID(id:String): DraftLocal

    @Update
    suspend fun update(person: DraftLocal)

    @Insert
    suspend fun insert(people: DraftLocal)

    @Delete
    suspend fun delete(person: DraftLocal)

    @Query("DELETE FROM Best_Table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Draft_Table'")
    suspend fun deleteAllTableUsers()


}