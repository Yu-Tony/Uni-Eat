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


class CardHomeFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter:CardsHomeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_home, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        rcListAlbum.layoutManager =  layoutManager
        this.adapter = context?.let { CardsHomeAdapter(it, DataCards.albums) }
        rcListAlbum.adapter = this.adapter
    }
}