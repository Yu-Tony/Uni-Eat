package com.psm.tablelayout.CardsLong

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.CardsHome.CardsHomeAdapterBest
import com.psm.tablelayout.LocalData.Facultades.FacultadesViewModel
import com.psm.tablelayout.LocalData.Perfil.PerfilViewModel
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_main.*


class CardHomeFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterFacu:CardsHomeAdapterFacu? = null
    private var adapterCateg:CardsHomeAdapterCateg? = null
    private var adapterBest:CardsHomeAdapterBest? = null
    //private var adapterBest:CardsHomeAdapterBest? = null

/////////////////////////////
//private lateinit var mFacultadesViewModel: FacultadesViewModel

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
        val layoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recycleFacultades.layoutManager =  layoutManager
        adapterFacu = context?.let { CardsHomeAdapterFacu(it, DataCards.facultad) }
        recycleFacultades.adapter = adapterFacu

        recycleCategoria.layoutManager =  layoutManager2
        adapterCateg = context?.let { CardsHomeAdapterCateg(it, DataCards.categorias) }
        recycleCategoria.adapter = adapterCateg

        recycleBest.layoutManager =  layoutManager3
        adapterBest = context?.let { CardsHomeAdapterBest(it, DataCards.BestResenas) }
        recycleBest.adapter = adapterBest

        //mFacultadesViewModel = ViewModelProvider(this).get(FacultadesViewModel::class.java)


        /* recycleBest.layoutManager =  layoutManager3
         adapterBest = context?.let { CardsHomeAdapterBest(it, DataCards.comida) }
         recycleBest.adapter = adapterBest*/




    }


    override fun onResume() {
        super.onResume()

        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {
            /*var AllUsersInDB = mFacultadesViewModel.readAllData
            if(AllUsersInDB!=null)
            {
                mFacultadesViewModel.deleteAllUsers()
            }*/

            DataCards.getCategorias()
            DataCards.getFacultades()
            DataCards.getBest()
            Toast.makeText(getActivity(),"Cargando...", Toast.LENGTH_SHORT).show();

           /* var i=0;
            do {
                mFacultadesViewModel.insert(DataCards.facultadL[i])
                i = (i+1)
            }while (DataCards.facultadL[i]!=null)*/


            if(llProgressBar != null) {
                llProgressBar.visibility = View.VISIBLE
            }
            Handler().postDelayed(
                {
                    adapterFacu?.setData(DataCards.facultad)
                    adapterCateg?.setData(DataCards.categorias)
                    adapterBest?.setData(DataCards.BestResenas)
                    adapterFacu?.notifyDataSetChanged()
                    adapterCateg?.notifyDataSetChanged()
                    adapterBest?.notifyDataSetChanged()

                },
                5000 // value in milliseconds
            )
            if(llProgressBar != null) {
                llProgressBar.visibility = View.GONE
            }

        }
        else
        {
           /* mFacultadesViewModel = ViewModelProvider(this).get(FacultadesViewModel::class.java)
            mFacultadesViewModel.readAllData.observe(viewLifecycleOwner, Observer { perfil->


                if(perfil!=null)
                {
                    Toast.makeText(getActivity(),"Getting from database",Toast.LENGTH_SHORT).show();

                    var i=0;
                    do {
                        DataCards.facultadL.add(perfil[i])
                       // mFacultadesViewModel.insert(DataCards.facultadL[i])
                        i = (i+1)
                    }while (DataCards.facultadL[i]!=null)

                }


            })*/


        }



    }

}