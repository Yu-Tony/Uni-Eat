package com.psm.tablelayout.CardsLong

import com.psm.tablelayout.R

//Nos permite manejar los generos
class Facultades(var facultadesID:Int?=null,
                 var facultadesNombre:String?=null,
                 var facultadesImage:String? = null,
                 var imgArray: ByteArray? = null)
{


}


class Categorias(var categoriaID:Int?=null,
                 var categoriaNombre:String?=null,
                 var categoriaImage:String? = null,
                 var imgArray: ByteArray? = null)
{

    /*override fun toString(): String {
        return this.strTitleC

    }*/
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


class Comida(
    var strTitle:String? = null,
    var strDescription:String? =  null,
    var intIdImage:Int? =  R.drawable.nct01,
    var facu: Facultades? = null,
    var categ: Categorias? = null,
    var imgArray: ByteArray? = null,
    var rating:Float?=null
   )
{}

