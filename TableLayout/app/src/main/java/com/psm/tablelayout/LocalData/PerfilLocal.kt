package com.psm.tablelayout.LocalData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PerfilLocal(
    @PrimaryKey(autoGenerate = true)
    var userID:Int? =  null,
    var userNombre:String? = null,
    var userApellidos:String? =  null,
    var userMail:String? =  null,
    var userPassword:String? =  null,
    var userPhone:String? =  null,
    var userImage:String? = null,
    var imgArray: ByteArray? = null)

