package com.psm.tablelayout.AddCard

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import kotlinx.android.synthetic.main.review_create.*
import kotlinx.android.synthetic.main.signup.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


//https://devofandroid.blogspot.com/2020/08/pick-multiple-images-from-gallery_22.html
class AddCardActivity: AppCompatActivity(), View.OnClickListener {

    //store uris of picked images
    private var images: ArrayList<Uri?>? = null
    //current position/index of selected images
    private var position = 0
    //request code to pick image(s)
    private val PICK_IMAGES_CODE = 0
    var categs: ArrayList<Categorias> = DataCards.categorias
    var facus: ArrayList<Facultades> = DataCards.facultad
    var FacultadSeleccionada:String?=null
    var CategoriaSeleccionada:String?=null

    var AllCampos:Boolean?=true





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_create)

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
                imageViewCreateReview.setImageURI(images!![position])
                //Toast.makeText(this, images!![position].toString(), Toast.LENGTH_SHORT).show()
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
                imageViewCreateReview.setImageURI(images!![position])
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

                        if (count > 5) {
                            Toast.makeText(this@AddCardActivity,"Solo puedes guardar un maximo de 5 imagenes", Toast.LENGTH_LONG).show()
                        }
                        else
                        {
                            for (i in 0 until count){
                                val imageUri = data.clipData!!.getItemAt(i).uri
                                //add image to list
                                images!!.add(imageUri)


                            }
                            //set first image from list to image switcher
                            imageViewCreateReview.setImageURI(images!![0])
                            position = 0;
                        }

                }
                else{
                    //picked single image
                    val imageUri = data.data
                    //set image to image switcher
                    imageViewCreateReview.setImageURI(imageUri)
                    position = 0;
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
        if(AllCampos==true)
        {

            /*---------------------------------IMAGENES---------------------------------------*/
            var imgArray:ByteArray? =  null
            var encodedString:String? = null;
            var strEncodeImage:String? = null
            var strEncodeImage2:String? = null
            var strEncodeImage3:String? = null
            var strEncodeImage4:String? = null
            var strEncodeImage5:String? = null
            var photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(0))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(1))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString =  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage2 = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(2))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage3 = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(3))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage4 = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(4))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage5 = "data:image/png;base64," + encodedString
            }

            /*---------------------------------TABLA---------------------------------------*/

            val res =   Resena(0,
                DataMY.perfil?.userMail,
                tituloCreate!!.text.toString(),
                CategoriaSeleccionada,
                FacultadSeleccionada,
                DescriptionCreate!!.text.toString(),
                ratingCreate.rating,
                0,
                strEncodeImage, strEncodeImage2, strEncodeImage3,strEncodeImage4,strEncodeImage5)

        }


    }

    private fun PostAdd()
    {
        CheckCampos()
        if(AllCampos==true)
        {

            /*---------------------------------IMAGENES---------------------------------------*/
            var imgArray:ByteArray? =  null
            var encodedString:String? = null;
            var strEncodeImage:String? = null
            var strEncodeImage2:String? = null
            var strEncodeImage3:String? = null
            var strEncodeImage4:String? = null
            var strEncodeImage5:String? = null
            var photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(0))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(1))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString =  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage2 = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(2))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage3 = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(3))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage4 = "data:image/png;base64," + encodedString
            }
            photo = MediaStore.Images.Media.getBitmap(this.contentResolver, images?.get(4))
            if(photo!=null)
            { val stream = ByteArrayOutputStream()
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                imgArray =  stream.toByteArray()
                encodedString=  Base64.getEncoder().encodeToString(imgArray)
                strEncodeImage5 = "data:image/png;base64," + encodedString
            }

            /*---------------------------------TABLA---------------------------------------*/

            val res =   Resena(0,
                DataMY.perfil?.userMail,
                tituloCreate!!.text.toString(),
                CategoriaSeleccionada,
                FacultadSeleccionada,
                DescriptionCreate!!.text.toString(),
                ratingCreate.rating,
                0,
                strEncodeImage, strEncodeImage2, strEncodeImage3,strEncodeImage4,strEncodeImage5)

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