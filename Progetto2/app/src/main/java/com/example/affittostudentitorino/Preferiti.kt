package com.example.affittostudentitorino

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ListView
import org.json.JSONObject

class Preferiti : AppCompatActivity() {

    //private var alertDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferiti)
        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)

        val db=DbManager(context = this)

        var list= db.elencoannunci(sharedPref.getInt("id",9999))

        var listView = findViewById<ListView>(R.id.pref)
        val adapter = Adapter(this, list as ArrayList<JSONObject>)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ -> //click singolo
            val selected = list[position].getInt("id")
            val intent = Intent(this, Annuncio1::class.java).apply {
                putExtra(EXTRA_MESSAGE,selected.toString())
                putExtra("ORIGINE","Preferiti")
            }
            startActivity(intent)
        }
    }
}
