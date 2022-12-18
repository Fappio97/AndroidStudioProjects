package com.example.affittostudentitorino

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_main.*


class Help : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }
    //Manda un email tramite app esterna
    fun mandaEmail(view: View){
        if(soggetto.text.toString()==""){
            Toast.makeText(this, "Per continuare bisogna inserire almeno l'oggetto dell'email", Toast.LENGTH_SHORT).show()
        }
        else {
            var emails: Array<String> = arrayOf("loreg9552@gmail.com", "diego@gmail.com")
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, emails)
                putExtra(Intent.EXTRA_SUBJECT, soggetto.text.toString())
                putExtra(Intent.EXTRA_TEXT, messaggio.text.toString())
                type = "plain/text"
            }
            startActivity(Intent.createChooser(intent, "Scegli un client da aprire per inviare l'email"))
        }
    }
}
