package com.psm.tablelayout.Profile

import android.util.Log
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.CardsLong.Resena
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

object DataMY {
    var perfil:Perfil? = null
    val resenasMine = ArrayList<Resena>()
    var resenasDrafts = ArrayList<Resena>()
    val resenasFav = ArrayList<Resena>()



    init {
        //this.initializeFacultad()
        //this.initializeCategorias()
        // this.initializeComidas()
        //this.getresenasMine()
        //this.getresenasDrafts()
        //this.getresenasFav()
    }


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
        perfil =profile




       /* perfil[0].userID = profile.userID
        perfil[0].userNombre = profile.userNombre
        perfil[0].userApellidos = profile.userApellidos
        perfil[0].userMail = profile.userMail
        perfil[0].userPassword = profile.userPassword
        perfil[0].userPhone = profile.userPhone
        perfil[0].userImage = profile.userImage
        perfil[0].imgArray = profile.imgArray*/


    }





}