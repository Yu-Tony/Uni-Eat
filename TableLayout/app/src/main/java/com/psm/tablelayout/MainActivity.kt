package com.psm.tablelayout

//import com.psm.tablelayout.Profile.MyEdit
//import com.psm.tablelayout.Profile.onFragmentActionsListener
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.psm.tablelayout.AddCard.AddCardActivity
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.Profile.MyEdit
import com.psm.tablelayout.Profile.SaveSharedPreference
import com.psm.tablelayout.Search.SearchActivity


//class MainActivity : AppCompatActivity(), onFragmentActionsListener {
class MainActivity : AppCompatActivity() {

    var loggedin = false;
    var LAUNCH_SECOND_ACTIVITY = 1;



    private val adapter by lazy{ ViewPagerAdapater(this, this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       /* lifecycleScope.launch {
            val FacultadesIntern = app.room.facultadesLocalDAO().getAll()
        }*/



        //DataCards.resenas
        //DataCards.facultad
        //DataCards.categorias



        if(SaveSharedPreference.getUserName(this)?.length  == 0)
        {

            setContentView(R.layout.start)

            val btnStart = findViewById<Button>(R.id.btnStart1)
            btnStart.setOnClickListener {
                setContentView(R.layout.start2)

                val btnLogStart = findViewById<Button>(R.id.start2Login)
                btnLogStart.setOnClickListener {

                    val intentAdd = Intent(this, LoginActivity::class.java);
                    startActivityForResult(intentAdd, LAUNCH_SECOND_ACTIVITY);


                }

                val btnSignStart = findViewById<Button>(R.id.start2Signup)
                btnSignStart.setOnClickListener {

                    val intentAdd = Intent(this, SignUpActivity::class.java);
                    startActivityForResult(intentAdd, LAUNCH_SECOND_ACTIVITY)


                }
            }
        }
        else
        {



            DataMY.perfil?.userMail = SaveSharedPreference.getUserName(this)
            setContentView(R.layout.activity_main)
            val pagerMain =  findViewById<ViewPager2>(R.id.pager)
            pagerMain.adapter =  this.adapter

            pagerMain.setUserInputEnabled(false);

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

            val btnAdd = findViewById<FloatingActionButton>(R.id.floating_action_button)
            btnAdd.setOnClickListener {
                val intentAdd = Intent(this, AddCardActivity::class.java);
                startActivity(intentAdd);
            }

            val btnSearch = findViewById<FloatingActionButton>(R.id.floating_action_button2)
            btnSearch.setOnClickListener {
                val intentSearch = Intent(this, SearchActivity::class.java);
                startActivity(intentSearch);
            }
        }



    }


     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         Log.d("gotta", "hum")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data?.getStringExtra("result")

                if (result =="logged") {
                    Log.d("gotta", result)
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

                    val btnAdd = findViewById<FloatingActionButton>(R.id.floating_action_button)
                    btnAdd.setOnClickListener {
                        val intentAdd = Intent(this, AddCardActivity::class.java);
                        startActivity(intentAdd);
                    }

                    val btnSearch = findViewById<FloatingActionButton>(R.id.floating_action_button2)
                    btnSearch.setOnClickListener {



                                val intentSearch = Intent(this, SearchActivity::class.java);
                                startActivity(intentSearch);



                    }
                };
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult


   /* private fun replaceFragment(fragment:Fragment)
    {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutMyPrincipal, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }*/

    fun onClickFragmentMy(intId:Int) {
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
