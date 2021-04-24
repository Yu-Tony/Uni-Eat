package com.psm.tablelayout.Profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.my_principal.*
import kotlinx.android.synthetic.main.my_principal.view.*

/*https://stackoverflow.com/questions/32700818/how-to-open-a-fragment-on-button-click-from-a-fragment-in-android*/
/*https://stackoverflow.com/questions/21028786/how-do-i-open-a-new-fragment-from-another-fragment*/
class MyFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter:MyAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.my_principal, container, false)

        view.btnEditProfile.setOnClickListener { view ->
            Log.d("UGH", "Selected")
            /*val nextFrag = MyEditFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.layoutMyPrincipal, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit()*/
        }

        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recycleMY.layoutManager =  layoutManager
        this.adapter = context?.let { MyAdapter(it, DataCards.comida) }
        recycleMY.adapter = this.adapter

    }




}