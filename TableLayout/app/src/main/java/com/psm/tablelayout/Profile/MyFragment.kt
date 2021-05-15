package com.psm.tablelayout.Profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.R
import com.psm.tablelayout.ViewPagerAdapater
import kotlinx.android.synthetic.main.my_principal.*
import kotlinx.android.synthetic.main.my_principal.view.*

/*https://stackoverflow.com/questions/32700818/how-to-open-a-fragment-on-button-click-from-a-fragment-in-android*/
/*https://stackoverflow.com/questions/21028786/how-do-i-open-a-new-fragment-from-another-fragment*/
class MyFragment : Fragment(), View.OnClickListener {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter:MyAdapter? = null
    private var listener: onFragmentActionsListener?=null;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.my_principal, container, false)
        val btnEdit = view.findViewById<Button>(R.id.btnEditProfile)
        btnEdit.setOnClickListener(this)
    /*view.btnEditProfile.setOnClickListener { view ->


    val nextFrag =yEditFragment()
    activity!!.supportFragmentManager.beginTransaction()
        .replace(R.id.layoutMyPrincipal, nextFrag, "findThisFragment")
        .addToBackStack(null)
        .commit()



    }*/

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is onFragmentActionsListener)
        {
            listener = context;
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener=null;
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
    super.onViewCreated(itemView, savedInstanceState)

    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    recycleMY.layoutManager =  layoutManager
    this.adapter = context?.let { MyAdapter(it, DataCards.comida) }
    recycleMY.adapter = this.adapter

    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnEditProfile->this.listener?.onClickFragmentMy((R.id.btnEditProfile))
        }
    }


}