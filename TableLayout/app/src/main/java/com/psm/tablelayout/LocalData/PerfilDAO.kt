package com.psm.tablelayout.LocalData

import androidx.room.*
import com.psm.tablelayout.CardsLong.Perfil

@Dao
interface PerfilDAO
{
    @Query("SELECT * FROM PerfilLocal")
    suspend fun getAll():List<PerfilLocal>

    @Query("SELECT * FROM PerfilLocal WHERE userID = :id")
    suspend fun getByID(id:Int):PerfilLocal

    @Update
    suspend fun update(person: PerfilLocal)

    @Insert
    suspend fun insert(people: List<PerfilLocal>)

    @Delete
    suspend fun delete(person: PerfilLocal)
}