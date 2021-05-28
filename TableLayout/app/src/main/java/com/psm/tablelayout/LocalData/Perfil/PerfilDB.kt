package com.psm.tablelayout.LocalData.Perfil

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.psm.tablelayout.LocalData.Perfil.PerfilDAO

@Database(
    entities=[PerfilLocal::class],
    version=1,
    exportSchema = false
)
abstract class PerfilDB : RoomDatabase()
{
    abstract fun perfilDAO(): PerfilDAO

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
                ).build()
            }
            return INSTANCE!!
        }
    }
}