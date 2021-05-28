package com.psm.tablelayout.CardsLong

import FILTER_NAME
import FILTER_TYPE
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities
import com.psm.tablelayout.Search.SearchActivity
import retrofit2.Call
import kotlin.collections.ArrayList


class CardsHomeAdapterFacu(val context: Context, var facu: List<Facultades>): RecyclerView.Adapter<CardsHomeAdapterFacu.ViewHolder>(), Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    private val fullAlbums =  ArrayList<Facultades>(facu)

    //se hace cargo de los graficos
    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val TitleCard =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.HomeTextCard)
        val ImageCard =  itemView?.findViewById<ImageView>(com.psm.tablelayout.R.id.HomeImageCard)
        var facuPosition:Int =  0


        init{

            this.ImageCard.setOnClickListener {

                //Lanzamos el intent para abrir el detalle
               /* val getFilter: Facultades =  DataCards.facultad[this.facuPosition]
               // Log.e("welcome", getFilter.strTitleF);
                val  activityIntent =  Intent(context, SearchActivity::class.java)
                activityIntent.putExtra(FILTER_NAME,getFilter.facultadesNombre)
                activityIntent.putExtra(FILTER_TYPE,"1")
                context.startActivity(activityIntent)*/
            }

            this.TitleCard.setOnClickListener {

                /*//Lanzamos el intent para abrir el detalle
                val getFilter: Facultades =  DataCards.facultad[this.facuPosition]
                // Log.e("welcome", getFilter.strTitleF);
                val  activityIntent =  Intent(context, SearchActivity::class.java)
                activityIntent.putExtra(FILTER_NAME,getFilter.facultadesNombre)
                activityIntent.putExtra(FILTER_TYPE,"1")
                context.startActivity(activityIntent)*/
            }

        }


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
        holder.TitleCard.text =  facultades.facultadesNombre
        holder.facuPosition =  position
        //holder.imgAlbumCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))



        if(facultades.imgArray == null){
            //holder.ImageCard.setImageResource(categorias.categoriaImage!!)
        }else{
            holder.ImageCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(facultades.imgArray!!))
        }


        /*if(facultades.imgArray == null){
            Log.e("take off", "null")
           holder.ImageCard.setImageResource(facultades.facultadesImage!!)
        }else{
            holder.ImageCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(facultades.imgArray!!))
            Log.e("take off", "next level")
        }*/

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

                        facu.facultadesNombre!!.toLowerCase().contains(queryString)
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


    fun setData(items: List<Facultades>) {
        facu = items
    }


}
