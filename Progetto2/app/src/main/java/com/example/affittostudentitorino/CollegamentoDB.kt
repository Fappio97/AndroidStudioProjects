package com.example.affittostudentitorino

import android.widget.Toast
import org.json.JSONObject

open class CollegamentoEsterno(){

    //Funzione login
    fun Login(user: String): JSONObject{
        val json= JSONObject().apply{
            put("username",user)
            put("email","emailemail@email.com")
            put("id",0)
        }
        return json
    }

    //Funzione registrazione
    fun Register(user: String,email:String): JSONObject{
        val json= JSONObject().apply{
            put("username",user)
            put("email","emailemail@email.com")
            put("id",0)
        }
        return json
    }

    //Funzione profilo
    fun Profilo(id: Int): JSONObject{
        val json= JSONObject().apply{
            put("nomecognome","nome"+" cognome")
            put("matricola","")
            put("corso","")
        }
        return json
    }

    //Funzione preferiti
    fun preferiti(): List<JSONObject>{
        var list= arrayListOf<JSONObject>()
        val json1= JSONObject().apply{
            put("ann","annuncio1")
            put("prezzo", 1)
            put("luogo","luogo1")
            put("id", 0)
        }
        val json2= JSONObject().apply{
            put("ann","annuncio2")
            put("prezzo", 2)
            put("luogo","luogo2")
            put("id", 0)
        }
        list.add(json1)
        list.add(json2)
        return list
    }

    //inserisce un nuovo annuncio
    fun inserisci(json: JSONObject):String{
        return "OK"
    }
}