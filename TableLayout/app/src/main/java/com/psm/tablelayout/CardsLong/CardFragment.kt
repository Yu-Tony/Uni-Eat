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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.psm.tablelayout.LocalData.Drafts.DraftViewModel
import com.psm.tablelayout.LocalData.Resenas.ResenasViewModel
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.my_principal.*
import kotlinx.android.synthetic.main.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


open class CardFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterDrafts:CardsAdapterAll? = null

    ///////////////////////////////////////////////////

    var refreshLayout: SwipeRefreshLayout? = null

    /////////////////////////////////////////////////
    private lateinit var mResViewModel: DraftViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.content_main, container, false)

        refreshLayout = view.findViewById<View>(R.id.swipeDrafts) as SwipeRefreshLayout

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rcListComidaSearch.layoutManager =  layoutManager
        this.adapterDrafts = context?.let { CardsAdapterAll(it, DataMY.resenasDrafts) }
        rcListComidaSearch.adapter = this.adapterDrafts

        refreshLayout!!.setOnRefreshListener { refreshApp() }

        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {

                llProgressBarDrafts.visibility = View.VISIBLE
                getresenasDrafts()


            //Toast.makeText(getActivity(),"Cargando...", Toast.LENGTH_SHORT).show();

        }
        else
        {

            DataCards.resenas.clear()
            mResViewModel = ViewModelProvider(this).get(DraftViewModel::class.java)
            mResViewModel.readAllData.observe(this, androidx.lifecycle.Observer { res->

                if(res!=null)
                {
                    Toast.makeText(getActivity(),"Cargando de la base de datos...",Toast.LENGTH_SHORT).show();

                    var FullSearch = res.size
                    var iteratorSearch=0;

                    while (iteratorSearch<FullSearch)
                    {
                        Toast.makeText(getActivity(),res[iteratorSearch].resenaPublicado.toString(),Toast.LENGTH_SHORT).show();


                        if((res[iteratorSearch].resenaPublicado.toString()=="0") && (res[iteratorSearch].resenaUsuario== DataMY.perfil?.userMail))
                        {
                            var  res = Resena(res[iteratorSearch].resenaID,res[iteratorSearch].resenaUsuario,res[iteratorSearch].resenaTitulo,res[iteratorSearch].resenaCategoria,res[iteratorSearch].resenaFacultad,
                                res[iteratorSearch].resenaDescription,res[iteratorSearch].resenaRate,res[iteratorSearch].resenaPublicado,
                                res[iteratorSearch].resenaImageOne, res[iteratorSearch].resenaImageTwo,res[iteratorSearch].resenaImageThree,
                                res[iteratorSearch].resenaImageFour,res[iteratorSearch].resenaImageFive,res[iteratorSearch].imgArray1 ,
                                res[iteratorSearch].imgArray2,
                                res[iteratorSearch].imgArray3,res[iteratorSearch].imgArray4,res[iteratorSearch].imgArray5)

                            DataMY.resenasDrafts.add(res)


                        }

                        iteratorSearch = (iteratorSearch+1)
                    }

                    Handler().postDelayed(
                        {
                            adapterDrafts?.setData(DataMY.resenasDrafts)
                            adapterDrafts?.notifyDataSetChanged()
                            // llProgressBarSearch.visibility = View.GONE

                        },
                        5000 // value in milliseconds
                    )



                }


            })
        }


    }

    private fun refreshApp()
    {
        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {

            getresenasDrafts()
            //Toast.makeText(getActivity(),"Cargando...", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getActivity(),"No hay conexion a internet", Toast.LENGTH_SHORT).show();
            refreshLayout?.setRefreshing(false);
        }

    }

    fun getresenasDrafts()
    {

        DataMY.resenasDrafts.clear()

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultResenas: Call<List<Resena>> = service.getResenas()

        resultResenas!!.enqueue(object: Callback<List<Resena>> {

            override fun onFailure(call: Call<List<Resena>>, t: Throwable){
                Log.e("getres", "error")
            }

            override fun onResponse(call: Call<List<Resena>>, response: Response<List<Resena>>){

                val arrayItems =  response.body()
                if (arrayItems != null){
                    for (item in arrayItems!!){

                        if((item.resenaPublicado==0) && (item.resenaUsuario== DataMY.perfil?.userMail))
                        {
                            DataMY.perfil?.userMail?.let { Log.e("bestie", it) }
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

                            DataMY.resenasDrafts.add(res)


                        }



                    }

                    Handler().postDelayed(
                        {
                            adapterDrafts?.setData(DataMY.resenasDrafts)
                            adapterDrafts?.notifyDataSetChanged()
                            refreshLayout?.setRefreshing(false);
                            llProgressBarDrafts.visibility = View.GONE
                        },
                        7000 // value in milliseconds
                    )
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()




    }


}