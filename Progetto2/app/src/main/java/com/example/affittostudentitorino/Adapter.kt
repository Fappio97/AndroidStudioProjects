package com.example.affittostudentitorino

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.json.JSONObject

class Adapter (private val context: Context, private val dataSource: ArrayList<JSONObject>) : BaseAdapter() {
    private val zone=arrayOf("Centro Storico"," San Salvario","Crocetta","San Paolo","Cenisia","Campidoglio","Aurora","Vanchiglia","Nizza","Lingotto", "Santa Rita","Mirafiori Nord","Pozzo Strada","Parella","Le Vallette","Lanzo","Borgata Vittoria","Barriera di Milano","Falchera","Barca- Bertolla","Madonna del Pilone", "Borgo Po","Mirafiori Sud")
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.listview_item, parent, false)

        val annuncioTextView = rowView.findViewById(R.id.annuncio) as TextView
        val prezzoTextView = rowView.findViewById(R.id.prezzo) as TextView
        val luogoTextView = rowView.findViewById(R.id.luogo) as TextView
        val idTextView = rowView.findViewById(R.id.ID) as TextView

        val item = getItem(position) as JSONObject
        val idzona=item.getInt("idzona")

        annuncioTextView.text = "In zona "+ zone[idzona] //ann
        prezzoTextView.text =item.getInt("prezzo").toString() +" euro al mese"//prezzo
        luogoTextView.text = item.getString("indirizzo") //luogo
        idTextView.text=item.getInt("id").toString()

        return rowView
    }
}