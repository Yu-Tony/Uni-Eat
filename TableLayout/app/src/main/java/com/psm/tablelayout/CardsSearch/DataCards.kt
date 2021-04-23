package com.psm.tablelayout.CardsSearch

import android.content.Context
import com.psm.tablelayout.R

//Esta clase esta manejada como un singleton
//Se genera una solo instancia durante toda el tiempo de ejecución

object DataCards {
    val genres = ArrayList<Genre>()
    val albums = ArrayList<Album>()
    var content:Context? = null

    init {
        this.initializeGenres()
        this.initializeAlbums()
    }

    private fun initializeGenres(){
        var genre =  Genre(1,"Rock")
        genres.add(genre)

        genre = Genre(2,"Blues")
        genres.add(genre)

        genre = Genre(2,"Jazz")
        genres.add(genre)

        genre = Genre(2,"Country")
        genres.add(genre)

        genre = Genre(2,"Dance")
        genres.add(genre)

    }

    private fun initializeAlbums(){
        var album =  Album()
        album.strTitle =  "The 7th Sense"
        album.strDescription = "keep on breathing"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        album.intIdImage = R.drawable.nct01
        album.genre =  genres[1]
        album.numLikes= 5

        albums.add(album)

        album =  Album()
        album.strTitle =  "NCT #127"
        album.strDescription = "keep on breathing"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles02,content!!)
        album.intIdImage = R.drawable.nct01
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "Chewing Gum"
        album.strDescription = "keep on breathing"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles03,content!!)
        album.intIdImage = R.drawable.nct01
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "NCT #127 LIMITLESS"
        album.strDescription = "keep on breathing"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles04,content!!)
        album.intIdImage = R.drawable.nct01
        album.genre =  genres[1]

        albums.add(album)

        album =  Album()
        album.strTitle =  "The First"
        album.strDescription = "keep on breathing"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles05,content!!)
        album.intIdImage = R.drawable.nct01
        album.genre =  genres[1]

        albums.add(album)

    }

}