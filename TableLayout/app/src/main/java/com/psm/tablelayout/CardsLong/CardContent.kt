package com.psm.tablelayout.CardsLong

import CARD_POSITION
import CARD_TYPE
import DEFAULT_CARD_POSITION
import DEFAULT_CARD_TYPE
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.psm.recyclerview.Utilities.ImageUtilities
import com.psm.tablelayout.AddCard.AddCardActivity
import com.psm.tablelayout.Profile.DataMY
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import kotlinx.android.synthetic.main.review_create.*
import kotlinx.android.synthetic.main.review_watch.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardContent: AppCompatActivity(), View.OnClickListener {

    var resenaID:Int = 0
    var typeCard:Int = 0
    val bitmapArray = arrayListOf<Bitmap>()
    private var position = 0
    var image: ImageView?=null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_watch)

        OptionsCreators.setVisibility(View.GONE);

         image = findViewById<View>(R.id.imageViewWatchReview) as ImageView

        this.resenaID = intent.getIntExtra(CARD_POSITION,DEFAULT_CARD_POSITION)
        this.typeCard = intent.getIntExtra(CARD_TYPE, DEFAULT_CARD_TYPE)


        //Toast.makeText(this, resenaID.toString(), Toast.LENGTH_SHORT).show()

        //Display Album
        displayAlbum()

        //switch to next image clicking this button
        btnAfterReviewWatch.setOnClickListener {
            if (position < bitmapArray!!.size-1){
                position++
                image!!.setImageBitmap(bitmapArray.get(position)!!)
               // imageViewCreateReview.setImageURI(bitmapArray!![position])
               // Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            }
            else{
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }
        }

        //switch to previous image clicking this button
        btnBeforeReviewWatch.setOnClickListener {
            if (position > 0) {
                position--
                image!!.setImageBitmap(bitmapArray.get(position)!!)
                //Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            } else {
                //no more images
                Toast.makeText(this, "No More images...", Toast.LENGTH_SHORT).show()
            }
        }

        val btnDel =  findViewById<Button>(R.id.DeleteMyReview)
        btnDel.setOnClickListener(this)

        val btnEdit =  findViewById<Button>(R.id.EditMyReview)
        btnEdit.setOnClickListener(this)

    }

    private fun displayAlbum()
    {
        if(typeCard==1)
        {
            var contador:Int = DataCards.BestResenas.lastIndex
            var i = 0

            while (i <= contador) {

                if( DataCards.BestResenas[i].resenaID == resenaID)
                {

                    bitmapArray.clear()

                    val showReview:Resena =  DataCards.BestResenas[i]
                    txtTituloReviewWatch.setText(showReview.resenaTitulo)
                    txtDescripcionReviewWatch.setText(showReview.resenaDescription)
                    txtCategoriaReviewWatch.setText(showReview.resenaCategoria)
                    txtFacultadReviewWatch.setText(showReview.resenaFacultad)
                    showReview.resenaRate.let {
                        if (it != null) {
                            ratingReviewWatch.setRating(it)
                        }
                    };

                    if(showReview.resenaImageOne == "data:image/png;base64,"){
                        //holder.imgcomidaCard.setImageResource(comida.intIdImage!!)
                        //Log.e("Not has smthng",showReview.resenaImageOne.toString())
                    }else{
                        //imageViewWatchReview.setImageBitmap(ImageUtilities.getBitMapFromByteArray(showReview.imgArray1!!))
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray1!!)); // Add a bitmap
                        //Log.e("HAS smthng",showReview.resenaImageOne.toString())
                    }

                    if(showReview.resenaImageTwo == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray2!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageThree == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray3!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageFour == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray4!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageFive == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray5!!)); // Add a bitmap
                    }


                    position = 0;

                    image?.setImageBitmap(bitmapArray.get(0)!!)

                    if(DataMY.perfil?.userMail == showReview.resenaUsuario)
                    {
                        OptionsCreators.setVisibility(View.VISIBLE);

                    }
                }
                i = (i+1)
            }

        }
        else
        {
            var contador:Int = DataCards.resenas.lastIndex
            var i = 0

            while (i <= contador) {

                if( DataCards.resenas[i].resenaID == resenaID)
                {

                    bitmapArray.clear()

                    val showReview:Resena =  DataCards.resenas[i]
                    txtTituloReviewWatch.setText(showReview.resenaTitulo)
                    txtDescripcionReviewWatch.setText(showReview.resenaDescription)
                    txtCategoriaReviewWatch.setText(showReview.resenaCategoria)
                    txtFacultadReviewWatch.setText(showReview.resenaFacultad)
                    showReview.resenaRate.let {
                        if (it != null) {
                            ratingReviewWatch.setRating(it)
                        }
                    };

                    if(showReview.resenaImageOne == "data:image/png;base64,"){
                        //holder.imgcomidaCard.setImageResource(comida.intIdImage!!)
                        //Log.e("Not has smthng",showReview.resenaImageOne.toString())
                    }else{
                        //imageViewWatchReview.setImageBitmap(ImageUtilities.getBitMapFromByteArray(showReview.imgArray1!!))
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray1!!)); // Add a bitmap
                        //Log.e("HAS smthng",showReview.resenaImageOne.toString())
                    }

                    if(showReview.resenaImageTwo == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray2!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageThree == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray3!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageFour == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray4!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageFive == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray5!!)); // Add a bitmap
                    }


                    position = 0;

                    image?.setImageBitmap(bitmapArray.get(0)!!)

                    if(DataMY.perfil?.userMail == showReview.resenaUsuario)
                    {
                        OptionsCreators.setVisibility(View.VISIBLE);

                    }
                }

                i = (i+1)
            }

            i=0;
            var contador2:Int = DataMY.resenasDrafts.lastIndex
            while(i<=contador2)
            {
                if( DataMY.resenasDrafts[i].resenaID == resenaID)
                {

                    bitmapArray.clear()

                    val showReview:Resena =   DataMY.resenasDrafts[i]
                    txtTituloReviewWatch.setText(showReview.resenaTitulo)
                    txtDescripcionReviewWatch.setText(showReview.resenaDescription)
                    txtCategoriaReviewWatch.setText(showReview.resenaCategoria)
                    txtFacultadReviewWatch.setText(showReview.resenaFacultad)
                    showReview.resenaRate.let {
                        if (it != null) {
                            ratingReviewWatch.setRating(it)
                        }
                    };

                    if(showReview.resenaImageOne == "data:image/png;base64,"){
                        //holder.imgcomidaCard.setImageResource(comida.intIdImage!!)
                        //Log.e("Not has smthng",showReview.resenaImageOne.toString())
                    }else{
                        //imageViewWatchReview.setImageBitmap(ImageUtilities.getBitMapFromByteArray(showReview.imgArray1!!))
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray1!!)); // Add a bitmap
                        //Log.e("HAS smthng",showReview.resenaImageOne.toString())
                    }

                    if(showReview.resenaImageTwo == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray2!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageThree == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray3!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageFour == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray4!!)); // Add a bitmap
                    }

                    if(showReview.resenaImageFive == "data:image/png;base64,"){
                    }else{
                        bitmapArray.add(ImageUtilities.getBitMapFromByteArray(showReview.imgArray5!!)); // Add a bitmap
                    }


                    position = 0;

                    image?.setImageBitmap(bitmapArray.get(0)!!)

                    if(DataMY.perfil?.userMail == showReview.resenaUsuario)
                    {
                        OptionsCreators.setVisibility(View.VISIBLE);

                    }


                }
                i=(i+1)
            }
        }







    }



    private fun DeleteReview()
    {
        var contador:Int = DataCards.resenas.lastIndex
        var i = 0
        while (i <= contador) {
            if( DataCards.resenas[i].resenaID == resenaID)
            {
                val review =   Resena(DataCards.resenas[i].resenaID)

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<Int> = service.deleteResena(review)

                result.enqueue(object: Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        //Toast.makeText(this@CardContent,"Error",Toast.LENGTH_LONG).show()
                        //DataCards.getResenas()
                        Toast.makeText(this@CardContent,"Borrando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                finish();
                            },
                            5000 // value in milliseconds
                        )
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        //Toast.makeText(this@CardContent,"OK",Toast.LENGTH_LONG).show()
                        Toast.makeText(this@CardContent,"Borrando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                finish();
                            },
                            5000 // value in milliseconds
                        )
                    }

                })
            }
            i=(i+1)
        }

        i=0;
        var contador2:Int = DataMY.resenasDrafts.lastIndex
        while(i<=contador2) {
            if (DataMY.resenasDrafts[i].resenaID == resenaID)
            {
                val review =   Resena(resenaID)

                val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
                val result: Call<Int> = service.deleteResena(review)

                result.enqueue(object: Callback<Int> {
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        //Toast.makeText(this@CardContent,"Error",Toast.LENGTH_LONG).show()
                        //DataCards.getResenas()
                        Toast.makeText(this@CardContent,"Borrando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                finish();
                            },
                            5000 // value in milliseconds
                        )
                    }

                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        //Toast.makeText(this@CardContent,"OK",Toast.LENGTH_LONG).show()
                        Toast.makeText(this@CardContent,"Borrando post...", Toast.LENGTH_LONG).show()
                        Handler().postDelayed(
                            {
                                finish();
                            },
                            5000 // value in milliseconds
                        )
                    }

                })
            }
            i=(i+1)
        }



    }

    private fun EditReview()
    {
        var contador:Int = DataCards.resenas.lastIndex
        var i = 0
        var intent = Intent(this,AddCardActivity::class.java) //not application context

        while (i <= contador) {



            if( DataCards.resenas[i].resenaID == resenaID)
            {

                intent.putExtra("Edit","Editar")
                intent.putExtra("Titulo",txtTituloReviewWatch.text.toString())
                intent.putExtra("Descripcion",txtDescripcionReviewWatch.text.toString())
                intent.putExtra("Categoria",txtCategoriaReviewWatch.text.toString())
                intent.putExtra("Facultad",txtFacultadReviewWatch.text.toString())
                intent.putExtra("Rating",ratingReviewWatch.rating.toString())
                intent.putExtra("ID",DataCards.resenas[i].resenaID.toString())
            }

            i=(i+1)
        }

        i=0;
        var contador2:Int = DataMY.resenasDrafts.lastIndex
        while(i<=contador2) {
            if (DataMY.resenasDrafts[i].resenaID == resenaID) {
                intent.putExtra("Edit","Editar")
                intent.putExtra("Titulo",txtTituloReviewWatch.text.toString())
                intent.putExtra("Descripcion",txtDescripcionReviewWatch.text.toString())
                intent.putExtra("Categoria",txtCategoriaReviewWatch.text.toString())
                intent.putExtra("Facultad",txtFacultadReviewWatch.text.toString())
                intent.putExtra("Rating",ratingReviewWatch.rating.toString())
                intent.putExtra("ID",resenaID.toString())
            }
            i=(i+1)
        }

        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.DeleteMyReview-> DeleteReview()
            R.id.EditMyReview->EditReview()
        }
    }




}