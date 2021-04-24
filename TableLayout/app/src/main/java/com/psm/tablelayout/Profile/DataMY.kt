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
        perf.MYname = "Teb"
        perf.MYlast = "Soto"
        perf.MYimage = R.drawable.nct01
        perf.MYcomidas
        DataMY.perfil.add(perf)

    }
}