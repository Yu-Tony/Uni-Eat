package com.psm.tablelayout.Profile

import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.R

object DataMY {
    val perfil = ArrayList<Perfil>()

    init {
        this.initializePerfil()
    }

    private fun initializePerfil()
    {
        var perf =  Perfil()
        perf.userNombre = "Teb"
        perf.userApellidos = "Soto"
        perf.userImage = R.drawable.nct01
        DataMY.perfil.add(perf)

    }
}