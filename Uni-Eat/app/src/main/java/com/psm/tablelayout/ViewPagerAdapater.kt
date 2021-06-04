package com.psm.tablelayout

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.psm.tablelayout.CardsLong.CardFragment
import com.psm.tablelayout.CardsLong.CardHomeFragment
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.Profile.MyFragment

//import com.psm.tablelayout.Profile.MyEditFragment
//import com.psm.tablelayout.Profile.MyFragment


class ViewPagerAdapater(fragment: FragmentActivity, private val context: Context) : FragmentStateAdapter(fragment)  {

    override fun getItemCount(): Int  = 4

    override fun createFragment(position: Int): Fragment {


        val fragment1 = CardHomeFragment()
        val fragment2 =  CardFragment()
        val fragment3 =  MyFragment()
        val fragment =  DemoObjectFragment()


        when(position){
            0 ->
            {
                return fragment1
            }
            1 ->
            {
                return fragment
            }
            2 ->
            {

                return fragment3

            }
            3 ->
            {
                return fragment2

            }


        }

        return fragment1
    }


}