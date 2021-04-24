package com.psm.tablelayout.CardsSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.content_main.*


class CardFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter:CardsAdapter? = null

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
        this.adapter = context?.let { CardsAdapter(it, DataCards.comida) }
        rcListComidaSearch.adapter = this.adapter
    }


}