package com.psm.tablelayout.Search

import FILTER_NAME
import FILTER_TYPE
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.psm.tablelayout.CardsLong.CardsAdapterAll
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.CardsLong.Resena
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SearchFilterActivity: AppCompatActivity() {

    private var reviewAdapter:CardsAdapterAll? = null
    private var type:String="null";
    private var textToSearch:String ="null";
    //////////////////////////////////////

    var spinnerType:Spinner?=null
    var spinnerSort:Spinner?=null

    var extraStr:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_main)

        DataCards.resenas.clear()
        //DataCards.getResenas()
        //DataCards.content =  this
        //RecyclerView


                rcListComidaSearch.layoutManager =  LinearLayoutManager(this)
                this.reviewAdapter =  CardsAdapterAll(this, DataCards.resenas)
                rcListComidaSearch.adapter = this.reviewAdapter

                val extras = getIntent().getExtras()
                if (null != extras) {
                    extraStr = extras.getString(FILTER_NAME)
                    type = extras.getString(FILTER_TYPE).toString()
                    //Toast.makeText(this,"HAY EXTRA NAME " + extraStr + " Y EXTRA NUMERO " + type, Toast.LENGTH_SHORT).show();
                    //The key argument here must match that used in the other activity
                }


        refreshApp();

        swipeDrafts.post {
            val connMgr = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo


            if (networkInfo != null && networkInfo.isConnected)
            {
                swipeDrafts.isRefreshing = true
                getResenas()
            }
            else
            {

            }



        }



            //--------------------------------------------------------SearchView
        //searchbarSearch.setOnQueryTextListener(this)


    }


    override fun onResume() {
        super.onResume()
    }

    private fun refreshApp()
    {
        swipeDrafts.setOnRefreshListener {

            getResenas()


        }
    }

    fun getResenas()
    {
        val connMgr = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo


        if (networkInfo != null && networkInfo.isConnected)
        {

            DataCards.resenas.clear()
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            var resultResenas: Call<List<Resena>> = service.getResenas()

            resultResenas!!.enqueue(object: Callback<List<Resena>> {

                override fun onFailure(call: Call<List<Resena>>, t: Throwable){
                    Log.e("getres", "error")
                }

                override fun onResponse(call: Call<List<Resena>>, response: Response<List<Resena>>){
                    Log.e("getres", "success")
                    val arrayItems =  response.body()
                    if (arrayItems != null){
                        for (item in arrayItems!!){

                            Log.e("getres", item.resenaPublicado.toString())
                            if(item.resenaPublicado!=0)
                            {
                                if((type == "1" && item.resenaFacultad==extraStr) || (type == "2" && item.resenaCategoria==extraStr))
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

                                    DataCards.resenas.add(res)
                                }

                            }



                        }


                        /*if(type == "1")
                        {
                            //https://stackoverflow.com/questions/51111974/how-to-implement-multiple-filters-in-searchview-using-recyclerview-and-cardview
                           // Toast.makeText(this,"FILTRO FACU " + extraStr, Toast.LENGTH_SHORT).show();
                            //reviewAdapter?.setData(DataCards.resenas)
                            reviewAdapter?.FacuFilter()?.filter(extraStr)

                        }

                        if(type == "2")
                        {
                            //Toast.makeText(this,"FILTRO CATEG " + extraStr, Toast.LENGTH_SHORT).show();
                            //reviewAdapter?.setData(DataCards.resenas)
                            reviewAdapter?.CategFilter()?.filter(extraStr)

                        }*/

                        Handler().postDelayed(
                            {
                                reviewAdapter?.setData(DataCards.resenas)
                                reviewAdapter?.notifyDataSetChanged()
                                swipeDrafts.isRefreshing=false;
                            },
                            5000 // value in milliseconds
                        )
                    }
                }
            })




            // Toast.makeText(this,"Cargando...", Toast.LENGTH_SHORT).show();
            /* Handler().postDelayed(
                 {
                     reviewAdapter?.setData(DataCards.resenas)
                     reviewAdapter?.notifyDataSetChanged()
                     swipeDrafts.isRefreshing=false;
                 },
                 4000 // value in milliseconds
             )*/

        }
        else
        {
            Toast.makeText(this,"No hay conexion a internet", Toast.LENGTH_SHORT).show();
            swipeDrafts.isRefreshing=false;

        }


    }
}