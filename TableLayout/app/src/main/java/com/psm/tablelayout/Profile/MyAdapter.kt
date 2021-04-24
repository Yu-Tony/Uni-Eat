package com.psm.tablelayout.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities
import com.psm.tablelayout.CardsSearch.Comida


class MyAdapter(val context: Context, var comidas:List<Comida>): RecyclerView.Adapter<MyAdapter.ViewHolder>(), Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    private val fullComidas =  ArrayList<Comida>(comidas)

    //se hace cargo de los graficos
    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        val txtTitle =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.txtTitle)
        val txtDescription =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.txtDescription)
        val imgcomidaCard =  itemView?.findViewById<ImageView>(com.psm.tablelayout.R.id.imgComidaCard)
        var comidaPosition:Int =  0

        init{






        }




    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  this.layoutInflater.inflate(com.psm.tablelayout.R.layout.item_card_search,parent,false)
        return  ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return this.comidas.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comida =  this.comidas[position]
        holder.txtTitle.text =  comida.strTitle
        holder.txtDescription.setText(comida.strDescription)
        holder.comidaPosition =  position


        if(comida.imgArray == null){
            holder.imgcomidaCard.setImageResource(comida.intIdImage!!)
        }else{
            holder.imgcomidaCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(comida.imgArray!!))
        }

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty()){

                    fullComidas

                }else{
                    val queryString = charSequence?.toString()?.toLowerCase()



                    comidas.filter { comidas ->

                        comidas.strTitle!!.toLowerCase().contains(queryString)|| comidas.strDescription!!.toLowerCase().contains(queryString)
                    }
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                comidas =  results?.values as List<Comida>
                notifyDataSetChanged()
            }

        }

    }


}
