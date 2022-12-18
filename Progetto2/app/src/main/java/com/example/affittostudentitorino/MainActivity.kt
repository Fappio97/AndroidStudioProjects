package com.example.affittostudentitorino

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v4.view.GravityCompat
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val coll=CollegamentoEsterno() // tramite esso uso i metodi della classe Collegamento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //crea la shared prefrence
    fun crea_sharedprefrence(json: JSONObject){
        val sharedPref =getSharedPreferences("info",Context.MODE_PRIVATE) ?: return
        val editor = sharedPref.edit()
        editor.putString("username",username.text.toString())
        editor.putInt("id",json.getInt("id"))
        editor.putString("email",json.getString("email"))
        editor.commit()
    }

    //l'utente preme sul bottone di login
    fun login(view: View) {
        var json=coll.Login(username.text.toString())
        if(username.text.toString()=="" || password.text.toString()=="") {
            errlogin.visibility=View.VISIBLE
            errlogin.text="Per continuare bisogna inserire sia l'username e la password corretta!"
        }
        else{
            val intent1 = Intent(this, HomePage::class.java).apply {
                putExtra(EXTRA_MESSAGE, "Bentornato")
            }
            crea_sharedprefrence(json)
            startActivity(intent1)
        }
    }

    //l'utente non ha un account
    fun registrazione(view: View) {
        val intent2=Intent(this, Nuovoaccount::class.java)
        startActivity(intent2)
    }

    //Uscita dall'app
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val sharedPref =getSharedPreferences("info",Context.MODE_PRIVATE) ?: return
        val string=sharedPref.getString("username", "XXXXX")
        if (string!="XXXXX") {
            val intent = Intent(this, HomePage::class.java).apply {
                putExtra(EXTRA_MESSAGE, "Bentornato")
            }
            startActivity(intent)
        }
    }
}
