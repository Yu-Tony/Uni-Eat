package com.psm.tablelayout.Profile

import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.CardsLong.Facultades
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.R
import java.util.*
import kotlin.collections.ArrayList

object DataMY {
    val perfil = ArrayList<Perfil>()


    fun initializePerfil(userID:Int?,
                                 userNombre:String?,
                                 userApellidos:String?,
                                 userMail:String?,
                                 userPassword:String?,
                                 userPhone:String? =  null,
                                 userImage:String?)
    {

        val strImage:String =  userImage!!.replace("data:image/png;base64,","")
        var byteArray:ByteArray? = null
        byteArray =  Base64.getDecoder().decode(strImage)


        var  profile = Perfil(userID, userNombre,userApellidos,userMail,userPassword,userPhone,userImage,byteArray)
        perfil.add(profile)
        perfil[0].userID = profile.userID
        perfil[0].userNombre = profile.userNombre
        perfil[0].userApellidos = profile.userApellidos
        perfil[0].userMail = profile.userMail
        perfil[0].userPassword = profile.userPassword
        perfil[0].userPhone = profile.userPhone
        perfil[0].userImage = profile.userImage
        perfil[0].imgArray = profile.imgArray


    }
}