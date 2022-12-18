package com.example.affittostudentitorino

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class dbHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    private val zone=arrayOf("Centro Storico"," San Salvario","Crocetta","San Paolo","Cenisia","Campidoglio","Aurora","Vanchiglia","Nizza","Lingotto", "Santa Rita","Mirafiori Nord","Pozzo Strada","Parella","Le Vallette","Lanzo","Borgata Vittoria","Barriera di Milano","Falchera","Barca- Bertolla","Madonna del Pilone", "Borgo Po","Mirafiori Sud")
    private var createTable1 ="CREATE TABLE IF NOT EXISTS zona(idzona int PRIMARY KEY NOT NULL,nome varchar(50) NOT NULL DEFAULT '0')"
    private var createTable2 ="CREATE TABLE IF NOT EXISTS appartamento( idappartamento int PRIMARY KEY NOT NULL , camereSingole int NOT NULL DEFAULT '0' , camereDoppie int NOT NULL DEFAULT '0' , camereTriple int NOT NULL DEFAULT '0' , bagni int NOT NULL DEFAULT '0' , indirizzo varchar(50) NOT NULL DEFAULT '0' , piano int NOT NULL DEFAULT '0')"
    private var createTable3 ="CREATE TABLE IF NOT EXISTS annuncio( idannuncio int PRIMARY KEY NOT NULL, utente int  NOT NULL,telefono varchar(10) NOT NULL,email varchar(50) NOT NULL, account int NOT NULL, datacreazione varchar(50) NOT NULL, prezzo int NOT NULL, camera varchar(50) NOT NULL, idzona int DEFAULT NULL, idappartamento int NOT NULL, CONSTRAINT FK_annuncio_appartamento  FOREIGN KEY (idappartamento) REFERENCES appartamento (idappartamento) ON DELETE CASCADE ON UPDATE CASCADE,CONSTRAINT FK_annuncio_zona FOREIGN KEY (idzona) REFERENCES zona (idzona) ON DELETE SET NULL ON UPDATE CASCADE)"

    private fun inizializza(db: SQLiteDatabase) {
        for(i in 0..22) {
            inseriscizone(db,i,zone[i])
        }
        var ida1=34; var cs1=1; var cd1=0; var ct1=0; var b1=1; var ind1="Via Montemagno";  var p1=5
        var ida2=35; var cs2=1; var cd2=0; var ct2=0; var b2=1; var ind2="Via Montemagnos"; var p2=5
        var idan1=1; var u1=5;  var a1=0;  var d1="27/05/2019"; var pz1=20; var c1="singola"; var idz1=6;
        var idan2=11;var u2=3;  var a2=0; var d2="27/05/2019"; var pz2=21; var c2="singola"; var idz2=1;
        /*var prova1= "INSERT INTO `annuncio` (`idannuncio`, `utente`, `account`, `datacreazione`, `prezzo`, `camera`, `idzona`, `idappartamento`) VALUES\n" +
                "(1, 5, 0, '27/05/2019', 20, 'singola', 6, 34),\n" +
                "(11, 3, 56, '27/05/2019', 21, 'singola', 1, 35);"*/
        var prova1="INSERT INTO `appartamento` (`idappartamento`, `camereSingole`, `camereDoppie`, `camereTriple`, `bagni`, `indirizzo`, `piano`) VALUES\n" +
                /*"($ida1, $cs1, $cd1, $ct1, $b1, 'Via montemagno', $p1),\n" +
                "($ida2, $cs2, $cd2, $ct2, $b2, 'Via montemagnos', $p2);"*/
                "(34,1,0,0,1,'Via collina',5),\n" +
                "(35,1,0,0,1,'Via monte',2);"
        db.execSQL(prova1)
        var prova2="INSERT INTO `annuncio` (`idannuncio`, `utente`,`telefono`,`email`, `account`, `datacreazione`, `prezzo`, `camera`, `idzona`, `idappartamento`) VALUES\n" +
                /*"($idan1, $u1,'4867546588','utente@gmail.com', $a1, $d1, $pz1, 'singola', $idz1 , $ida1),\n" +
                "($idan2, $u2,'4589612677','utentes@gmail.com', $a2, $d2, $pz2, 'singola', $idz2 ,$ida2);"*/
                "(1,5,'4867546588','utente@gmail.com',0,'27/05/2019',20,'singola',6,34),\n" +
                "(11,3,'4589612677','utentes@gmail.com',0,'27/05/2019',21,'singola',1,35);"
        db.execSQL(prova2)
    }

    fun inseriscizone(db: SQLiteDatabase,id: Int,nome: String){
        val insert = "INSERT INTO zona (idzona,nome) VALUES ($id,'$nome')"
        db.execSQL(insert)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTable1)
        db.execSQL(createTable2)
        db.execSQL(createTable3)
        inizializza(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}