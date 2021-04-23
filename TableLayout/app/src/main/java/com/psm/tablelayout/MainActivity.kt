package com.psm.tablelayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    //Que son las variables lazy

    private val adapter by lazy{ ViewPagerAdapater(this)}

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
                            tab.setIcon(R.drawable.ico_fav)
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
}
