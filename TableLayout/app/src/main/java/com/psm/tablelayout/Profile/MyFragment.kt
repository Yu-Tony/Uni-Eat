package com.psm.tablelayout.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psm.tablelayout.CardsSearch.DataCards
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.my_principal.*


class MyFragment : Fragment(), View.OnClickListener  {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter:MyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_principal, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recycleMY.layoutManager =  layoutManager
        this.adapter = context?.let { MyAdapter(it, DataCards.comida) }
        recycleMY.adapter = this.adapter
    }

    override fun onClick(v: View?) {
        var fragment: Fragment? = null
        when (view!!.id) {
            R.id.btnEditProfile -> {
                
                fragment = MyEditFragment()
                replaceFragment(fragment)
            }
        }
    }

    fun replaceFragment(someFragment: Fragment?) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        if (someFragment != null) {
            transaction.replace(R.id.recycleMY, someFragment)
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

}