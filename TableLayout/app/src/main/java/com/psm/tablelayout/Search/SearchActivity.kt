package com.psm.tablelayout.Search

import FILTER_NAME
import FILTER_TYPE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.psm.tablelayout.CardsLong.*
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.search.*
import java.util.*

//class SearchActivity:AppCompatActivity(), SearchView.OnQueryTextListener
class SearchActivity:AppCompatActivity(), SearchView.OnQueryTextListener {

    private var reviewAdapter:CardsAdapter? = null
    private var type:String?=null;
    private var type2:String?="1";
    private var type3:String="1";
    /*private val fullCategories =  ArrayList<Categorias>(DataCards.categorias)
    private val fullFacultades =  ArrayList<Facultades>(DataCards.facultad)*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)


        DataCards.content =  this
        //RecyclerView
       /* RecycleViewSearch.layoutManager =  LinearLayoutManager(this)
        this.reviewAdapter =  CardsAdapter(this, DataCards.resenas)
        RecycleViewSearch.adapter = this.reviewAdapter*/

        //--------------------------------------------------------SearchView
        searchbarSearch.setOnQueryTextListener(this)


        //--------------------------------------------------------START FROM HOME
        //https://stackoverflow.com/questions/8255618/check-if-extras-are-set-or-not
        var extraStr:String? = null
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


        }



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
                val spinnerType=findViewById<Spinner>(R.id.spinnerType)
                val listType = resources.getStringArray(R.array.listType)
                val adaptadorSpinnerType = ArrayAdapter(this,android.R.layout.simple_spinner_item,listType)
                spinnerType.adapter=adaptadorSpinnerType;

                spinnerType.onItemSelectedListener =object: AdapterView.OnItemSelectedListener
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

                val spinnerSort=findViewById<Spinner>(R.id.spinnerSort)
                val listSort = resources.getStringArray(R.array.listSort)
                val adaptadorSpinnerSort = ArrayAdapter(this,android.R.layout.simple_spinner_item,listSort)
                spinnerSort.adapter=adaptadorSpinnerSort

                spinnerSort.onItemSelectedListener =object: AdapterView.OnItemSelectedListener
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



    }


    override fun onQueryTextSubmit(query: String?): Boolean {

        return false;
    }


 override fun onQueryTextChange(newText: String?): Boolean {



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

        Toast.makeText(this,"Buscando reseñas...", Toast.LENGTH_LONG).show()
        //DataCards.getResenas()

        //Toast.makeText(this,"ONRESUME", Toast.LENGTH_LONG).show()

    }

}