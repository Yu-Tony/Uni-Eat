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
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.content_main.*


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
                    //The key argument here must match that used in the other activity
                }

        val connMgr = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {


            if(type == "1")
            {
                //https://stackoverflow.com/questions/51111974/how-to-implement-multiple-filters-in-searchview-using-recyclerview-and-cardview
                Toast.makeText(this,"FILTER??", Toast.LENGTH_SHORT).show();

                //reviewAdapter?.setData(DataCards.resenas)
                reviewAdapter?.FacuFilter()?.filter(extraStr)
                if (extraStr != null) {
                    //Log.e("mamba facu", extraStr!!)
                }
            }

            if(type == "2")
            {
                //reviewAdapter?.setData(DataCards.resenas)
                reviewAdapter?.CategFilter()?.filter(extraStr)
                if (extraStr != null) {
                    // Log.e("mamba categ", extraStr!!)
                }
            }



            Toast.makeText(this,"Cargando...", Toast.LENGTH_SHORT).show();
            Handler().postDelayed(
                {
                    reviewAdapter?.setData(DataCards.resenas)
                    reviewAdapter?.notifyDataSetChanged()

                },
                4000 // value in milliseconds
            )

        }
        else
        {}


            //--------------------------------------------------------SearchView
        //searchbarSearch.setOnQueryTextListener(this)


    }


    override fun onResume() {
        super.onResume()







    }

}