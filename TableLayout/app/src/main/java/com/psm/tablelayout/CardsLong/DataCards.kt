package com.psm.tablelayout.CardsLong

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.psm.tablelayout.R
import com.psm.tablelayout.RestEngine
import com.psm.tablelayout.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

//Esta clase esta manejada como un singleton
//Se genera una solo instancia durante toda el tiempo de ejecución

object DataCards {
    val facultad = ArrayList<Facultades>()
    val categorias = ArrayList<Categorias>()
    val comida = ArrayList<Comida>()
    var content:Context? = null

    init {
        //this.initializeFacultad()
        //this.initializeCategorias()
       // this.initializeComidas()
        this.getFacultades()
        this.getCategorias()

    }

   /* */

    private fun getFacultades()
    {
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        var resultFacultades: Call<List<Facultades>> = service.getFacultades()

        resultFacultades!!.enqueue(object: Callback<List<Facultades>> {

            override fun onFailure(call: Call<List<Facultades>>, t: Throwable){
                Log.e("getfaultades", "error")
            }

            override fun onResponse(call: Call<List<Facultades>>, response: Response<List<Facultades>>){
                val arrayItems =  response.body()
                if (arrayItems != null){
                    for (item in arrayItems!!){
                        val strImage:String =  item.facultadesImage!!.replace("data:image/png;base64,","")
                        var byteArray:ByteArray? = null
                        byteArray =  Base64.getDecoder().decode(strImage)
                        var  facu = Facultades(item.facultadesID,item.facultadesNombre,strImage,byteArray  )
                        facultad.add(facu)


                    }
                }

            }
        })
    }

    private fun getCategorias()
{
    val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
    var resultCategorias: Call <List<Categorias>> = service.getCategorias()

    resultCategorias!!.enqueue(object: Callback<List<Categorias>> {

        override fun onFailure(call: Call<List<Categorias>>, t: Throwable){
            Log.e("getcategorias", "error")
        }

        override fun onResponse(call: Call<List<Categorias>>, response: Response<List<Categorias>>){
            val arrayItems =  response.body()
            if (arrayItems != null){
                for (item in arrayItems!!){
                    val strImage:String =  item.categoriaImage!!.replace("data:image/png;base64,","")
                    var byteArray:ByteArray? = null
                    byteArray =  Base64.getDecoder().decode(strImage)
                    var  categ = Categorias(item.categoriaID,item.categoriaNombre,strImage,byteArray  )
                    categorias.add(categ)


                }
            }
        }
    })
}

    /*private fun initializeFacultad(){
        var  facu = Facultades(R.drawable.facu_arqui,"Arquitectura")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Ciencias Biológicas")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Ciencias Químicas")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Ciencias Físico Matemáticas")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Ingeniería Civil")//casror raro
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Ingeniería Mecánica y Eléctrica")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Derecho y Criminología")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Filosofía y Letras")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Contaduría Pública y Administración")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Trabajo Social y Desarrollo Humano")//
        facultad.add(facu)

        facu = Facultades(R.drawable.facu_fcfm,"Organización Deportiva")//
        facultad.add(facu)

    }*/

    /*private fun initializeCategorias()
    {
        var  categ = Categorias(R.drawable.facu_fcfm,"Platillos")//
        categorias.add(categ)

        categ = Categorias(R.drawable.facu_fcfm,"Frutas")//
        categorias.add(categ)

        categ = Categorias(R.drawable.facu_fcfm,"Ensaladas")//
        categorias.add(categ)

        categ = Categorias(R.drawable.facu_fcfm,"Bebidas")//
        categorias.add(categ)

        categ = Categorias(R.drawable.facu_fcfm,"Postres")//
        categorias.add(categ)

        /*categ = Categorias(R.drawable.facu_fcfm,"Antojitos")
        categorias.add(categ)

        categ = Categorias(R.drawable.facu_fcfm,"Extras")
        categorias.add(categ)*/


    }*/

    /*private fun initializeComidas(){
        var com =  Comida()
        com.strTitle =  "The 7th Sense"
        com.strDescription = "I'm a misfit 맞는 fit 자체가 없지 숨이 턱턱 막힌 옷을 입은 느낌 지겹지 고민없이 그냥 rip it 나를 끌어내리려할수록 get lifted[Verse 2: Taeyong]됐으요 날재단하려 는 것 다 됐으요"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles01,content!!)
        com.intIdImage = R.drawable.nct01
        com.facu =  facultad[0]
        com.categ= categorias[0]
        com.rating = 2.0f
        comida.add(com)

        com =  Comida()
        com.strTitle =  "NCT #127"
        com.strDescription = "Breve descripcion de la comida"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles02,content!!)
        com.intIdImage = R.drawable.nct01
        com.facu =  facultad[1]
        com.categ= categorias[1]
        com.rating = 3.0f
        comida.add(com)

        com =  Comida()
        com.strTitle =  "Chewing Gum"
        com.strDescription = "Filosofía y Letras"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles03,content!!)
        com.intIdImage = R.drawable.nct01
        com.facu =  facultad[1]
        com.categ= categorias[2]
        com.rating = 4.5f
        comida.add(com)

        com =  Comida()
        com.strTitle =  "NCT #127 LIMITLESS"
        com.strDescription = "Breve descripcion de la comida"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles04,content!!)
        com.intIdImage = R.drawable.nct01
        com.facu =  facultad[2]
        com.categ= categorias[2]
        com.rating = 2.0f
        comida.add(com)

        com =  Comida()
        com.strTitle =  "The First"
        com.strDescription = "Breve descripcion de la comida"
        //album.imgArray =  ImageUtilities.getByteArrayFromResourse(R.drawable.beatles05,content!!)
        com.intIdImage = R.drawable.nct01
        com.facu =  facultad[2]
        com.categ= categorias[2]
        com.rating = 5.0f
        comida.add(com)

    }*/

}