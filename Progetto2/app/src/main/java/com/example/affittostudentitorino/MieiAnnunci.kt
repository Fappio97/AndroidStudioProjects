package com.example.affittostudentitorino

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.ListView
import org.json.JSONObject

class MieiAnnunci : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_miei_annunci)
        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)

        val db=DbManager(context = this)

        var list= db.elencoannunci(sharedPref.getInt("id",9999))

        var listView = findViewById<ListView>(R.id.mieiann)
        val adapter = Adapter(this, list as ArrayList<JSONObject>)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ -> //click singolo
            val selected = list[position].getInt("id")
            val intent = Intent(this, Annuncio1::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE,selected.toString())
                putExtra("ORIGINE","MieiAnnunci")
            }
            startActivity(intent)
        }
    }
}