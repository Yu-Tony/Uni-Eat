package com.psm.tablelayout.LocalData

import android.app.Application
import androidx.room.Room

class PerfilApp:Application() {


    val room = Room.databaseBuilder(this, PerfilDB::class.java, "perfil").build()
}