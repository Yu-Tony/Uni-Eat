package com.psm.tablelayout.AddCard

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Categorias
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.CardsLong.Facultades
import com.psm.tablelayout.CardsLong.Resena
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.review_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


//https://devofandroid.blogspot.com/2020/08/pick-multiple-images-from-gallery_22.html
class AddCardActivity: AppCompatActivity(), View.OnClickListener {

    //store uris of picked images
    private var images: ArrayList<Uri?>? = null
    private var imagesBM = ArrayList<Bitmap>()
    //current position/index of selected images
    private var position = 0
    //request code to pick image(s)
    private val PICK_IMAGES_CODE = 0
    var categs: ArrayList<Categorias> = DataCards.categorias
    var facus: ArrayList<Facultades> = DataCards.facultad
    var FacultadSeleccionada:String?=null
    var CategoriaSeleccionada:String?=null

    var AllCampos:Boolean?=true

    var imgArray:ByteArray? =  null
    var imgArray2:ByteArray? =  null
    var imgArray3:ByteArray? =  null
    var imgArray4:ByteArray? =  null
    var imgArray5:ByteArray? =  null

    var encodedString:String? = null;
    var encodedString2:String? = null;

    var strEncodeImage: Array<String?> = arrayOfNulls(5)
    /*var strEncodeImage:String? = null
    var strEncodeImageTwo:String? = null
    var strEncodeImage3:String? = null
    var strEncodeImage4:String? = null
    var strEncodeImage5:String? = null*/


