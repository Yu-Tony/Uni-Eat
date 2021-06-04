package com.psm.tablelayout.Profile

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.psm.recyclerview.Utilities.ImageUtilities
import com.psm.tablelayout.CardsLong.CardsAdapterAll
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.CardsLong.Resena
import com.psm.tablelayout.LocalData.Perfil.PerfilLocal
import com.psm.tablelayout.LocalData.Perfil.PerfilViewModel
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.my_principal.*
import kotlinx.android.synthetic.main.my_principal.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/*https://stackoverflow.com/questions/32700818/how-to-open-a-fragment-on-button-click-from-a-fragment-in-android*/
/*https://stackoverflow.com/questions/21028786/how-do-i-open-a-new-fragment-from-another-fragment*/
class MyFragment : Fragment(), View.OnClickListener {


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapterMY:MyAdapter? = null
    private var listener: onFragmentActionsListener?=null;
    private lateinit var imageView: ImageView
///////////////////////////////
    private lateinit var mUserViewModel: PerfilViewModel

//////////////////////////////////
    var refreshLayout: SwipeRefreshLayout? = null

    /////////////////////////

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.my_principal, container, false)

        val btnEdit = view.findViewById<Button>(R.id.btnEditProfile)
        btnEdit.setOnClickListener(this)


        mUserViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)

        //usernameMy
        view.btnEditProfile.setOnClickListener { view ->

            val intent = Intent(activity, MyEdit::class.java)
            startActivity(intent)

        }

        view.btnLogOutMy.setOnClickListener{ view ->

            SaveSharedPreference.setUserName(getActivity(),
               ""
            )


            System.exit(0);
        }

        //https://www.devguru.com/content/features/articles/android/swipe_to_refresh_layout.html
        refreshLayout = view.findViewById<View>(R.id.swipeMy) as SwipeRefreshLayout

        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connMgr.activeNetworkInfo
        //Log.e("ONRESUME", "MY")
        if (networkInfo != null && networkInfo.isConnected) {

            val busqueda: String? = SaveSharedPreference.getUserName(getActivity())

            //val busqueda:String = DataMY.perfil?.userMail.toString()
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Perfil>>? = busqueda?.let { service.getUser(it) }

            if (result != null) {
                result.enqueue(object: Callback<List<Perfil>> {
                    override fun onFailure(call: Call<List<Perfil>>, t: Throwable) {
                        Toast.makeText(getActivity(),"Error al obtener el usuario",Toast.LENGTH_SHORT).show();
                    }

                    override fun onResponse(call: Call<List<Perfil>>, response: Response<List<Perfil>>) {

                        val item =  response.body()

                        if (item != null){

                            var AllUsersInDB = mUserViewModel.readAllData
                            if(AllUsersInDB!=null)
                            {
                                mUserViewModel.deleteAllUsers()
                                mUserViewModel.deleteAllTableUsers()
                            }

                            val responseBody: List<Perfil>? = response.body()
                            if (!responseBody!!.isEmpty()) {
                                llProgressBarMy.visibility = View.VISIBLE
                                var strMessage:String =  ""
                                strMessage =   item[0].userPassword.toString()

                                val returnIntent = Intent()

                                DataMY.initializePerfil(item[0].userID,
                                    item[0].userNombre,
                                    item[0].userApellidos,
                                    item[0].userMail,
                                    item[0].userPassword,
                                    item[0].userPhone,
                                    item[0].userImage)

                                val profile =
                                    PerfilLocal(
                                        null,
                                        item[0].userNombre,
                                        item[0].userApellidos,
                                        item[0].userMail,
                                        item[0].userPassword,
                                        item[0].userPhone,
                                        item[0].userImage
                                    )



                                mUserViewModel.insert(profile)

                                /*SaveSharedPreference.setUserName(this@SignUpActivity,
                                        DataMY.perfil?.userNombre
                                    )*/

                                getresenasMine()



                            } else {
                                Toast.makeText(getActivity(),"El usuario no existe",Toast.LENGTH_SHORT).show();
                            }

                        }


                    }

                })
            }

        }
        else {
            mUserViewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)
            mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { perfil->


                if(perfil!=null)
                {
                    Toast.makeText(getActivity(),"Getting from database",Toast.LENGTH_SHORT).show();
                    DataMY.initializePerfil(perfil[0].userID,
                        perfil[0].userNombre,
                        perfil[0].userApellidos,
                        perfil[0].userMail,
                        perfil[0].userPassword,
                        perfil[0].userPhone,
                        perfil[0].userImage
                    )
                }


            })

            showData();
        }

        return view
    }


    override fun onPause() {
        super.onPause()
        //Log.e("hot sauce", "pause")


    }

    override fun onResume() {
        super.onResume()


        //Log.e("hot sauce", "resume")
    }



    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
    super.onViewCreated(itemView, savedInstanceState)

    val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    recycleMY.layoutManager =  layoutManager
    this.adapterMY = context?.let { MyAdapter(it, DataMY.resenasMine) }
    recycleMY.adapter = this.adapterMY



        refreshLayout!!.setOnRefreshListener { refreshApp() }




    }


    override fun onClick(v: View?) {
       /* when(v!!.id)
        {
            R.id.btnEditProfile->this.listener?.onClickFragmentMy((R.id.btnEditProfile))

        }*/
    }


    private fun refreshApp() {

        getProfile();

    }

    fun getProfile()
    {
        val connMgr = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connMgr.activeNetworkInfo
        //Log.e("ONRESUME", "MY")
        if (networkInfo != null && networkInfo.isConnected) {

            val busqueda: String? = SaveSharedPreference.getUserName(getActivity())

            //val busqueda:String = DataMY.perfil?.userMail.toString()
            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Perfil>>? = busqueda?.let { service.getUser(it) }

            if (result != null) {
                result.enqueue(object: Callback<List<Perfil>> {
                    override fun onFailure(call: Call<List<Perfil>>, t: Throwable) {
                        Toast.makeText(getActivity(),"Error al obtener el usuario",Toast.LENGTH_SHORT).show();
                    }

                    override fun onResponse(call: Call<List<Perfil>>, response: Response<List<Perfil>>) {

                        val item =  response.body()

                        if (item != null){

                            var AllUsersInDB = mUserViewModel.readAllData
                            if(AllUsersInDB!=null)
                            {
                                mUserViewModel.deleteAllUsers()
                            }

                            val responseBody: List<Perfil>? = response.body()
                            if (!responseBody!!.isEmpty()) {
                                var strMessage:String =  ""
                                strMessage =   item[0].userPassword.toString()

                                val returnIntent = Intent()

                                DataMY.initializePerfil(item[0].userID,
                                    item[0].userNombre,
                                    item[0].userApellidos,
                                    item[0].userMail,
                                    item[0].userPassword,
                                    item[0].userPhone,
                                    item[0].userImage)

                                val profile =
                                    PerfilLocal(
                                        null,
                                        item[0].userNombre,
                                        item[0].userApellidos,
                                        item[0].userMail,
                                        item[0].userPassword,
                                        item[0].userPhone,
                                        item[0].userImage
                                    )



                                mUserViewModel.insert(profile)

                                /*SaveSharedPreference.setUserName(this@SignUpActivity,
                                        DataMY.perfil?.userNombre
                                    )*/

                                //showData();
                                getresenasMine()



                            } else {
                                Toast.makeText(getActivity(),"El usuario no existe",Toast.LENGTH_SHORT).show();
                            }

                        }


                    }

                })
            }

        }
        else {
            Toast.makeText(getActivity(),"No hay conexion a internet",Toast.LENGTH_SHORT).show();
            refreshLayout?.setRefreshing(false);
        }

    }

        fun showData()
    {
        val view = view
        if (view != null) {
            view.isFocusableInTouchMode = true
            view.requestFocus()
        }

        if (view != null) {
            val textView: TextView = view.findViewById<Button>(R.id.usernameMy)
            textView.text = (DataMY.perfil?.userNombre + " " + DataMY.perfil?.userApellidos)

            imageView = view.findViewById(R.id.pictureMy)
        }

        if(DataMY.perfil?.imgArray == null){
            //holder.ImageCard.setImageResource(categorias.categoriaImage!!)
        }
        else{
            imageView.setImageBitmap(ImageUtilities.getBitMapFromByteArray(DataMY.perfil?.imgArray!!))
            //imageProfile.setImageBitmap(ImageUtilities.getBitMapFromByteArray(DataMY.perfil[0].imgArray!!))
        }

        //getresenasMine()


    }

    fun getresenasMine()
    {
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultResenas: Call<List<Resena>> = service.getResenas()

        resultResenas!!.enqueue(object: Callback<List<Resena>> {

            override fun onFailure(call: Call<List<Resena>>, t: Throwable){
                Log.e("getres", "error")
            }

            override fun onResponse(call: Call<List<Resena>>, response: Response<List<Resena>>){
                Log.e("getres", "success")
                val arrayItems =  response.body()
                if (arrayItems != null){
                    for (item in arrayItems!!){

                        if((item.resenaPublicado!=0) && (item.resenaUsuario == DataMY.perfil?.userMail))
                        {
                            var byteArray1:ByteArray? = null
                            var byteArray2:ByteArray? = null
                            var byteArray3:ByteArray? = null
                            var byteArray4:ByteArray? = null
                            var byteArray5:ByteArray? = null

                            if(item.resenaImageOne!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray1 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageTwo!=null)
                            {
                                val strImage:String =  item.resenaImageTwo!!.replace("data:image/png;base64,","")
                                byteArray2 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageThree!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray3 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageFour!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray4 =  Base64.getDecoder().decode(strImage)
                            }
                            if(item.resenaImageFive!=null)
                            {
                                val strImage:String =  item.resenaImageOne!!.replace("data:image/png;base64,","")
                                byteArray5 =  Base64.getDecoder().decode(strImage)
                            }

                            var  res = Resena(item.resenaID,item.resenaUsuario,item.resenaTitulo,item.resenaCategoria,item.resenaFacultad,
                                item.resenaDescription,item.resenaRate,item.resenaPublicado,
                                item.resenaImageOne, item.resenaImageTwo,item.resenaImageThree,
                                item.resenaImageFour,item.resenaImageFive,byteArray1 ,byteArray2,
                                byteArray3,byteArray4,byteArray5)

                            DataMY.resenasMine.add(res)
                        }

                    }

                    Handler().postDelayed(
                        {
                            adapterMY?.setData(DataMY.resenasMine)
                            adapterMY?.notifyDataSetChanged()
                            refreshLayout?.setRefreshing(false);
                            showData();
                            llProgressBarMy.visibility = View.GONE
                        },
                        4000 // value in milliseconds
                    )
                }
            }
        })
    }


}



