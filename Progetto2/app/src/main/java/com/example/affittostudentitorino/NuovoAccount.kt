package com.example.affittostudentitorino

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_nuovoaccount.*
import kotlinx.android.synthetic.main.spinner_item.*
import org.json.JSONObject

class Nuovoaccount : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    private val coll=CollegamentoEsterno() // tramite esso uso i metodi della classe Collegamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuovoaccount)

        val spinner: Spinner = findViewById(R.id.corso)
        spinner.onItemSelectedListener = this

        // Crea un arrayadapter usando un array di stringhe
        ArrayAdapter.createFromResource(
            this,R.array.lista_corsi, R.layout.spinner_item).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)//uso un mio layout
            spinner.adapter = adapter//applica l'adapter allo spinner
        }
    }

    //Un elemento viene selezionato e recuperato
     override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        parent.getItemAtPosition(pos)
    }

    //metodo interfaccia
    override fun onNothingSelected(parent: AdapterView<*>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //crea la shared prefrence
    fun crea_sharedprefrence(json: JSONObject){
        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE) ?: return
        val editor = sharedPref.edit()
        editor.putString("username",username2.text.toString())
        editor.putInt("id",json.getInt("id"))
        editor.putString("email",json.getString("email"))
        editor.commit()
    }
    //Controlla che i campi siano inseriti correttamente
    fun controlla_campi():String{
        val spinner: Spinner = findViewById(R.id.corso)
        if(nome.text.toString()=="")
            return "Riempire anche il campo 'nome'!"
        if(cognome.text.toString()=="")
            return "Riempire anche il campo 'cognome'!"
        if(username2.text.toString()=="")
            return "Riempire anche il campo 'username'!"
        if(email.text.toString()=="" || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())
            return "Campo 'email' vuoto o email non esistente"
        if(password2.text.toString()=="")
            return "Riempire anche il campo 'password'!"
        if(telefono.text.toString()=="" || telefono.text.toString().length<10)
            return "Campo 'telefono' vuoto o numero di telefono non esistente"
        if(this.studente.isChecked && (matricola.text.toString()=="" || spinner.selectedItem.toString()==""))
            return "Se sei uno studente devi riempire anche i campi 'matricola' e 'corso'"
        return "OK"
    }

    //l'utente si registra
    fun registrati(view: View) {
        val stringa=controlla_campi()
        var json=coll.Register(username2.text.toString(),email.text.toString())
        if(stringa=="OK"){
            val intent = Intent(this, HomePage::class.java).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Benvenuto")
            }
            crea_sharedprefrence(json)
            startActivity(intent)
        }
        else{
            errreg.visibility=View.VISIBLE
            errreg.text=stringa
        }
    }
    //mostra/nasconde le editext relative allo studente
    fun sei_studente(view: View){
        if (!this.studente.isChecked){
            corso.visibility=View.GONE
            matricola.visibility=View.GONE
        }
        else{
            corso.visibility=View.VISIBLE
            matricola.visibility=View.VISIBLE
        }

    }
}