    var EditIntent:String? =null
    var IDintent:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_create)


        val RatingIntent = getIntent().getStringExtra("Rating")?.toFloat()


        EditIntent = getIntent().getStringExtra("Edit")
        val TituloIntent = getIntent().getStringExtra("Titulo")
        val DescIntent = getIntent().getStringExtra("Descripcion")
        val CategIntent = getIntent().getStringExtra("Categoria")
        val FacuIntent = getIntent().getStringExtra("Facultad")
        IDintent = getIntent().getStringExtra("ID")?.toInt()

        //Toast.makeText(this, IDintent.toString(), Toast.LENGTH_SHORT).show()


        /*---------SPINNER CATEGORIAS-------------*/
        val spinnerCategoriaCreate=findViewById<Spinner>(R.id.spinnerCategory)
        val adapter: ArrayAdapter<Categorias> =
            ArrayAdapter<Categorias>(this, android.R.layout.simple_spinner_dropdown_item,
            categs
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCategoriaCreate.setAdapter(adapter)

        spinnerCategoriaCreate.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                CategoriaSeleccionada = categs[i].categoriaNombre

            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })


        /*---------SPINNER FACULTADES-------------*/
        val spinnerFacultades=findViewById<Spinner>(R.id.spinnerFacultad)
        val adapterFacu: ArrayAdapter<Facultades> =
            ArrayAdapter<Facultades>(this, android.R.layout.simple_spinner_dropdown_item,
                facus
            )

        adapterFacu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFacultades.setAdapter(adapterFacu)

        spinnerFacultades.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                FacultadSeleccionada = facus[i].facultadesNombre
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        /*----------------------Display images-------------------------*/
        //init list
        images = ArrayList()

        //setup image switcher
        imageViewCreateReview.setFactory { ImageView(applicationContext) }

        //pick images clicking this button
        btnImageReviewCreate.setOnClickListener {
            pickImagesIntent()
        }

        //switch to next image clicking this button
        btnAfterReviewCreate.setOnClickListener {
            if (position < images!!.size-1){
                position++

                val drawable: Drawable = BitmapDrawable(imagesBM.get(position))
                imageViewCreateReview.setImageDrawable(drawable)

                //imageViewCreateReview.setImageURI(images!![position])
                Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            }
            else{
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }
        }

        //switch to previous image clicking this button
        btnBeforeReviewCreate.setOnClickListener {
            if (position > 0) {
                position--

                val drawable: Drawable = BitmapDrawable(imagesBM.get(position))
                imageViewCreateReview.setImageDrawable(drawable)

               // imageViewCreateReview.setImageURI(images!![position])
                Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            } else {
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }
        }

        /*------------------------BOTONES------------------------------*/

        val btnSaveDraft =  findViewById<Button>(R.id.btnSaveDraft)
        btnSaveDraft.setOnClickListener(this)

        val btnPost =  findViewById<Button>(R.id.btnPost)
        btnPost.setOnClickListener(this)

        /*---------------------------EDITAR-----------------------------------*/

        if(EditIntent=="Editar")
        {
            val TituloEditar: TextView = findViewById(R.id.tituloCreate) as TextView
            TituloEditar.text = TituloIntent
            val DescEditar: TextView = findViewById(R.id.DescriptionCreate) as TextView
            DescEditar.text=DescIntent

            imageCreateLayout.setVisibility(View.GONE);
            if (RatingIntent != null) {
                ratingCreate.setRating(RatingIntent)
            }
            FacultadSeleccionada=FacuIntent
            CategoriaSeleccionada=CategIntent
        }
        else
        {
            imageCreateLayout.setVisibility(View.VISIBLE);
        }
    }

    private fun pickImagesIntent() {

     val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_CODE)

    }

    //https://stackoverflow.com/questions/54144821/how-to-limit-multiple-image-selection-from-the-gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE){

            if (resultCode == Activity.RESULT_OK){

                if (data!!.clipData != null){
                    //picked multiple images
                    //get number of picked images
                    val count = data.clipData!!.itemCount

                        if (count > 2) {
                            Toast.makeText(this@AddCardActivity,"Solo puedes guardar un maximo de 2 imagenes", Toast.LENGTH_LONG).show()
                        }
                        else
                        {

                            for (i in 0 until count){
                                val imageUri = data.clipData!!.getItemAt(i).uri
                                //add image to list
                                images!!.add(imageUri)
                            }


                           // imageViewCreateReview.setImageURI(images!![0])
                            position = 0;

                            for(i in 0 until count)
                            {
                                val photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images!![i])

                                val resized = Bitmap.createScaledBitmap(photo, 700, 700, true)
                                //imageView.setImageBitmap(resized)

                                val stream = ByteArrayOutputStream()
                                //Bitmap.CompressFormat agregar el formato desado, estoy usando aqui jpeg
                                resized.compress(Bitmap.CompressFormat.JPEG, 70, stream)

                                imagesBM.add(resized);

                               /* val bitmap =
                                    Bitmap.createScaledBitmap(photo, 500, 500, true)*/
                                //Agregamos al objecto album el arreglo de bytes
                                imgArray =  stream.toByteArray()
                                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                                strEncodeImage[i] = "data:image/png;base64," + encodedString


                            }

                            val drawable: Drawable = BitmapDrawable(imagesBM.get(0))
                            imageViewCreateReview.setImageDrawable(drawable)

                        }

                }
                else{
                    //picked single image
                    val imageUri = data.data
                    //set image to image switcher

                    position = 0;

                    val photo = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    val stream = ByteArrayOutputStream()
                    val resized = Bitmap.createScaledBitmap(photo, 700, 700, true)
                    //Bitmap.CompressFormat agregar el formato desado, estoy usando aqui jpeg
                    resized.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                    //Agregamos al objecto album el arreglo de bytes
                    imgArray =  stream.toByteArray()
                    encodedString=  Base64.getEncoder().encodeToString(imgArray)
                    strEncodeImage[0] = "data:image/png;base64," + encodedString
                    Log.e("light bulb only one", strEncodeImage[0]!!)


                    val drawable: Drawable = BitmapDrawable(resized)
                    imageViewCreateReview.setImageDrawable(drawable)

                   // imageViewCreateReview.setImageURI(imageUri)
                }

            }

        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnSaveDraft-> DraftAdd()
            R.id.btnPost-> PostAdd()
        }
    }

    private fun DraftAdd()
    {
        CheckCampos()
        if(EditIntent=="Editar")
        {
            if(AllCampos==true)
            {
                val res =   Resena(IDintent,
                    DataMY.perfil?.userMail,
                    tituloCreate!!.text.toString(),
                    CategoriaSeleccionada,
                    FacultadSeleccionada,
                    DescriptionCreate!!.text.toString(),
                    ratingCreate.rating,
                    0)

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<Int> = service.updateResena(res)

                result.enqueue(object: Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                       // Toast.makeText(this@AddCardActivity,"Error",Toast.LENGTH_LONG).show()
                        //DataCards.getResenas()
                        Toast.makeText(this@AddCardActivity,"Actualizando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                //Toast.makeText(this@AddCardActivity, strEncodeImage[1], Toast.LENGTH_LONG).show()
                                finish();
                            },
                            5000 // value in milliseconds
                        )
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        //Toast.makeText(this@AddCardActivity,"OK",Toast.LENGTH_LONG).show()

                        //DataCards.getResenas()
                        Toast.makeText(this@AddCardActivity,"Actualizando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                //Toast.makeText(this@AddCardActivity, strEncodeImage[1], Toast.LENGTH_LONG).show()
                                finish();
                            },
                            5000 // value in milliseconds
                        )


                    }
                })
            }
        }
        else
        {
            if(AllCampos==true)
            {

                /*---------------------------------TABLA---------------------------------------*/

                val res =   Resena(0,
                    DataMY.perfil?.userMail,
                    tituloCreate!!.text.toString(),
                    CategoriaSeleccionada,
                    FacultadSeleccionada,
                    DescriptionCreate!!.text.toString(),
                    ratingCreate.rating,
                    0,
                    strEncodeImage[0], strEncodeImage[1], strEncodeImage[2],strEncodeImage[3],strEncodeImage[4])

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<Int> = service.saveResena(res)

                result.enqueue(object: Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Toast.makeText(this@AddCardActivity,"Error",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        Toast.makeText(this@AddCardActivity,"OK",Toast.LENGTH_LONG).show()

                       // DataCards.getResenas()
                        Toast.makeText(this@AddCardActivity,"Guardando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                //Toast.makeText(this@AddCardActivity, strEncodeImage[1], Toast.LENGTH_LONG).show()
                                finish();
                            },
                            5000 // value in milliseconds
                        )


                    }
                })

            }
        }



    }

    private fun PostAdd()
    {

        CheckCampos()
        if(EditIntent=="Editar")
        {
            if(AllCampos==true)
            {
                val res =   Resena(IDintent,
                    DataMY.perfil?.userMail,
                    tituloCreate!!.text.toString(),
                    CategoriaSeleccionada,
                    FacultadSeleccionada,
                    DescriptionCreate!!.text.toString(),
                    ratingCreate.rating,
                    1)

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<Int> = service.updateResena(res)

                result.enqueue(object: Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        //Toast.makeText(this@AddCardActivity,"Error",Toast.LENGTH_LONG).show()
                        //DataCards.getResenas()
                        Toast.makeText(this@AddCardActivity,"Actualizando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                //Toast.makeText(this@AddCardActivity, strEncodeImage[1], Toast.LENGTH_LONG).show()
                                finish();
                            },
                            5000 // value in milliseconds
                        )
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        //Toast.makeText(this@AddCardActivity,"OK",Toast.LENGTH_LONG).show()

                        //DataCards.getResenas()
                        Toast.makeText(this@AddCardActivity,"Actualizando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                //Toast.makeText(this@AddCardActivity, strEncodeImage[1], Toast.LENGTH_LONG).show()
                                finish();
                            },
                            5000 // value in milliseconds
                        )


                    }
                })
            }
        }
        else
        {
            if(AllCampos==true)
            {

                         /*---------------------------------TABLA---------------------------------------*/

                val res =   Resena(0,
                    DataMY.perfil?.userMail,
                    tituloCreate!!.text.toString(),
                    CategoriaSeleccionada,
                    FacultadSeleccionada,
                    DescriptionCreate!!.text.toString(),
                    ratingCreate.rating,
                    1,
                    strEncodeImage[0], strEncodeImage[1], strEncodeImage[2],strEncodeImage[3],strEncodeImage[4])

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<Int> = service.saveResena(res)

                result.enqueue(object: Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Toast.makeText(this@AddCardActivity,"Error",Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        Toast.makeText(this@AddCardActivity,"OK",Toast.LENGTH_LONG).show()

                        //DataCards.getResenas()
                        Toast.makeText(this@AddCardActivity,"Subiendo post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                //Toast.makeText(this@AddCardActivity, strEncodeImage[1], Toast.LENGTH_LONG).show()
                                finish();
                            },
                            5000 // value in milliseconds
                        )


                    }
                })

            }
        }



    }

    private fun CheckCampos()
    {
        AllCampos = true

        if(tituloCreate!!.text.toString() != "")
        {
            if(DescriptionCreate!!.text.toString() != "")
            {
                if(ratingCreate.rating  != 0f)
                {
                    if(images!=null)
                    {

                    }
                    else
                    {
                        AllCampos=false
                        Toast.makeText(this@AddCardActivity,"Debe seleccionar al menos una imagen", Toast.LENGTH_LONG).show()
                    }
                }
                else
                {
                    AllCampos=false
                    Toast.makeText(this@AddCardActivity,"Debe seleccionar la calificacion con las estrellas", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                AllCampos=false
                Toast.makeText(this@AddCardActivity,"El campo Descripcion no debe estar vacio", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            AllCampos=false
            Toast.makeText(this@AddCardActivity,"El campo Titulo no debe estar vacio", Toast.LENGTH_LONG).show()
        }
    }


}