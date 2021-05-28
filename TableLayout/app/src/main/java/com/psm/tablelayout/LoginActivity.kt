package com.psm.tablelayout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.Profile.SaveSharedPreference
import kotlinx.android.synthetic.main.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.InetAddress

class LoginActivity: AppCompatActivity(), View.OnClickListener {

    val EXTRA_TEXT_ADD = "logged"

    ////////////////////////////////////

    var builderIniciar: AlertDialog.Builder? = null
    var dialogIniciar: AlertDialog? = null

    var builderPass: AlertDialog.Builder? = null
    var dialogPass: AlertDialog? = null



//    val app = applicationContext as PerfilApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btnLogIn =  findViewById<Button>(R.id.btnLogin)
        btnLogIn.setOnClickListener(this)


        /*val button = findViewById<Button>(R.id.btnLogin)
        button.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("result", EXTRA_TEXT_ADD)
            setResult(Activity.RESULT_OK, returnIntent)
            finish();
        }*/

       /* lifecycleScope.launch{
            val people = app.room.perfilDAO().getAll()
        }*/

        builderIniciar = AlertDialog.Builder(this)
        dialogIniciar = builderIniciar!!.setTitle("Error")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("El usuario no existe")
            .setPositiveButton("Cerrar") {
                    dialogIniciar, which -> dialogIniciar.dismiss()
            }
            .create()

        builderPass = AlertDialog.Builder(this)
        dialogPass = builderPass!!.setTitle("Error")
        .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("La contraseña no coincide")
            .setPositiveButton("Cerrar") {
                    dialogPass, which -> dialogPass.dismiss()
            }
            .create()

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnLogin-> checkUser()
        }
    }

    private fun checkUser()
    {
        val intId:String =  LoginMail!!.text.toString()
        val pass:String =  LoginPass!!.text.toString()

        val ConnectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true) {
            if(intId!="")
            {
                if(pass!="")
                {
                    val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                    val result: Call<List<Perfil>> = service.getUser(intId)

                    result.enqueue(object: Callback<List<Perfil>>
                    {
                        override fun onFailure(call: Call<List<Perfil>>, t: Throwable) {
                            Toast.makeText(this@LoginActivity,"Ocurrió un error favor de intentar de nuevo", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<List<Perfil>>, response: Response<List<Perfil>>) {

                            var strMessage:String =  ""
                            var byteArray:ByteArray? = null
                            val item =  response.body()

                            if (item != null){
                                val responseBody: List<Perfil>? = response.body()
                                if (!responseBody!!.isEmpty()) {
                                    var strMessage:String =  ""
                                    strMessage =   item[0].userPassword.toString()
                                    if(pass==strMessage)
                                    {
                                        val returnIntent = Intent()


                                        DataMY.initializePerfil(item[0].userID,
                                            item[0].userNombre,
                                            item[0].userApellidos,
                                            item[0].userMail,
                                            item[0].userPassword,
                                            item[0].userPhone,
                                            item[0].userImage)

                                        //https://stackoverflow.com/questions/45213706/kotlin-wait-function
                                        DataMY.getresenasDrafts()

                                        SaveSharedPreference.setUserName(this@LoginActivity,
                                            item[0].userNombre
                                        )

                                        Toast.makeText(this@LoginActivity,"Cargando...", Toast.LENGTH_LONG).show()
                                        Handler().postDelayed(
                                            {
                                                returnIntent.putExtra("result", EXTRA_TEXT_ADD)
                                                setResult(Activity.RESULT_OK, returnIntent)
                                                finish();
                                            },
                                            5000 // value in milliseconds
                                        )

                                        //Toast.makeText(this@LoginActivity, DataMY.perfil[0].userNombre, Toast.LENGTH_LONG).show()

                                    }
                                    else
                                    {
                                        dialogPass?.show()
                                    }

                                } else {
                                    dialogIniciar?.show()
                                }

                            }



                        }

                    })



                }
                else
                {
                    Toast.makeText(this@LoginActivity,"El campo Contraseña no debe estar vacío", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this@LoginActivity,"El campo Correo no debe estar vacío", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val dialog: AlertDialog = builder.setTitle("Alerta de conexión a internet")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Favor de revisar tu conexión a internet")
                .setPositiveButton("Cerrar") {
                        dialog, which -> dialog.dismiss()
                }
                .create()
            dialog.show()
        }


    }




}