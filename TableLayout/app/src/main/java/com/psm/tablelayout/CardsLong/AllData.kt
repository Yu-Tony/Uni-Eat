package com.psm.tablelayout.CardsLong

import com.psm.tablelayout.R

//Nos permite manejar los generos
class Facultades(var imageFacu:Int,
                 var strTitleF:String,
                 var imgArray: ByteArray? = null,
                 var locationF:String? = null,
                 var campusF:Int? =  null,
                 var stateF:Int? =  null)
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
    var imgArray: ByteArray? = null
   )
{}

class Perfil(
    var MYname:String? = null,
    var MYlast:String? =  null,
    var MYemail:String? =  null,
    var MYphone:String? =  null,
    var MYpassword:String? =  null,
    var MYimage:Int? =  R.drawable.nct01,
    var MYid:Int? =  null,
    var MYcomidas: ArrayList<Comida>? = null,
    var imgArray: ByteArray? = null,
    var MYlocation:String? = null,
    var MYcampus:Int? =  null
)
{}

