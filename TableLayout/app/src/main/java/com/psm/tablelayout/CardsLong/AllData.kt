package com.psm.tablelayout.CardsLong

import com.psm.tablelayout.R

//Nos permite manejar los generos
class Facultades(var facultadesID:Int?=null,
                 var facultadesNombre:String?=null,
                 var facultadesImage:String? = null,
                 var imgArray: ByteArray? = null)
{
    override fun toString(): String {
        return this.facultadesNombre!!

    }

}



class Categorias(var categoriaID:Int?=null,
                 var categoriaNombre:String?=null,
                 var categoriaImage:String? = null,
                 var imgArray: ByteArray? = null)
{

    override fun toString(): String {
        return this.categoriaNombre!!

    }
}

data class Perfil(
    var userID:Int? =  null,
    var userNombre:String? = null,
    var userApellidos:String? =  null,
    var userMail:String? =  null,
    var userPassword:String? =  null,
    var userPhone:String? =  null,
    var userImage:String? = null,
    var imgArray: ByteArray? = null
)
{}


data class Resena(
    var resenaID:Int? =  null,
    var resenaUsuario:String? = null,
    var resenaTitulo:String? =  null,
    var resenaCategoria:String? =  null,
    var resenaFacultad:String? =  null,
    var resenaDescription:String? =  null,
    var resenaRate:Float?=null,
    var resenaPublicado:Int? =  null,
    var resenaImageOne:String? = null,
    var resenaImageTwo:String? = null,
    var resenaImageThree:String? = null,
    var resenaImageFour:String? = null,
    var resenaImageFive:String? = null,
    var imgArray1: ByteArray? = null,
    var imgArray2: ByteArray? = null,
    var imgArray3: ByteArray? = null,
    var imgArray4: ByteArray? = null,
    var imgArray5: ByteArray? = null
)
{}