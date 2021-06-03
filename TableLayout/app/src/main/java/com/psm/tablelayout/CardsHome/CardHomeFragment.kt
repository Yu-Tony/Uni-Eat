package com.psm.tablelayout.CardsLong

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.psm.tablelayout.CardsHome.CardsHomeAdapterBest
import com.psm.tablelayout.LocalData.Facultades.FacultadesLocal
import com.psm.tablelayout.LocalData.Facultades.FacultadesViewModel
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilViewModel
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.content_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CardHomeFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterFacu:CardsHomeAdapterFacu? = null
    private var adapterCateg:CardsHomeAdapterCateg? = null
    private var adapterBest:CardsHomeAdapterBest? = null
    //private var adapterBest:CardsHomeAdapterBest? = null

/////////////////////////////
//private lateinit var mFacultadesViewModel: FacultadesViewModel
var refreshLayout: SwipeRefreshLayout? = null

    //////////////////////////////////
    private lateinit var mFacuViewModel: FacultadesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //getCategorias();
        val view: View = inflater.inflate(R.layout.content_home, container, false)

        refreshLayout = view.findViewById<View>(R.id.swipeHome) as SwipeRefreshLayout

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

        refreshLayout!!.setOnRefreshListener { refreshApp() }

        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {
            /*var AllUsersInDB = mFacultadesViewModel.readAllData
            if(AllUsersInDB!=null)
            {
                mFacultadesViewModel.deleteAllUsers()
            }*/


            getCategorias()
            getFacultades()
            getBest()
            //Toast.makeText(getActivity(),"Cargando...", Toast.LENGTH_SHORT).show();

            /* var i=0;
             do {
                 mFacultadesViewModel.insert(DataCards.facultadL[i])
                 i = (i+1)
             }while (DataCards.facultadL[i]!=null)*/



            llProgressBar.visibility = View.VISIBLE
            Handler().postDelayed(
                {
                    adapterFacu?.setData(DataCards.facultad)
                    adapterCateg?.setData(DataCards.categorias)
                    adapterBest?.setData(DataCards.BestResenas)
                    adapterFacu?.notifyDataSetChanged()
                    adapterCateg?.notifyDataSetChanged()
                    adapterBest?.notifyDataSetChanged()
                    llProgressBar.visibility = View.GONE

                    //refreshLayout?.setRefreshing(false);

                },
                6000 // value in milliseconds
            )




        }
        else
        {
            TODO("ELSE DE FRAGMENT OSEA TRAER LAS COSAS DE LA BASE DE DATOS")
        }


    }


    override fun onResume() {
        super.onResume()




    }

    private fun refreshApp()
    {

            val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected)
            {
                /*var AllUsersInDB = mFacultadesViewModel.readAllData
                if(AllUsersInDB!=null)
                {
                    mFacultadesViewModel.deleteAllUsers()
                }*/

                getCategorias()
                getFacultades()
                getBest()
                //Toast.makeText(getActivity(),"Cargando...", Toast.LENGTH_SHORT).show();

                /* var i=0;
                 do {
                     mFacultadesViewModel.insert(DataCards.facultadL[i])
                     i = (i+1)
                 }while (DataCards.facultadL[i]!=null)*/



                //llProgressBar.visibility = View.VISIBLE
                Handler().postDelayed(
                    {
                        adapterFacu?.setData(DataCards.facultad)
                        adapterCateg?.setData(DataCards.categorias)
                        adapterBest?.setData(DataCards.BestResenas)
                        adapterFacu?.notifyDataSetChanged()
                        adapterCateg?.notifyDataSetChanged()
                        adapterBest?.notifyDataSetChanged()
                        //llProgressBar.visibility = View.GONE

                        refreshLayout?.setRefreshing(false);

                    },
                    6000 // value in milliseconds
                )




            }
            else
            {

                Toast.makeText(getActivity(),"No hay conexion a internet",Toast.LENGTH_SHORT).show();
                refreshLayout?.setRefreshing(false);
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


    fun getFacultades()
    {
        DataCards.facultad.clear()



        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultFacultades: Call<List<Facultades>> = service.getFacultades()

        resultFacultades!!.enqueue(object: Callback<List<Facultades>> {

            override fun onFailure(call: Call<List<Facultades>>, t: Throwable){
                Log.e("getfaultades", "error")
            }

            override fun onResponse(call: Call<List<Facultades>>, response: Response<List<Facultades>>){
                val arrayItems =  response.body()

              /*  var AllFacusInDB = mFacuViewModel.readAllData
                if(AllFacusInDB!=null)
                {
                    mFacuViewModel.deleteAllUsers()
                }*/

                if (arrayItems != null){
                    for (item in arrayItems!!){

                        val strImage:String =  item.facultadesImage!!.replace("data:image/png;base64,","")
                        var byteArray:ByteArray? = null
                        byteArray =  Base64.getDecoder().decode(strImage)
                        var  facu = Facultades(item.facultadesID,item.facultadesNombre,strImage,byteArray  )
                        DataCards.facultad.add(facu)
/*
                        val Facu =
                            FacultadesLocal(
                    null,
                                item.facultadesNombre,
                                strImage,
                                byteArray
                            )
                        mFacuViewModel.insert(Facu)*/

                    }
                }

            }
        })
    }

    fun getCategorias()
    {
        DataCards.categorias.clear()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultCategorias: Call<List<Categorias>> = service.getCategorias()

        resultCategorias!!.enqueue(object: Callback<List<Categorias>> {

            override fun onFailure(call: Call<List<Categorias>>, t: Throwable){
                Log.e("getcategorias", "error")
            }

            override fun onResponse(call: Call<List<Categorias>>, response: Response<List<Categorias>>){
                val arrayItems =  response.body()
                if (arrayItems != null){
                    for (item in arrayItems!!){
                        val strImage:String =  item.categoriaImage!!.replace("data:image/png;base64,","")
                        var byteArray:ByteArray? = null
                        byteArray =  Base64.getDecoder().decode(strImage)
                        var  categ = Categorias(item.categoriaID,item.categoriaNombre,strImage,byteArray  )
                        DataCards.categorias.add(categ)


                    }
                }
            }
        })
    }

    fun getBest()
    {
        DataCards.BestResenas.clear()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultResenas: Call<List<Resena>> = service.getBestResenas()

        resultResenas!!.enqueue(object: Callback<List<Resena>> {

            override fun onFailure(call: Call<List<Resena>>, t: Throwable){
                Log.e("getres", "error")
            }

            override fun onResponse(call: Call<List<Resena>>, response: Response<List<Resena>>){
                val arrayItems =  response.body()
                if (arrayItems != null){
                    for (item in arrayItems!!){

                        if(item.resenaPublicado!=0)
                        {

                            var byteArray1:ByteArray? = null
                            var byteArray2:ByteArray? = null
                            var byteArray3:ByteArray? = null
                            var byteArray4:ByteArray? = null
                            var byteArray5:ByteArray? = null

                            if(item.resenaImageOne!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray1 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageTwo!=null)
                            {
                                val strImage:String =  item.resenaImageTwo!!.replace("data:image/png;base64,","")
                                byteArray2 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageThree!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray3 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageFour!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray4 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageFive!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray5 =  Base64.getDecoder().decode(strImage)
                            }

                            var  res = Resena(item.resenaID,item.resenaUsuario,item.resenaTitulo,item.resenaCategoria,item.resenaFacultad,
                                item.resenaDescription,item.resenaRate,item.resenaPublicado,
                                item.resenaImageOne, item.resenaImageTwo,item.resenaImageThree,
                                item.resenaImageFour,item.resenaImageFive,byteArray1 ,byteArray2,
                                byteArray3,byteArray4,byteArray5)

                            DataCards.BestResenas.add(res)

                        }



                    }
                }
            }
        })
    }
}