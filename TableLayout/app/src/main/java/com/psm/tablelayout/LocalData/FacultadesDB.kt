package com.psm.tablelayout.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities= [FacultadesLocal::class],
    version = 1
)
abstract class FacultadesDB:RoomDatabase() {

    abstract fun facultadesLocalDAO(): FacultadesLocalDAO
}