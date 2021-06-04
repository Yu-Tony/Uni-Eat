package com.psm.tablelayout.LocalData.Perfil

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Perfil_Table")
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

