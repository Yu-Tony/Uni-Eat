package com.psm.tablelayout.LocalData

import android.app.Application
import androidx.room.Room

class FacultadApp: Application() {

    val app = applicationContext as FacultadApp

    val room = Room.databaseBuilder(this, FacultadesDB::class.java, "person").build()
}