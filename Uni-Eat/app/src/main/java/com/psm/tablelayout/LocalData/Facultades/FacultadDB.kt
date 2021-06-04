package com.psm.tablelayout.LocalData.Facultades

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities=[FacultadesLocal::class],
    version=1,
    exportSchema = false
)
abstract class FacultadDB : RoomDatabase()
{
    abstract fun facultadesDAO(): FacultadesDAO

    //singleton
    companion object
    {
        @Volatile
        private var INSTANCE: FacultadDB?=null

        fun getInstance(context: Context): FacultadDB
        {
            if(INSTANCE ==null)
            {
                INSTANCE =Room.databaseBuilder(
                    context.applicationContext,
                    FacultadDB::class.java, "facu_database"
                ).build()
            }
            return INSTANCE!!
        }
    }
}