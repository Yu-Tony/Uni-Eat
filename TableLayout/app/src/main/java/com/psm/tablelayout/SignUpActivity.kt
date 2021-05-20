package com.psm.tablelayout

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Perfil
import kotlinx.android.synthetic.main.signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern


class SignUpActivity:AppCompatActivity(), View.OnClickListener {

    val EXTRA_TEXT_ADD = "logged"
    private val IMAGE_PICK_CODE = 1000;
    var imgArray:ByteArray? =  null
    var imageSignUp: ImageView? =  null
    var errorUser:Int=0

    //////////////////////////////////////
    var passUsuario: String? =null
    var phoneUsuario:String?=null
    var emailUsuario:String?=null
    var apellidoUsuario:String?=null
    var nombreUsuario:String?=null
    //////////////////////////////////////

    //^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[a-z])" +  // min
                "(?=.*[A-Z])" +  // mayus
                "(?=.*\\d)" +  // digit
                "[a-zA-Z\\d]" +  // matches
                ".{8,}" +  // at least 8 characters
                "$"
    )
    ///////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        val btnSubirFoto =  findViewById<Button>(R.id.btnSignUpFoto)
        btnSubirFoto.setOnClickListener(this)

        val btnCreateUser =  findViewById<Button>(R.id.btnSignUp)
        btnCreateUser.setOnClickListener(this)

        imageSignUp =  findViewById(R.id.imageSignUp)

        /*val button = findViewById<Button>(R.id.btnSignUp)
        button.setOnClickListener {

           /* val returnIntent = Intent()
            returnIntent.putExtra("result", EXTRA_TEXT_ADD)
            setResult(Activity.RESULT_OK, returnIntent)
            finish();*/


           /* val returnIntent = Intent()
            returnIntent.putExtra("result", "logged")
            setResult(Activity.RESULT_OK, returnIntent)*/

        }*/
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnSignUpFoto-> uploadImage()
            R.id.btnSignUp-> checkUser()
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
                this.imageSignUp!!.setImageBitmap(photo)

                val bitmap = (imageSignUp!!.getDrawable() as BitmapDrawable).bitmap
            }

        }
    }



    private fun checkEmail (){

        val intId:String =  editTextTextEmailAddress!!.text.toString()

        if(intId!="")
        {

            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<List<Perfil>> = service.getUser(intId)



            result.enqueue(object: Callback<List<Perfil>> {
                override fun onFailure(call: Call<List<Perfil>>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity,"Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<Perfil>>, response: Response<List<Perfil>>) {


                    var strMessage:String =  ""
                    var byteArray:ByteArray? = null
                    val item =  response.body()

                    if (item != null){
                        val responseBody: List<Perfil>? = response.body()
                        if (!responseBody!!.isEmpty()) {
                            Toast.makeText(this@SignUpActivity,"Ya existe un usuario con este correo", Toast.LENGTH_LONG).show()
                        } else {
                            //Toast.makeText(this@SignUpActivity,"Creando uno nuevo", Toast.LENGTH_LONG).show()
                            createUser()
                        }

                    }



                }

            })
        }



    }


    private fun createUser()
    {
        val encodedString:String =  Base64.getEncoder().encodeToString(this.imgArray)
        val strEncodeImage:String = "data:image/png;base64," + encodedString

        //SE CONSTRUYE EL OBJECTO A ENVIAR,  ESTO DEPENDE DE COMO CONSTRUYAS EL SERVICIO
        // SI TU SERVICIO POST REQUIERE DOS PARAMETROS HACER UN OBJECTO CON ESOS DOS PARAMETROS
        val user =   Perfil(0,
            nombreUsuario,
            apellidoUsuario,
            emailUsuario,
            passUsuario,
            phoneUsuario,
            strEncodeImage)

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.saveUser(user)

        result.enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(this@SignUpActivity,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                //Toast.makeText(this@SignUpActivity,"OK",Toast.LENGTH_LONG).show()
                Toast.makeText(this@SignUpActivity,"Usuario creado", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun checkUser(){

        /*--------------------------------NOMBRE CHECK-----------------------------------------*/
        if(editTextTextPersonName!!.text.toString() != "")
        {
            errorUser=0
            nombreUsuario =  editTextTextPersonName!!.text.toString()

            /*-----------------------------APELLIDO CHECK---------------------------*/
            if(editTextTextPersonName2!!.text.toString() != "")
            {
                errorUser=0
                apellidoUsuario =  editTextTextPersonName2!!.text.toString()

                /*-----------------------------EMAIL CHECK---------------------------*/
                if(editTextTextEmailAddress!!.text.toString() != "")
                {
                    errorUser=0
                    emailUsuario =  editTextTextEmailAddress!!.text.toString()

                    /*-----------------------------PASSWORD CHECK---------------------------*/
                    if(editTextTextPassword!!.text.toString() != "")
                    {
                        if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
                            password.setError("Password is too weak");
                            return false;
                        }
                        else
                        {
                            
                        }

                        passUsuario =  editTextTextPassword!!.text.toString()
                        errorUser=0

                        /*-----------------------------IMAGEN CHECK---------------------------*/
                        if(imgArray!=null)
                        {
                            /*-----------------------------PHONE CHECK---------------------------*/
                            if(editTextTextPhone!!.text.toString() != "")
                            {
                                errorUser=0
                                phoneUsuario=  editTextTextPhone!!.text.toString()
                            }
                            errorUser=0
                           // Toast.makeText(this@SignUpActivity,"Todo todo bien", Toast.LENGTH_LONG).show()

                            checkEmail()

                        }
                        else
                        {
                            errorUser=1
                            Toast.makeText(this@SignUpActivity,"Favor de agregar una imagen", Toast.LENGTH_LONG).show()
                        }

                    }
                    else
                    {
                        errorUser=1
                        Toast.makeText(this@SignUpActivity,"El campo Contraseña no puede estar vacío", Toast.LENGTH_LONG).show()
                    }

                }
                else
                {
                    errorUser=1
                    Toast.makeText(this@SignUpActivity,"El campo Correo no puede estar vacío", Toast.LENGTH_LONG).show()
                }

            }
            else
            {
                errorUser=1
                Toast.makeText(this@SignUpActivity,"El campo Apellido no puede estar vacío", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            errorUser=1
            Toast.makeText(this@SignUpActivity,"El campo Nombre no puede estar vacío", Toast.LENGTH_LONG).show()
        }










    }

}