package com.example.affittostudentitorino

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_ricerca.*
import org.json.JSONObject

class Ricerca : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ricerca)
        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)

        val spinner1: Spinner = findViewById(R.id.zona3)
        spinner1.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this, R.array.lista_zone, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)//uso un mio layout
            spinner1.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.tipo2)
        spinner2.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this, R.array.lista_affitti, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)//uso un mio layout
            spinner2.adapter = adapter
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //Un elemento viene selezionato e recuperato
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }
    //Dato almeno uno dei 4 filtri fa partire la ricerca
    fun ricerca(view: View){
        val spinner1: Spinner = findViewById(R.id.zona3)
        val spinner2: Spinner = findViewById(R.id.tipo2)
        if(spinner1.selectedItem.toString() == "" && spinner2.selectedItem.toString() == "" && prezzomin.text.toString() == "" && prezzomax.text.toString() == "") {
            errric.visibility = View.VISIBLE
        }
        else{
            val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)
            val db=DbManager(context = this)
            var list= db.elencoannunci(sharedPref.getInt("id",9999))

            var listView = findViewById<ListView>(R.id.list_view2)
            val adapter = Adapter(this, list as ArrayList<JSONObject>)
            listView.adapter = adapter

            listView.setOnItemClickListener { _, _, position, _ -> //click singolo
                val selected = list[position].getInt("id")
                val intent = Intent(this, Annuncio2::class.java).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE,selected.toString())
                }
                startActivity(intent)
            }
        }
    }
}
