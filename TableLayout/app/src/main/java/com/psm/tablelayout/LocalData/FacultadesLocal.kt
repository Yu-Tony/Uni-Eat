package com.psm.tablelayout.LocalData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FacultadesLocal(
    @PrimaryKey(autoGenerate = true)
    var facultadesID:Int?=null,
    var facultadesNombre:String?=null,
    var facultadesImage:String? = null,
    var imgArray: ByteArray? = null)
