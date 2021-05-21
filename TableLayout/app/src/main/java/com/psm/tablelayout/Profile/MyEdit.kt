package com.psm.tablelayout.Profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.my_editar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*


class MyEdit:  AppCompatActivity(), View.OnClickListener {

    private val IMAGE_PICK_CODE = 1000;
    var imgArray:ByteArray? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_editar)

        //
        val btnSubirFoto =  findViewById<Button>(R.id.btnCambiarImagenPerfil)
        btnSubirFoto.setOnClickListener(this)

        val btnGuardarPerfil =  findViewById<Button>(R.id.btnGuardarProfile)
        btnGuardarPerfil.setOnClickListener(this)

        val btnCancelProfile =  findViewById<Button>(R.id.btnCancelProfile)
        btnCancelProfile.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.btnGuardarProfile-> guardarPerfil()
            R.id.btnCancelProfile-> close()
            R.id.btnCambiarImagenPerfil->uploadImage()
        }
    }

    private fun uploadImage(){

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE) // GIVE AN INTEGER VALUE FOR IMAGE_PICK_CODE LIKE 1000

        /*val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)*/
    }

    override fun onActivityResult(requestcode: Int, resultcode: Int, data: Intent?) {
        super.onActivityResult(requestcode, resultcode, data)

        if (resultcode == Activity.RESULT_OK) {
            //RESPUESTA DE LA CÁMARA CON TIENE LA IMAGEN
            if (requestcode == IMAGE_PICK_CODE) {


                val pickedImage: Uri? = data?.data
                val photo = MediaStore.Images.Media.getBitmap(this.contentResolver, pickedImage)

                // val photo =  data?.extras?.get("data") as Bitmap
                val stream = ByteArrayOutputStream()
                //Bitmap.CompressFormat agregar el formato desado, estoy usando aqui jpeg
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                //Agregamos al objecto album el arreglo de bytes
                imgArray =  stream.toByteArray()
                //Mostramos la imagen en la vista
                this.imageEditProfile!!.setImageBitmap(photo)

                val bitmap = (imageEditProfile!!.getDrawable() as BitmapDrawable).bitmap
            }

        }
    }


    private fun close()
    {
        finish();
    }


    private fun guardarPerfil()
    {
        val intId:String =  editTextPassword!!.text.toString()


        val user:Perfil?=null

        if(intId=="" && imgArray==null)
        {
            Toast.makeText(this@MyEdit,"No se realizó ningún cambio",Toast.LENGTH_LONG).show()
        }
        else
        {
            if(intId!="" && imgArray!=null)
            {
                Toast.makeText(this@MyEdit,DataMY.perfil[0].userID.toString(),Toast.LENGTH_LONG).show()
                var passTemp=DataMY.perfil[0].userPassword
                DataMY.perfil[0].userPassword = intId

                val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)
                val strEncodeImage:String = "data:image/png;base64," + encodedString

                DataMY.perfil[0].userImage=strEncodeImage

                //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
                // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
                val user =   Perfil(DataMY.perfil[0].userID,
                    DataMY.perfil[0].userNombre,
                    DataMY.perfil[0].userApellidos,
                    DataMY.perfil[0].userMail,
                    DataMY.perfil[0].userPassword,
                    DataMY.perfil[0].userPhone,
                    DataMY.perfil[0].userImage)

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<String> = service.updateUser(user)

                result.enqueue(object: Callback<String>{
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@MyEdit,"Error" + DataMY.perfil[0].userPassword,Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        //Toast.makeText(this@SignUpActivity,"OK",Toast.LENGTH_LONG).show()
                        Toast.makeText(this@MyEdit,"Usuario cambiado" + DataMY.perfil[0].userPassword, Toast.LENGTH_LONG).show()


                    }
                })

            }
            else
            {
                if(intId!="")
                {
                    Toast.makeText(this@MyEdit,DataMY.perfil[0].userID.toString(),Toast.LENGTH_LONG).show()
                    var passTemp=DataMY.perfil[0].userPassword
                    DataMY.perfil[0].userPassword = intId
                    //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
                    // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
                    val user =   Perfil(DataMY.perfil[0].userID,
                        DataMY.perfil[0].userNombre,
                        DataMY.perfil[0].userApellidos,
                        DataMY.perfil[0].userMail,
                        DataMY.perfil[0].userPassword,
                        DataMY.perfil[0].userPhone,
                        DataMY.perfil[0].userImage)

                    val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                    val result: Call<String> = service.updateUser(user)

                    result.enqueue(object: Callback<String>{
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(this@MyEdit,"Error" + DataMY.perfil[0].userPassword,Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            //Toast.makeText(this@SignUpActivity,"OK",Toast.LENGTH_LONG).show()
                            Toast.makeText(this@MyEdit,"Usuario cambiado" + DataMY.perfil[0].userPassword, Toast.LENGTH_LONG).show()


                        }
                    })
                }


                if(imgArray!=null)
                {
                    Toast.makeText(this@MyEdit,DataMY.perfil[0].userID.toString(),Toast.LENGTH_LONG).show()

                    val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)
                    val strEncodeImage:String = "data:image/png;base64," + encodedString

                    DataMY.perfil[0].userImage=strEncodeImage
                    //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
                    // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
                    val user =   Perfil(DataMY.perfil[0].userID,
                        DataMY.perfil[0].userNombre,
                        DataMY.perfil[0].userApellidos,
                        DataMY.perfil[0].userMail,
                        DataMY.perfil[0].userPassword,
                        DataMY.perfil[0].userPhone,
                        DataMY.perfil[0].userImage)

                    val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                    val result: Call<String> = service.updateUser(user)

                    result.enqueue(object: Callback<String>{
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Toast.makeText(this@MyEdit,"Error",Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            //Toast.makeText(this@SignUpActivity,"OK",Toast.LENGTH_LONG).show()
                            Toast.makeText(this@MyEdit,"Usuario cambiado", Toast.LENGTH_LONG).show()


                        }
                    })
                }
            }

        }







    }


}