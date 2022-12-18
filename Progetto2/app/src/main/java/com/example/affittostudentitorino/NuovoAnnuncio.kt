package com.example.affittostudentitorino

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nuovo_annuncio.*
import kotlinx.android.synthetic.main.activity_nuovoaccount.*
import org.json.JSONObject

class NuovoAnnuncio : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val coll = CollegamentoEsterno() // tramite esso uso i metodi della classe Collegamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuovo_annuncio)

        val spinner: Spinner = findViewById(R.id.zona)
        val sing: Spinner = findViewById(R.id.singola)
        val dop: Spinner = findViewById(R.id.doppia)
        val trip: Spinner = findViewById(R.id.tripla)
        val bag: Spinner = findViewById(R.id.bagno)

        spinner.onItemSelectedListener = this
        sing.onItemSelectedListener = this
        dop.onItemSelectedListener = this
        trip.onItemSelectedListener = this
        bag.onItemSelectedListener = this
        creaSpinners()
    }

    fun creaSpinners(){
        val spinner: Spinner = findViewById(R.id.zona)
        val sing: Spinner = findViewById(R.id.singola)
        val dop: Spinner = findViewById(R.id.doppia)
        val trip: Spinner = findViewById(R.id.tripla)
        val bag: Spinner = findViewById(R.id.bagno)
        //Crea un arraydapter per le stanze
        ArrayAdapter.createFromResource(
            this, R.array.lista_numeri, R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)//uso un mio layout
            sing.adapter = adapter
            dop.adapter = adapter
            trip.adapter = adapter
            bag.adapter = adapter
        }
        // Crea un arrayadapter per le zone
        ArrayAdapter.createFromResource(this, R.array.lista_zone, R.layout.spinner_item).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)//uso un mio layout
            spinner.adapter = adapter//applica l'adapter allo spinner
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //Un elemento viene selezionato e recuperato
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }

    //Controlla che i campi siano inseriti correttamente
    fun controlla_campi(): String {
        val spinner: Spinner = findViewById(R.id.zona)
        val sing: Spinner = findViewById(R.id.singola)
        val dop: Spinner = findViewById(R.id.doppia)
        val trip: Spinner = findViewById(R.id.tripla)
        val bag: Spinner = findViewById(R.id.bagno)
        if (indirizzo.text.toString() == "")
            return "campo 'indirizzo' vuoto"
        if (spinner.selectedItem.toString() == "")
            return "campo 'zona' vuoto"
        if (prezzo2.text.toString() == "")
            return "campo 'prezzo' vuoto"
        if (sing.selectedItem.toString() == "")
            return "inserire numero stanze singole"
        if (dop.selectedItem.toString() == "")
            return "inserire numero stanze doppie"
        if (trip.selectedItem.toString() == "")
            return "inserire numero stanze triple"
        if (bag.selectedItem.toString() == "")
            return "inserire numero bagni"
        return "OK"
    }

    //Inserisce un nuovo annuncio
    fun inserisci(view: View) {
        val stringa = controlla_campi()
        if(stringa=="OK") {
            val spinner: Spinner = findViewById(R.id.zona)
            val sing: Spinner = findViewById(R.id.singola)
            val dop: Spinner = findViewById(R.id.doppia)
            val trip: Spinner = findViewById(R.id.tripla)
            val bag: Spinner = findViewById(R.id.bagno)
            val json = JSONObject().apply {
                put("indirizzo", indirizzo.text.toString())
                put("zona", spinner.selectedItem.toString())
                put("prezzo", prezzo2.text)
                put("singola", sing.selectedItem)
                put("doppia", dop.selectedItem)
                put("tripla", trip.selectedItem)
                put("bagno", bag.selectedItem)
            }
            coll.inserisci(json)
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
        else
            errann.visibility=View.VISIBLE
            errann.text=stringa
    }
}
