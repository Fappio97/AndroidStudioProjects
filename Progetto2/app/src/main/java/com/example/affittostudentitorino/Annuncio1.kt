package com.example.affittostudentitorino

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import kotlinx.android.synthetic.main.activity_annuncio.*
import org.json.JSONObject

class Annuncio1 : AppCompatActivity() {

    private val zone=arrayOf("Centro Storico"," San Salvario","Crocetta","San Paolo","Cenisia","Campidoglio","Aurora","Vanchiglia","Nizza","Lingotto", "Santa Rita","Mirafiori Nord","Pozzo Strada","Parella","Le Vallette","Lanzo","Borgata Vittoria","Barriera di Milano","Falchera","Barca- Bertolla","Madonna del Pilone", "Borgo Po","Mirafiori Sud")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annuncio)
        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)
        val idannuncio=intent.getStringExtra(AlarmClock.EXTRA_MESSAGE).toInt()

        if(intent.getStringExtra("ORIGINE")=="Preferiti"){
            val db=DbManager(context = this)
            var list= db.ricercaAnnuncio(idannuncio,sharedPref.getInt("id",9999))
            riempiCampi(list.get(0))
            rim1.visibility= View.VISIBLE
        }
        else if(intent.getStringExtra("ORIGINE")=="MieiAnnunci"){
            //Prende le info da un db esterno
            rim2.visibility= View.VISIBLE
        }

    }
    fun riempiCampi(lista: JSONObject){
        val zona=lista.getString("idzona").toInt()
        indirizzo2.apply{
            append(lista.getString("indirizzo"))
        }
        zona2.apply{
            append(zone[zona])
        }
        prezzo3.apply{
            append(lista.getString("prezzo")+" euro")
        }
        telefono2.apply{
            append(lista.getString("telefono"))
        }
        singole.apply{
           append(lista.getString("camereSingole"))
        }
        doppie.apply{
            append(lista.getString("camereDoppie"))
        }
        triple.apply{
            append(lista.getString("camereTriple"))
        }
        bagni.apply{
           append(lista.getString("bagni"))
        }
        tipo.apply{
            append(lista.getString("camera"))
        }
        email4.apply{
            append(lista.getString("email"))
        }
    }
    //Rimuove l'annuncio selezionato dai preferiti
    fun rimuoviPreferito(view: View){
        val db=DbManager(context = this)

        val idannuncio=intent.getStringExtra(AlarmClock.EXTRA_MESSAGE).toInt()
        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)
        db.cancellaAnnuncio(idannuncio,sharedPref.getInt("id",9999))

        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }

    //Rimuove l'annuncio selezionato
    fun rimuovi(view: View){
        //Si collega al db esrerno
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}
