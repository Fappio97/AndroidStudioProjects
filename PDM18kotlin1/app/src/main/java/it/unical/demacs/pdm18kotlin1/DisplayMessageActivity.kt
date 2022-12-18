package it.unical.demacs.pdm18kotlin1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.TypedValue
import android.widget.TextView

class DisplayMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // prendiamo il messaggio dall'intento
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        // creazione del textView
        var textView = TextView(this)

        // grandezza text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40f)
        // assegnamo alla textView il testo messaggio
        textView.text = message

        // aggiungiamo al root view l'elemento textView
        setContentView(textView)
    }
}