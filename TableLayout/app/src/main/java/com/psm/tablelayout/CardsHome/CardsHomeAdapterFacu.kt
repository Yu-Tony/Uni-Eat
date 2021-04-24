package com.psm.tablelayout.CardsLong

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities


class CardsHomeAdapterFacu(val context: Context, var facu:List<Facultades>): RecyclerView.Adapter<CardsHomeAdapterFacu.ViewHolder>(), Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    private val fullAlbums =  ArrayList<Facultades>(facu)

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
        return this.facu.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facultades =  this.facu[position]
        holder.TitleCard.text =  facultades.strTitleF
        holder.albumPosition =  position
        //holder.imgAlbumCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))

        if(facultades.imgArray == null){
            holder.ImageCard.setImageResource(facultades.imageFacu!!)
        }else{
            holder.ImageCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(facultades.imgArray!!))
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



                    facu.filter { facu ->

                        facu.strTitleF!!.toLowerCase().contains(queryString)
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                facu =  results?.values as List<Facultades>
                notifyDataSetChanged()
            }

        }

    }





}
