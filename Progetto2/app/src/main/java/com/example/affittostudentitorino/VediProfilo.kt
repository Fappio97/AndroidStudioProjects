package com.example.affittostudentitorino

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vedi_profilo.*
import org.json.JSONObject

class VediProfilo : AppCompatActivity() {

    private val coll=CollegamentoEsterno() // tramite esso uso i metodi della classe Collegamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedi_profilo)

        val sharedPref=getSharedPreferences("info", Context.MODE_PRIVATE)
        var id=sharedPref.getInt("id",9)
        var json=coll.Profilo(id)

        settacampi(json)
    }

    //Riempie i campi di testo
    @SuppressLint("SetTextI18n")
    fun settacampi(json: JSONObject){
        val sharedPref=getSharedPreferences("info", Context.MODE_PRIVATE)
        nomecognome.apply{
            append(json.getString("nomecognome"))
        }
        username3.apply{
            append(sharedPref.getString("username", "errore nel salvataggio"))
        }
        email3.apply{
           append(sharedPref.getString("email", "errore nel salvataggio"))
        }
        matricola2.apply{
           append(json.getString("matricola"))
        }
        corso2.apply{
            append(json.getString("corso"))
        }
    }

}
