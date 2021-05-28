package com.psm.tablelayout.Search

import FILTER_NAME
import FILTER_TYPE
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.psm.tablelayout.CardsLong.CardsAdapterAll
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.search.*

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

        type=="null"
        //DataCards.content =  this
        //RecyclerView
        rcListComidaSearch.layoutManager =  LinearLayoutManager(this)
        this.reviewAdapter =  CardsAdapterAll(this, DataCards.resenas)
        rcListComidaSearch.adapter = this.reviewAdapter

        //--------------------------------------------------------SearchView
        //searchbarSearch.setOnQueryTextListener(this)


        //--------------------------------------------------------START FROM HOME
        //https://stackoverflow.com/questions/8255618/check-if-extras-are-set-or-not

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



        }



        //--------------------------------------------------------ADVANCED SEARCH
        /*var toggleAdvanced = findViewById<Button>(R.id.btnToggleSearchAdvanced)
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

        }*/



    }


    override fun onResume() {
        super.onResume()


        val connMgr = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected)
        {

            DataCards.getResenas()

            if(type == "1")
            {
                //https://stackoverflow.com/questions/51111974/how-to-implement-multiple-filters-in-searchview-using-recyclerview-and-cardview
                Toast.makeText(this,"FILTER??", Toast.LENGTH_SHORT).show();
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
        {

        }



    }

}