package com.psm.tablelayout.CardsLong

import com.psm.tablelayout.R

//Nos permite manejar los generos
class Facultades(var imageFacu:Int,
                 var strTitleF:String,
                 var imgArray: ByteArray? = null)
{

    override fun toString(): String {
        return this.strTitleF

    }
}

class Categorias(var imageCateg:Int,
                 var strTitleC:String,
                 var imgArray: ByteArray? = null)
{

    override fun toString(): String {
        return this.strTitleC

    }
}


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

class Perfil(
    var userID:Int? =  null,
    var userNombre:String? = null,
    var userApellidos:String? =  null,
    var userMail:String? =  null,
    var userPassword:String? =  null,
    var userPhone:String? =  null,
    var userImage:Int? =  R.drawable.nct01,
    var imgArray: ByteArray? = null
)
{}
