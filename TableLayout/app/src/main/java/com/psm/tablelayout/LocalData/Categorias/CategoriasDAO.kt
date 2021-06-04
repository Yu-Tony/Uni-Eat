package com.psm.tablelayout.LocalData.Categorias

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal

@Dao
interface CategoriasDAO
{
    @Query("SELECT * FROM Categorias_Table")
    fun getAll():LiveData<List<CategoriasLocal>>

    @Query("SELECT * FROM Categorias_Table WHERE categoriaID = :id")
    suspend fun getByID(id:String): CategoriasLocal

    @Update
    suspend fun update(person: CategoriasLocal)

    @Insert
    suspend fun insert(people: CategoriasLocal)

    @Delete
    suspend fun delete(person: CategoriasLocal)

    @Query("DELETE FROM Categorias_Table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Categorias_Table'")
    suspend fun deleteAllTableUsers()


}