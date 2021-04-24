package com.psm.tablelayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.psm.tablelayout.CardsSearch.CardFragment
import com.psm.tablelayout.CardsSearch.CardHomeFragment
import com.psm.tablelayout.Profile.MyFragment

//Extiende de FragmentStateAdapter

class ViewPagerAdapater(fragment: FragmentActivity) : FragmentStateAdapter(fragment)  {

    //Constante a nivel de clase
    companion object{
        private  const val ARG_OBJECT = "object"
    }

    //cuantos fragments va a tener el swipe
    override fun getItemCount(): Int  = 4

    override fun createFragment(position: Int): Fragment {
        //Vamos a crear el fragmente
        val fragment1 = CardHomeFragment()
        val fragment2 =  CardFragment()
        val fragment3 =  MyFragment()
        val fragment =  DemoObjectFragment()



        //Tenemos 2 formas de pasar información a ese fragment
        //Una pasar los datos por medio de un constructor que no es recomendable
        //La segunda usando los arguments, setar argumentos al adaptador que vamos a
        //mandar a cada instancia


/**/
        //En caso de que lo fragments sean diferentes
        //usaremos un when
        when(position){
            0 ->
            {
                return fragment1
            }
            1 ->
            {
                return fragment2
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