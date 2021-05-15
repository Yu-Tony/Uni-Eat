package com.psm.tablelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.psm.tablelayout.Profile.MyEdit
import com.psm.tablelayout.Profile.MyEditFragment
import com.psm.tablelayout.Profile.onFragmentActionsListener

class MainActivity : AppCompatActivity(), onFragmentActionsListener {

    //Que son las variables lazy

    private val adapter by lazy{ ViewPagerAdapater(this, this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerMain =  findViewById<ViewPager2>(R.id.pager)
        pagerMain.adapter =  this.adapter

        val tab_layoutMain =  findViewById<TabLayout>(R.id.tab_layout)

        //Aqui ya sabe quien es nuestro tab, y quien nuestro pager
        val tabLayoutMediator =  TabLayoutMediator(tab_layoutMain,pagerMain
            , TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when(position){
                    0-> {
                            tab.text =  "Home"
                            tab.setIcon(R.drawable.ico_home)
                        }
                    1-> {
                            tab.text =  "Favorite"
                            tab.setIcon(R.drawable.ico_favoritos)
                        }
                    2-> {
                            tab.text =  "MY"
                            tab.setIcon(R.drawable.ico_person)
                        }
                    3-> {
                        tab.text =  "Drafts"
                        tab.setIcon(R.drawable.ico_draw)
                    }

                }
            })
        tabLayoutMediator.attach()

    }

    private fun replaceFragment(fragment:Fragment)
    {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutMyPrincipal, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickFragmentMy(intId:Int) {
       when(intId)
       {
           R.id.btnEditProfile->{
               Log.e("Wayv", "tbt")

               /*val fragmentTransaction=supportFragmentManager.beginTransaction()
               fragmentTransaction.replace(R.id.layoutMyPrincipal, MyEditFragment())
               fragmentTransaction.addToBackStack(null)
               fragmentTransaction.commit()
              //this.replaceFragment(MyEditFragment())*/

               val intent = Intent(this, MyEdit::class.java)
               startActivity(intent)
           }
       }
    }


}
