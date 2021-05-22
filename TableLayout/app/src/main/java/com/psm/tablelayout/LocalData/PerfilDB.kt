package com.psm.tablelayout.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities=[PerfilLocal::class],
    version=1
)
abstract class PerfilDB : RoomDatabase()
{
    abstract fun perfilDAO(): PerfilDAO
}