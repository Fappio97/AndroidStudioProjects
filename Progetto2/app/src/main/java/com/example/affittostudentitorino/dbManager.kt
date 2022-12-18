package com.example.affittostudentitorino

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import junit.framework.Test
import org.json.JSONObject
import java.util.logging.Logger


class DbManager internal constructor(context: Context) {

    internal var helper: dbHelper? = null

    init {

        helper = dbHelper(context, DATABASE, null, VERSIONE_DATABASE)
    }
    //Ricerca gli annunci salvati da un utente
    fun elencoannunci(idutente: Int): List<JSONObject> {
        var list = arrayListOf<JSONObject>()
        val query = "SELECT * FROM annuncio,appartamento WHERE account=$idutente"
        val db = helper!!.readableDatabase
        val result = db.rawQuery(query, null)
        result!!.moveToFirst()
        while (result.moveToNext()) {
            val json = JSONObject().apply {
                put("idzona", result.getString(result.getColumnIndex("idzona")).toInt())
                put("prezzo", result.getString(result.getColumnIndex("prezzo")).toInt())
                put("indirizzo", result.getString(result.getColumnIndex("indirizzo")))
                put("id", result.getString(result.getColumnIndex("idannuncio")).toInt())
            }
            list.add(json)
        }
        return list
    }
    //Ricerca le info relative ad un anunncio
    fun ricercaAnnuncio(idannuncio: Int, id: Int): List<JSONObject>{
        var list = arrayListOf<JSONObject>()
        val query = "SELECT * FROM appartamento,annuncio WHERE idannuncio=$idannuncio AND account=$id"
        val db = helper!!.getReadableDatabase()
        val result = db.rawQuery(query, null)
        result!!.moveToFirst()
        while (result.moveToNext()) {
            val json = JSONObject().apply {
                put("idzona", result.getString(result.getColumnIndex("idzona")))
                put("prezzo", result.getString(result.getColumnIndex("prezzo")).toInt())
                put("indirizzo", result.getString(result.getColumnIndex("indirizzo")))
                put("id", result.getString(result.getColumnIndex("idannuncio")).toInt())
                put("camera", result.getString(result.getColumnIndex("camera")))
                put("camereSingole", result.getString(result.getColumnIndex("camereSingole")).toInt())
                put("camereDoppie", result.getString(result.getColumnIndex("camereDoppie")).toInt())
                put("camereTriple", result.getString(result.getColumnIndex("camereTriple")).toInt())
                put("bagni", result.getString(result.getColumnIndex("bagni")).toInt())
                put("telefono", result.getString(result.getColumnIndex("telefono")))
                put("email", result.getString(result.getColumnIndex("email")))
            }
            list.add(json)
        }
        result.close()
        return list
    }
    //Cancella un annuncio dai preferiti
    fun cancellaAnnuncio(idannuncio: Int, id: Int) {
        val query = "DELETE FROM annuncio WHERE idannuncio=$idannuncio AND account=$id"
        val db = helper!!.getReadableDatabase()
        db.execSQL(query)
    }
    //informazioni sul database
    companion object {
        private val DATABASE = "affittointerno"
        private val VERSIONE_DATABASE = 1
    }
}