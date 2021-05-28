package com.psm.tablelayout.CardsLong

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.content_main.*


open class CardFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterAll:CardsAdapterAll? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rcListComidaSearch.layoutManager =  layoutManager
        this.adapterAll = context?.let { CardsAdapterAll(it, DataMY.resenasDrafts) }
        rcListComidaSearch.adapter = this.adapterAll
    }

    override fun onResume() {
        super.onResume()

        /*val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            TODO("get cards")
            Toast.makeText(getActivity(),"Cargando...", Toast.LENGTH_SHORT).show();
            Handler().postDelayed(
                {

                    TODO("adapter set data")
                    TODO("adapter notify")
                },
                5000 // value in milliseconds
            )
        }
        else
        {

        }*/

    }


}