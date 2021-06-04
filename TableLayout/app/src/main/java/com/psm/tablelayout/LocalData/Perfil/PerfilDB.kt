package com.psm.tablelayout.LocalData.Perfil

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomMasterTable
import com.psm.tablelayout.LocalData.Best.BestDAO
import com.psm.tablelayout.LocalData.Best.BestLocal
import com.psm.tablelayout.LocalData.Categorias.CategoriasDAO
import com.psm.tablelayout.LocalData.Categorias.CategoriasLocal
import com.psm.tablelayout.LocalData.Drafts.DraftDAO
import com.psm.tablelayout.LocalData.Drafts.DraftLocal
import com.psm.tablelayout.LocalData.Facultades.FacultadesDAO
import com.psm.tablelayout.LocalData.Facultades.FacultadesLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilDAO
import com.psm.tablelayout.LocalData.Resenas.ResenasDAO
import com.psm.tablelayout.LocalData.Resenas.ResenasLocal

@Database(
    entities=[PerfilLocal::class,
        FacultadesLocal::class,
        CategoriasLocal::class,
        BestLocal::class,
        ResenasLocal::class,
        DraftLocal::class
    ],
    version=1,
    exportSchema = false
)
abstract class PerfilDB : RoomDatabase()
{
    abstract fun perfilDAO(): PerfilDAO
    abstract fun facultadesDAO(): FacultadesDAO
    abstract fun bestDAO(): BestDAO
    abstract fun categoriasDAO(): CategoriasDAO
    abstract fun resenasDAO(): ResenasDAO
    abstract fun draftDAO(): DraftDAO


    //singleton
    companion object
    {
        @Volatile
        private var INSTANCE: PerfilDB?=null

        fun getInstance(context: Context): PerfilDB
        {
            if(INSTANCE ==null)
            {
                INSTANCE =Room.databaseBuilder(
                    context.applicationContext,
                    PerfilDB::class.java, "user_database"
                ) .build()
            }
            return INSTANCE!!
        }


    }
}