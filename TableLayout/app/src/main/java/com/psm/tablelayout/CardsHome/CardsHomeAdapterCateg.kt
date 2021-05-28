package com.psm.tablelayout.CardsLong

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities


class CardsHomeAdapterCateg(val context: Context, var categ: List<Categorias>): RecyclerView.Adapter<CardsHomeAdapterCateg.ViewHolder>(), Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    private val fullAlbums =  ArrayList<Categorias>(categ)

    //se hace cargo de los graficos
    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        val TitleCard =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.HomeTextCard)
        val ImageCard =  itemView?.findViewById<ImageView>(com.psm.tablelayout.R.id.HomeImageCard)
        var categPosition:Int =  0


        init{

            this.ImageCard.setOnClickListener {

                /*//Lanzamos el intent para abrir el detalle
                val getFilter: Categorias =  DataCards.categorias[this.categPosition]
                // Log.e("welcome", getFilter.strTitleF);
                val  activityIntent =  Intent(context, SearchActivity::class.java)
                activityIntent.putExtra(FILTER_NAME,getFilter.categoriaNombre)
                activityIntent.putExtra(FILTER_TYPE,"2")
                context.startActivity(activityIntent)*/
            }


            this.TitleCard.setOnClickListener {

                /*//Lanzamos el intent para abrir el detalle
                val getFilter: Categorias =  DataCards.categorias[this.categPosition]
                // Log.e("welcome", getFilter.strTitleF);
                val  activityIntent =  Intent(context, SearchActivity::class.java)
                activityIntent.putExtra(FILTER_NAME,getFilter.categoriaNombre)
                activityIntent.putExtra(FILTER_TYPE,"2")
                context.startActivity(activityIntent)*/
            }

        }




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  this.layoutInflater.inflate(com.psm.tablelayout.R.layout.item_card_home,parent,false)
        return  ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return this.categ.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categorias =  this.categ[position]
        holder.TitleCard.text =  categorias.categoriaNombre
        holder.categPosition =  position
        //holder.imgAlbumCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(album.imgArray!!))

        if(categorias.imgArray == null){
            //holder.ImageCard.setImageResource(categorias.categoriaImage!!)
        }else{
            holder.ImageCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(categorias.imgArray!!))
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



                    categ.filter { categ ->

                        categ.categoriaNombre!!.toLowerCase().contains(queryString)
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                categ =  results?.values as List<Categorias>
                notifyDataSetChanged()
            }

        }

    }

    fun setData(items: List<Categorias>) {
        categ = items
    }



}
