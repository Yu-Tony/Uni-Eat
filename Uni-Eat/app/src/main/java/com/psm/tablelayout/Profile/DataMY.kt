package com.psm.tablelayout.Profile

import android.os.Handler
import android.util.Log
import android.view.View
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.CardsLong.Resena
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

object DataMY {
    var perfil:Perfil? = null
    val resenasMine = ArrayList<Resena>()
    var resenasDrafts = ArrayList<Resena>()
    val resenasFav = ArrayList<Resena>()



    init {
        //this.initializeFacultad()
        //this.initializeCategorias()
        // this.initializeComidas()
        //this.getresenasMine()
        //this.getresenasDrafts()
        //this.getresenasFav()
    }


    fun initializePerfil(userID:Int?,
                                 userNombre:String?,
                                 userApellidos:String?,
                                 userMail:String?,
                                 userPassword:String?,
                                 userPhone:String? =  null,
                                 userImage:String?)
    {

        val strImage:String =  userImage!!.replace("data:image/png;base64,","")
        var byteArray:ByteArray? = null
        byteArray =  Base64.getDecoder().decode(strImage)


        var  profile = Perfil(userID, userNombre,userApellidos,userMail,userPassword,userPhone,userImage,byteArray)
        perfil =profile





       /* perfil[0].userID = profile.userID
        perfil[0].userNombre = profile.userNombre
        perfil[0].userApellidos = profile.userApellidos
        perfil[0].userMail = profile.userMail
        perfil[0].userPassword = profile.userPassword
        perfil[0].userPhone = profile.userPhone
        perfil[0].userImage = profile.userImage
        perfil[0].imgArray = profile.imgArray*/


    }

    fun getresenasDrafts()
    {

        DataMY.resenasDrafts.clear()

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultResenas: Call<List<Resena>> = service.getResenas()

        resultResenas!!.enqueue(object: Callback<List<Resena>> {

            override fun onFailure(call: Call<List<Resena>>, t: Throwable){
                Log.e("getres", "error")
            }

            override fun onResponse(call: Call<List<Resena>>, response: Response<List<Resena>>){

                val arrayItems =  response.body()
                if (arrayItems != null){
                    for (item in arrayItems!!){

                        if((item.resenaPublicado==0) && (item.resenaUsuario== DataMY.perfil?.userMail))
                        {
                            DataMY.perfil?.userMail?.let { Log.e("bestie", it) }
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

                            DataMY.resenasDrafts.add(res)


                        }



                    }


                }
            }
        })
    }


}