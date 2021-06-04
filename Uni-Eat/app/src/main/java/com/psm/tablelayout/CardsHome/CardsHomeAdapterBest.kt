package com.psm.tablelayout.CardsHome

import CARD_POSITION
import CARD_TYPE
import FILTER_TYPE
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities
import com.psm.tablelayout.CardsLong.CardContent
import com.psm.tablelayout.CardsLong.Facultades
import com.psm.tablelayout.CardsLong.Resena

class CardsHomeAdapterBest(val context: Context, var bestRes: List<Resena>): RecyclerView.Adapter<CardsHomeAdapterBest.ViewHolder>(),
    Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    private val fullBestRes =  ArrayList<Resena>(bestRes)

    //se hace cargo de los graficos
    inner class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val TitleCard =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.HomeTextCard)
        val ImageCard =  itemView?.findViewById<ImageView>(com.psm.tablelayout.R.id.HomeImageCard)
        var facuPosition:Int =  0
        var comidaID:Int = 0

        init{

            this.ImageCard.setOnClickListener {

                //Lanzamos el intent para abrir el detalle

                // Log.e("welcome", getFilter.strTitleF);

                val  activityIntent =  Intent(context,CardContent::class.java)
                activityIntent.putExtra(CARD_POSITION,this.comidaID)
                activityIntent.putExtra(CARD_TYPE,1)
                context.startActivity(activityIntent)

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
        return this.bestRes.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facultades =  this.bestRes[position]
        holder.TitleCard.text =  facultades.resenaTitulo
        holder.facuPosition =  position
        holder.comidaID = facultades.resenaID!!
        //holder.imgAlbumCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))



        if(facultades.imgArray1 == null){
            //holder.ImageCard.setImageResource(categorias.categoriaImage!!)
        }else{
            holder.ImageCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(facultades.imgArray1!!))
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

                    fullBestRes

                }else{
                    val queryString = charSequence?.toString()?.toLowerCase()



                    bestRes.filter { bestRes ->

                        bestRes.resenaTitulo!!.toLowerCase().contains(queryString)
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                bestRes =  results?.values as List<Resena>
                notifyDataSetChanged()
            }

        }

    }


    fun setData(items: List<Resena>) {
        bestRes = items
    }


}
