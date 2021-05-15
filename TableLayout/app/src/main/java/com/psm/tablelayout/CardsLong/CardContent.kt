package com.psm.tablelayout.CardsLong

import CARD_POSITION
import DEFAULT_CARD_POSITION
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.psm.recyclerview.Utilities.ImageUtilities
import com.psm.tablelayout.R
import kotlinx.android.synthetic.main.review_watch.*

class CardContent: AppCompatActivity() {

    var comidaPosition:Int = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_watch)


        this.comidaPosition =  savedInstanceState?.getInt(CARD_POSITION,DEFAULT_CARD_POSITION) ?: intent.getIntExtra(CARD_POSITION,DEFAULT_CARD_POSITION)
        //Display Album
        if(this.comidaPosition != DEFAULT_CARD_POSITION){
            this.displayAlbum()
        }else{
            DataCards.comida.add(Comida())
            this.comidaPosition =  DataCards.comida.lastIndex
        }


    }

    private fun displayAlbum()
    {
        val showReview:Comida =  DataCards.comida[comidaPosition]
        txtTituloReviewWatch.setText(showReview.strTitle)
        txtDescripcionReviewWatch.setText(showReview.strDescription)
        txtCategoriaReviewWatch.setText(showReview.categ?.strTitleC)
        txtFacultadReviewWatch.setText(showReview.facu?.strTitleF)

       /* carouselReviewWatch.pageCount=3;
        carouselReviewWatch.setImageListener{
            position,imageView->
            showReview.imgArray?.get(position)?.toInt()?.let { imageView.setImageResource(it) }
        }

        */


    }


}