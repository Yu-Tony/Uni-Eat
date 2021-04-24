package com.psm.tablelayout.CardsSearch

import com.psm.tablelayout.R

//Nos permite manejar los generos
class Facultades(var imageFacu:Int, var strTitleF:String, var imgArray: ByteArray? = null){

    override fun toString(): String {
        return this.strTitleF

    }
}

class Categorias(var imageCateg:Int, var strTitleC:String, var imgArray: ByteArray? = null){

    override fun toString(): String {
        return this.strTitleC

    }
}



//Las variables se declaran como opcionales.
//Esto permite crear un objecto de album por defecto vacio
//que sea usa cuando vamos agregar un nuevo album
class Comida(
    var strTitle:String? = null,
    var strDescription:String? =  null,
    var intIdImage:Int? =  R.drawable.nct01,
    var facu: Facultades? = null,
    var categ: Categorias? = null,
    var imgArray: ByteArray? = null
   )
{}