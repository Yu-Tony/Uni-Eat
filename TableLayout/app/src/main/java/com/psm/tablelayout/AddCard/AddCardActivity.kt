package com.psm.tablelayout.AddCard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.psm.tablelayout.CardsLong.Categorias
import com.psm.tablelayout.CardsLong.DataCards
import com.psm.tablelayout.CardsLong.Facultades
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.review_create.*


//https://devofandroid.blogspot.com/2020/08/pick-multiple-images-from-gallery_22.html
class AddCardActivity: AppCompatActivity() {

    //store uris of picked images
    private var images: ArrayList<Uri?>? = null

    //current position/index of selected images
    private var position = 0

    //request code to pick image(s)
    private val PICK_IMAGES_CODE = 0

    var categs: ArrayList<Categorias> = DataCards.categorias
    var facus: ArrayList<Facultades> = DataCards.facultad



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
                categs[i].categoriaNombre
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
                facus[i].facultadesNombre
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
                Toast.makeText(this, images!![position].toString(), Toast.LENGTH_SHORT).show()
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

                    if(count < 3)
                    {
                        Toast.makeText(this@AddCardActivity,"Deses seleccionar al menos 3 imagenes", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        if (count > 5) {
                            Toast.makeText(this@AddCardActivity,"Solo puedes guardar un maximo de 5 imagenes", Toast.LENGTH_LONG).show()
                        }
                        else
                        {

                        }

                        for (i in 0 until 5){
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




}