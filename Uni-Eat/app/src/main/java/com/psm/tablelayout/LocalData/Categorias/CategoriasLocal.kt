package com.psm.tablelayout.LocalData.Categorias

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Categorias_Table")
class CategoriasLocal(
    @PrimaryKey(autoGenerate = true)
    var categoriaID:Int? =  null,
    var categoriaNombre:String? = null,
    var categoriaImage:String? =  null,
    var imgArray: ByteArray? = null)
