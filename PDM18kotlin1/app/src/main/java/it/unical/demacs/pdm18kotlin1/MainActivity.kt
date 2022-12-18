package it.unical.demacs.pdm18kotlin1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
// serve uesto import per prendere gli oggetti view più facilmente
import kotlinx.android.synthetic.main.activity_main.*
const val EXTRA_MESSAGE = "it.unical.demacs.pdm18kotlin1.MESSAGE"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // OPZIONE 2: all'interno del metodo onCreate definiamo il click sul bottone
        // con il relativo comportamento
 /*
        id_button.setOnClickListener() {
            val intent = Intent(this, DisplayMessageActivity::class.java)
            val m = message.text.toString()
            intent.putExtra(EXTRA_MESSAGE, m)
            startActivity(intent)
        }
   */
        // OPZIONE 3: all'interno del metodo onCreate definiamo il click sul bottone
        // ma poi chiamiamo una funzione esterna
        id_button.setOnClickListener() {
            sendMessage()
        }
    }
    /* OPZIONE 1: dove nell'XML mettiamo nel button
    fun sendMessage(view : View) {
        val intent = Intent(this, DisplayMessageActivity::class.java)
        val m = message.text.toString()
        intent.putExtra(EXTRA_MESSAGE, m)
        startActivity(intent)
    }
    */

    fun sendMessage() {
        val intent = Intent(this, DisplayMessageActivity::class.java)
        val m = message.text.toString()
        intent.putExtra(EXTRA_MESSAGE, m)
        startActivity(intent)
    }
}