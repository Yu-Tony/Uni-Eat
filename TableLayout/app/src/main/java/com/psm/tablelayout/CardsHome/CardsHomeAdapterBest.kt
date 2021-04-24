package com.psm.tablelayout.CardsLong

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities


class CardsHomeAdapterBest(val context: Context, var albums:List<Comida>): RecyclerView.Adapter<CardsHomeAdapterBest.ViewHolder>(), Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    private val fullAlbums =  ArrayList<Comida>(albums)

    //se hace cargo de los graficos
    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        val TitleCard =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.TitleCard)
        val ImageCard =  itemView?.findViewById<ImageView>(com.psm.tablelayout.R.id.ImageCard)
        var albumPosition:Int =  0




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  this.layoutInflater.inflate(com.psm.tablelayout.R.layout.item_card_home,parent,false)
        return  ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return this.albums.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album =  this.albums[position]
        holder.TitleCard.text =  album.strTitle
        holder.albumPosition =  position
        //holder.imgAlbumCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))

        if(album.imgArray == null){
            holder.ImageCard.setImageResource(album.intIdImage!!)
        }else{
            holder.ImageCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))
        }

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty()){

                    fullAlbums

                }else{
                    val queryString = charSequence?.toString()?.toLowerCase()



                    albums.filter { album ->

                        album.strTitle!!.toLowerCase().contains(queryString)|| album.strDescription!!.toLowerCase().contains(queryString)
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                albums =  results?.values as List<Comida>
                notifyDataSetChanged()
            }

        }

    }


}
