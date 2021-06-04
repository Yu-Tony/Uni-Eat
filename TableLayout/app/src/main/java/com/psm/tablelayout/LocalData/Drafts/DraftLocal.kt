package com.psm.tablelayout.LocalData.Drafts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Draft_Table")
class DraftLocal(
    @PrimaryKey(autoGenerate = true)
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
    var imgArray5: ByteArray? = null)

