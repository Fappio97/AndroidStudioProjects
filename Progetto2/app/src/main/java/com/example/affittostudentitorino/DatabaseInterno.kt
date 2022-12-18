package com.example.affittostudentitorino

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import org.json.JSONObject
import java.sql.SQLException
import java.util.*
import kotlin.math.absoluteValue

class DatabaseInterno(var context: Context): SQLiteOpenHelper(context,"affittointerno",null,1){
    private val zone=arrayOf("Centro Storico"," San Salvario","Crocetta","San Paolo","Cenisia","Campidoglio","Aurora","Vanchiglia","Nizza","Lingotto", "Santa Rita","Mirafiori Nord","Pozzo Strada","Parella","Le Vallette","Lanzo","Borgata Vittoria","Barriera di Milano","Falchera","Barca- Bertolla","Madonna del Pilone", "Borgo Po","Mirafiori Sud")
    private var createTable1 ="CREATE TABLE IF NOT EXISTS zona(idzona INTEGER PRIMARY KEY NOT NULL,nome varchar(50) NOT NULL DEFAULT '0')"
    private var createTable2 ="CREATE TABLE IF NOT EXISTS appartamento( idappartamento INTEGER PRIMARY KEY NOT NULL , camereSingole INTEGER NOT NULL DEFAULT '0' , camereDoppie INTEGER NOT NULL DEFAULT '0' , camereTriple INTEGER NOT NULL DEFAULT '0' , bagni INTEGER NOT NULL DEFAULT '0' , indirizzo varchar(50) NOT NULL DEFAULT '0' , piano INTEGER NOT NULL DEFAULT '0')"
    private var createTable3 ="CREATE TABLE IF NOT EXISTS annuncio( idannuncio INTEGER PRIMARY KEY NOT NULL, utente INTEGER  NOT NULL, account INTEGER , datacreazione varchar(50) NOT NULL, prezzo INTEGER NOT NULL, camera varchar(50) NOT NULL, idzona INTEGER DEFAULT NULL, idappartamento INTEGER NOT NULL, CONSTRAINT FK_annuncio_appartamento  FOREIGN KEY (idappartamento) REFERENCES appartamento (idappartamento) ON DELETE CASCADE ON UPDATE CASCADE,CONSTRAINT FK_annuncio_zona FOREIGN KEY (idzona) REFERENCES zona (idzona) ON DELETE SET NULL ON UPDATE CASCADE)"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTable1)
        db.execSQL(createTable2)
        db.execSQL(createTable3)
        zone(db,0,"Centro Storico")
        zone(db,1,"San Salvario")
        inserisciZone(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
        TODO("non da implementare")
    }

    fun cancella(idannuncio: Int,idutente: Int){
        val db = this.readableDatabase
        var delete="DELETE FROM annuncio WHERE idannuncio="+idannuncio+" AND account="+idutente
        db.execSQL(delete)
    }

    fun cancella2(){
        TODO("not implemented")
    }

    fun leggi(id: Int):List<JSONObject> {
        var list = arrayListOf<JSONObject>()
        val db = this.readableDatabase

        val query="SELECT * FROM zona,appartamento,annuncio WHERE account="+id
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) do {
            val json = JSONObject().apply {
                put("zona", result.getString(result.getColumnIndex("nome")))
                put("prezzo", result.getString(result.getColumnIndex("prezzo")).toInt())
                put("indirizzo", result.getString(result.getColumnIndex("indirizzo")))
                put("id",result.getString(result.getColumnIndex("idannuncio")).toInt())
            }
            list.add(json)
        } while (result.moveToNext())
        return list
    }

    fun inserisci(){
        TODO("not implemented")
    }

    fun zone(db: SQLiteDatabase,id: Int,nome: String ){
        var zona = ContentValues()
        zona.put("idzona", id)
        zona.put("nome",nome)
        db.insert("zona",null,zona)
    }

    fun inserisciZone(db: SQLiteDatabase){
        /*for(i in 0..22) {
            var cv = ContentValues()
            cv.put("idzona", i)
            cv.put("nome",zone[i])
            db.insert("zona",null,cv)
        }
        var cv2 = ContentValues()
        cv2.put("idannuncio",1)
        cv2.put("utente", 5)
        cv2.put("account",0)
        cv2.put("dataCreazione","27/05/2019" )
        cv2.put("prezzo",20)
        cv2.put("camera", "singola")
        cv2.put("idzona",1)
        cv2.put("idappartamento", 99)
        db.insert("annuncio",null,cv2)
        var cv3 = ContentValues()
        cv3.put("idappartamento",99)
        cv3.put("camereSingole",1)
        cv3.put("camereDoppie",0)
        cv3.put("camereTriple",0)
        cv3.put("bagni",1)
        cv3.put("Indirizzo","via montemagno")
        cv3.put("piano",5)
        db.insert("appartamento",null,cv3)*/
        var prova1="INSERT INTO appartamento (idappartamento,camereSingole,camereDoppie,camereTriple,bagni,Indirizzo,piano)"+
                   "VALUES(99,1,0,0,1,'Via Montemagno',5)"
        var prova2="INSERT INTO annuncio(idannuncio,utente,account,dataCreazione,prezzo,camera,idzona,idappartamento)"+
                   "VALUES(1,5,0,'27/05/2019',20,'singola',1,99)"
        db.execSQL(prova1)
        db.execSQL(prova2)

    }
}