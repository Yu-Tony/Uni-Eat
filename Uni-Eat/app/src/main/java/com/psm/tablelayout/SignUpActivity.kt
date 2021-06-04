package com.psm.tablelayout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Perfil
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.Profile.SaveSharedPreference
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

    //https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-android/
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

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }
    ///////////////////////////////////////

    var builderUser: AlertDialog.Builder? = null
    var dialogUser: AlertDialog? = null

    var builderCreateError: AlertDialog.Builder? = null
    var dialogCreateError: AlertDialog? = null

    var builderCreate: AlertDialog.Builder? = null
    var dialogCreate: AlertDialog? = null

    //////////////////////////////////////////////////

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

         builderUser = AlertDialog.Builder(this)
         dialogUser = builderUser!!.setTitle("Error")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Ya existe un usuario con este correo, intentar con otro correo o iniciar sesión")
            .setPositiveButton("Cerrar") {
                    dialogUser, which -> dialogUser.dismiss()
            }
            .create()

        builderCreateError = AlertDialog.Builder(this)
         dialogCreateError = builderCreateError!!.setTitle("Error")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Ocurrió un error al crear el usuario, favor de intentar de nuevo")
            .setPositiveButton("Cerrar") {
                    dialogCreateError, which -> dialogCreateError.dismiss()
            }
            .create()

        builderCreate = AlertDialog.Builder(this)
        dialogCreate = builderCreate!!.setTitle("Bienvenido")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Usuario creado exitosamente")
            .setPositiveButton("Cerrar") {
                    dialogCreate, which -> dialogCreate.dismiss()
            }
            .create()
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
                            dialogUser?.show()
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
                dialogCreate?.show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                //Toast.makeText(this@SignUpActivity,"OK",Toast.LENGTH_LONG).show()
                Toast.makeText(this@SignUpActivity,"Usuario creado", Toast.LENGTH_LONG).show()
                //dialogCreate?.show()

                DataMY.initializePerfil (0,
                    nombreUsuario,
                    apellidoUsuario,
                    emailUsuario,
                    passUsuario,
                    phoneUsuario,
                    strEncodeImage)

                SaveSharedPreference.setUserName(this@SignUpActivity,
                    DataMY.perfil?.userNombre
                )

                val returnIntent = Intent()
                returnIntent.putExtra("result", EXTRA_TEXT_ADD)
                setResult(Activity.RESULT_OK, returnIntent)
                finish();


            }
        })
    }

    private fun checkUser(){

        //https://handyopinion.com/no-internet-connection-dialog-in-android-java-kotlin/
        val ConnectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = ConnectionManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected == true)
        {
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
                        var emailCorrect = isValidEmail(emailUsuario)

                        if(emailCorrect==true)
                        {
                            /*-----------------------------PASSWORD CHECK---------------------------*/
                            if(editTextTextPassword!!.text.toString() != "")
                            {
                                if (!PASSWORD_PATTERN.matcher(editTextTextPassword!!.text.toString()).matches()) {
                                    errorUser=0
                                    Toast.makeText(this@SignUpActivity,"Contraseña debe tener minimo 8 caracteres y debe incluir 1 mayus, 1 min, 1 numero", Toast.LENGTH_LONG).show()
                                }
                                else
                                {
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


                                        checkEmail()

                                    }
                                    else
                                    {
                                        errorUser=1
                                        Toast.makeText(this@SignUpActivity,"Favor de agregar una imagen", Toast.LENGTH_LONG).show()
                                    }
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
                            Toast.makeText(this@SignUpActivity,"Ingrese un email valido", Toast.LENGTH_LONG).show()

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