package com.psm.tablelayout.CardsLong

import CARD_POSITION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.psm.recyclerview.Utilities.ImageUtilities


class CardsAdapterAll(val context: Context, var resenas:List<Resena>): RecyclerView.Adapter<CardsAdapterAll.ViewHolder>(), Filterable {



    private  val layoutInflater =  LayoutInflater.from(context)
    val fullResenas =  ArrayList<Resena>(resenas)


    //se hace cargo de los graficos
    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){




        val txtTitle =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.txtTitle)
        val txtDescription =  itemView?.findViewById<TextView>(com.psm.tablelayout.R.id.txtDescription)
        val imgcomidaCard =  itemView?.findViewById<ImageView>(com.psm.tablelayout.R.id.imgComidaCard)
        val ratingComidaCard=itemView?.findViewById<RatingBar>(com.psm.tablelayout.R.id.ratingBarItemCard)
        var comidaPosition:Int =  0
        var comidaID:Int = 0


        init{

            /*this.LikeButton.setOnClickListener {

                val albumEdit:Album =  DataManager.albums[albumPosition]
                var tempNum = albumEdit.numLikes?.plus(1);


                SaveLikes(tempNum,this.albumPosition);
                this.likeAlbum.text = tempNum.toString()

                Log.d("UGH", "AddLike:" + (tempNum))
                //this.likeAlbum.setText(this.likeCount)
            }*/

            this.imgcomidaCard.setOnClickListener {

                //Lanzamos el intent para abrir el detalle
                val  activityIntent =  Intent(context,CardContent::class.java)
                activityIntent.putExtra(CARD_POSITION,this.comidaID)
                context.startActivity(activityIntent)

                //Log.e("call me",this.comidaID.toString())



            }


           /* this.txtTitle.setOnClickListener {

                //Lanzamos el intent para abrir el detalle
                val  activityIntent =  Intent(context,CardContent::class.java)
                activityIntent.putExtra(CARD_POSITION,this.comidaPosition)
                context.startActivity(activityIntent)
            }*/

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =  this.layoutInflater.inflate(com.psm.tablelayout.R.layout.item_card_search,parent,false)
        return  ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return this.resenas.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comida =  this.resenas[position]
        holder.txtTitle.text =  comida.resenaTitulo
        //comida.rating?.let { holder.ratingComidaCard.setRating(it) };
        //showReview.rating?.let { ratingReviewWatch.setRating(it) };
        comida.resenaRate?.let { holder.ratingComidaCard.setRating(it) }
        holder.txtDescription.setText(comida.resenaDescription)
        holder.comidaPosition =  position
        holder.comidaID = comida.resenaID!!

        if(comida.imgArray1 == null){
            //holder.imgcomidaCard.setImageResource(comida.intIdImage!!)
        }else{
            holder.imgcomidaCard.setImageBitmap(ImageUtilities.getBitMapFromByteArray(comida.imgArray1!!))
        }

    }



    fun setData(items: List<Resena>) {
        resenas = items
    }

    override fun getFilter(): Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
               /*  filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){
                    //Log.e("This enter ", "is null :c")
                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()
                    if (queryString == null || queryString.isEmpty() || queryString == "")
                    {
                        // Log.e("This enter ", "is null too")
                        fullComidas
                    }
                    else
                    {
                        // Log.e("This enter is not null ", queryString)
                        comidas.filter { comidas ->

                            comidas.strTitle!!.toLowerCase().contains(queryString)|| //comidas.strDescription!!.toLowerCase().contains(queryString)||
                                    comidas.facu?.facultadesNombre!!.toLowerCase().contains(queryString) ||
                                    comidas.categ?.strTitleC!!.toLowerCase().contains(queryString)
                        }
                    }

                }*/

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
                /*
                comidas =  results?.values as List<Comida>
                notifyDataSetChanged()*/
            }
        }


    }

    fun NameFilter():Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){

                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()

                  // Log.e("This enter is not null ", queryString)
                        resenas.filter { comidas ->
                            comidas.resenaTitulo!!.toLowerCase().contains(queryString) || comidas.resenaDescription!!.toLowerCase().contains(queryString)
                        }


                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {

                resenas =  results?.values as List<Resena>
                notifyDataSetChanged()
            }
        }

    }

    fun FacuFilter():Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){
                    //Log.e("This enter ", "is null :c")
                    Toast.makeText(Activity(),"FULL RESENA", Toast.LENGTH_SHORT).show();
                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()

                        // Log.e("This enter is not null ", queryString)
                        resenas.filter { comidas ->
                            Toast.makeText(Activity(),"ONLY FACU", Toast.LENGTH_SHORT).show();

                                    comidas.resenaFacultad!!.toLowerCase().contains(queryString)
                        }


                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {

                resenas =  results?.values as List<Resena>
                notifyDataSetChanged()
            }
        }

    }

    fun CategFilter():Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {

                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){
                    //Log.e("This enter ", "is null :c")
                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()
                    if (queryString == null || queryString.isEmpty() || queryString == "")
                    {
                        // Log.e("This enter ", "is null too")
                        fullResenas
                    }
                    else
                    {
                        // Log.e("This enter is not null ", queryString)
                        resenas.filter { comidas ->


                                    comidas.resenaCategoria!!.toLowerCase().contains(queryString)
                        }
                    }

                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {

                resenas =  results?.values as List<Resena>
                notifyDataSetChanged()
            }
        }

    }

    fun NameFilterUpside():Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {

                resenas=resenas.reversed()
                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
               filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){

                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()
                    if (queryString == null || queryString.isEmpty() || queryString == "")
                    {
                        // Log.e("This enter ", "is null too")

                    }
                    else
                    {

                        // Log.e("This enter is not null ", queryString)
                        resenas.filter { comidas ->
                            comidas.resenaTitulo!!.toLowerCase().contains(queryString) || comidas.resenaDescription!!.toLowerCase().contains(queryString)
                        }
                    }

                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {

                resenas =  results?.values as List<Resena>
                resenas=resenas.reversed()
                notifyDataSetChanged()
            }
        }

    }

    fun FacuFilterUpside():Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {

                resenas=resenas.reversed()
                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){
                    //Log.e("This enter ", "is null :c")
                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()
                    if (queryString == null || queryString.isEmpty() || queryString == "")
                    {
                        // Log.e("This enter ", "is null too")
                        fullResenas
                    }
                    else
                    {
                        // Log.e("This enter is not null ", queryString)
                        resenas.filter { comidas ->


                            comidas.resenaFacultad!!.toLowerCase().contains(queryString)
                        }
                    }

                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {

                resenas =  results?.values as List<Resena>
                resenas=resenas.reversed()
                notifyDataSetChanged()
            }
        }

    }

    fun CategFilterUpside():Filter
    {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults? {
                resenas=resenas.reversed()
                //Obtenemos la cadena
                val filterResults = Filter.FilterResults()
                filterResults.values =  if (charSequence == null || charSequence.isEmpty() || charSequence == ""){
                    //Log.e("This enter ", "is null :c")
                    fullResenas

                }
                else{
                    val queryString = charSequence?.toString()?.toLowerCase()
                    if (queryString == null || queryString.isEmpty() || queryString == "")
                    {
                        // Log.e("This enter ", "is null too")
                        fullResenas
                    }
                    else
                    {
                        // Log.e("This enter is not null ", queryString)
                        resenas.filter { comidas ->


                            comidas.resenaCategoria!!.toLowerCase().contains(queryString)
                        }
                    }

                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {

                resenas =  results?.values as List<Resena>
                resenas=resenas.reversed()
                notifyDataSetChanged()
            }
        }

    }

}
