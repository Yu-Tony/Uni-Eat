package com.psm.tablelayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.Profile.DataMY
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity: AppCompatActivity(), View.OnClickListener {

    val EXTRA_TEXT_ADD = "logged"


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
        if(intId!="")
        {
            if(pass!="")
            {
                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<List<Perfil>> = service.getUser(intId)



                result.enqueue(object: Callback<List<Perfil>> {
                    override fun onFailure(call: Call<List<Perfil>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity,"Error", Toast.LENGTH_LONG).show()
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
                                    Toast.makeText(this@LoginActivity,"La contraseña no coincide", Toast.LENGTH_LONG).show()
                                }

                            } else {
                                Toast.makeText(this@LoginActivity,"El usuario no existe", Toast.LENGTH_LONG).show()
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


}