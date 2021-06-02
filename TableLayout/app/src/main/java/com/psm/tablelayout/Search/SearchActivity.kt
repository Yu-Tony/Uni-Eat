package com.psm.tablelayout.Search

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.psm.tablelayout.CardsLong.CardsAdapterAll
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.CardsLong.Resena
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

//class SearchActivity:AppCompatActivity(), SearchView.OnQueryTextListener
class SearchActivity:AppCompatActivity(), SearchView.OnQueryTextListener {

    private var reviewAdapter:CardsAdapterAll? = null
    private var type:String="null";
    private var type2:String="1";
    private var type3:String="1";
    private var textToSearch:String ="null";
    //////////////////////////////////////

    var spinnerType:Spinner?=null
    var spinnerSort:Spinner?=null

    /*private val fullCategories =  ArrayList<Categorias>(DataCards.categorias)
    private val fullFacultades =  ArrayList<Facultades>(DataCards.facultad)*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.search)

        type=="null"
        //DataCards.content =  this
        //RecyclerView
         RecycleViewSearch.layoutManager =  LinearLayoutManager(this)
         this.reviewAdapter =  CardsAdapterAll(this, DataCards.resenas)
         RecycleViewSearch.adapter = this.reviewAdapter

        //--------------------------------------------------------SearchView
        searchbarSearch.setOnQueryTextListener(this)


        //--------------------------------------------------------START FROM HOME
        //https://stackoverflow.com/questions/8255618/check-if-extras-are-set-or-not
      /*  var extraStr:String? = null
        val extras = getIntent().extras
        if (extras != null) {
            extraStr = extras.getString(FILTER_NAME)
        } else {
            extraStr = "extra not set"
        }

        if(extraStr == "extra not set")
        {

        }
        else
        {
            //https://stackoverflow.com/questions/45132729/get-string-extra-from-activity-kotlin
            type = intent.getStringExtra(FILTER_TYPE).toString()
            if(type == "1")
            {
                //https://stackoverflow.com/questions/51111974/how-to-implement-multiple-filters-in-searchview-using-recyclerview-and-cardview
                reviewAdapter?.FacuFilter()?.filter(extraStr)
                if (extraStr != null) {
                    // Log.e("mamba facu", extraStr)
                }
            }

            if(type == "2")
            {
                reviewAdapter?.CategFilter()?.filter(extraStr)
                if (extraStr != null) {
                    // Log.e("mamba categ", extraStr)
                }
            }


        }*/



        //--------------------------------------------------------ADVANCED SEARCH
        var toggleAdvanced = findViewById<Button>(R.id.btnToggleSearchAdvanced)
        val AdvancedLayout = findViewById<LinearLayout>(R.id.SearchLayoutAdvanced)
        toggleAdvanced.setOnClickListener {
            if (AdvancedLayout.getVisibility() == View.VISIBLE)
            {
                AdvancedLayout.setVisibility(View.GONE);
            }
            else
            {
                AdvancedLayout.setVisibility(View.VISIBLE);

                //https://www.youtube.com/watch?v=nzQVzIHIzUg
                 spinnerType=findViewById<Spinner>(R.id.spinnerType)
                val listType = resources.getStringArray(R.array.listType)
                val adaptadorSpinnerType = ArrayAdapter(this,android.R.layout.simple_spinner_item,listType)
                spinnerType?.adapter=adaptadorSpinnerType;

                spinnerType?.onItemSelectedListener =object: AdapterView.OnItemSelectedListener
                {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        if(position==0) { type2="1"}
                        if(position==1) { type2="2"}
                        if(position==2) { type2="3"}


                    }

                }

                spinnerSort=findViewById<Spinner>(R.id.spinnerSort)
                val listSort = resources.getStringArray(R.array.listSort)
                val adaptadorSpinnerSort = ArrayAdapter(this,android.R.layout.simple_spinner_item,listSort)
                spinnerSort?.adapter=adaptadorSpinnerSort

                spinnerSort?.onItemSelectedListener =object: AdapterView.OnItemSelectedListener
                {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        if(position==0)
                        {
                            type3="1"
                            if(type2=="1"){reviewAdapter?.NameFilter()?.filter(null)}
                            if(type2=="2"){reviewAdapter?.FacuFilter()?.filter(null)}
                            if(type2=="3"){reviewAdapter?.CategFilter()?.filter(null)}


                        }
                        if(position==1)
                        {   type3="2"
                            if(type2=="1"){reviewAdapter?.NameFilterUpside()?.filter(null)}
                            if(type2=="2"){reviewAdapter?.FacuFilterUpside()?.filter(null)}
                            if(type2=="3"){reviewAdapter?.CategFilterUpside()?.filter(null)}
                        }



                    }

                }
            }

        }


        /*ScrollViewSearch.setOnScrollChangeListener(View.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > 20) {
                swipeSearch.setEnabled(false)
            } else {
                swipeSearch.setEnabled(true)
            }
        })*/



        refreshApp();

    }



    override fun onQueryTextSubmit(query: String?): Boolean {

        return false;
    }


    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText != null) {
            textToSearch = newText
        }

        if(type2=="1")
        {
            if(type3=="1")
            {
                reviewAdapter?.NameFilter()?.filter(newText)
            }
            else
            {
                reviewAdapter?.NameFilterUpside()?.filter(newText)
            }

        }
        if(type2=="2")
        {
            if(type3=="1")
            {
                reviewAdapter?.FacuFilter()?.filter(newText)
            }
            else
            {
                reviewAdapter?.FacuFilterUpside()?.filter(newText)
            }

        }
        if(type2=="3")
        {
            if(type3=="1")
            {
                reviewAdapter?.CategFilter()?.filter(newText)
            }
            else
            {
                reviewAdapter?.CategFilterUpside()?.filter(newText)
            }

        }



        return false;
    }

    override fun onResume() {
        super.onResume()





    }

    private fun refreshApp()
    {
        swipeSearch.setOnRefreshListener {
            val connMgr = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected)
            {

                searchbarSearch.setQuery("", false);
                searchbarSearch.setIconified(true);
                type2 = "1"
                type3 = "1"
                spinnerType?.setSelection(0)
                spinnerSort?.setSelection(0)
                /* if(textToSearch=="" && type2=="1" && type3=="1" && type=="null")
                 {*/

                //llProgressBarSearch.visibility = View.VISIBLE
                getResenas()
                /*}*/


                //Toast.makeText(this,"Cargando...", Toast.LENGTH_SHORT).show();


            }
            else
            {

            }
        }


    }

    fun getResenas()
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

                    Handler().postDelayed(
                        {
                            reviewAdapter?.setData(DataCards.resenas)
                            reviewAdapter?.notifyDataSetChanged()
                            // llProgressBarSearch.visibility = View.GONE
                            swipeSearch.isRefreshing=false;
                        },
                        11000 // value in milliseconds
                    )
                }
            }
        })
    }
}