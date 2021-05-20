package com.psm.tablelayout.CardsLong

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.content_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardHomeFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterFacu:CardsHomeAdapterFacu? = null
    private var adapterCateg:CardsHomeAdapterCateg? = null
    //private var adapterBest:CardsHomeAdapterBest? = null
    var itemFacu:List<Facultades>?=null;
    var itemCard:List<Categorias>?=null;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //getCategorias();
        val view: View = inflater.inflate(R.layout.content_home, container, false)


        // Return the fragment view/layout
        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)



        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //val layoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recycleFacultades.layoutManager =  layoutManager
        adapterFacu = context?.let { CardsHomeAdapterFacu(it, DataCards.facultad) }
        recycleFacultades.adapter = adapterFacu

        recycleCategoria.layoutManager =  layoutManager2
        adapterCateg = context?.let { CardsHomeAdapterCateg(it, DataCards.categorias) }
        recycleCategoria.adapter = adapterCateg

       /* recycleBest.layoutManager =  layoutManager3
        adapterBest = context?.let { CardsHomeAdapterBest(it, DataCards.comida) }
        recycleBest.adapter = adapterBest*/




    }




}