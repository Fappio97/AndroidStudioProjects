@file:Suppress("DEPRECATION")

package com.example.affittostudentitorino

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.app_bar_home_page.*
import android.support.v4.widget.DrawerLayout
import android.widget.Toast
import android.support.v7.widget.Toolbar
import android.util.Log

class HomePage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val toolbar= findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val sharedPref =getSharedPreferences("info", Context.MODE_PRIVATE)
        welcomes.apply {
            text = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE) +" "+ sharedPref.getString("username", "errore nel salvatggio")
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {//uscita dall'app
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // mostra a schermo il menu
        menuInflater.inflate(R.menu.home_page, menu)
        return true
    }
    //comportamento menu opzioni
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            val intent = Intent(this, Help::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    //comportamento menu laterale
    fun displaySelectedScreen(item: MenuItem){
        val id = item.itemId
        when (id) {
            R.id.profilo->menu(1)
            R.id.preferiti->menu(2)
            R.id.nuovi->menu(3)
            R.id.ricerca->menu(4)
            R.id.annuncimiei->menu(5)
            R.id.logouts->menu(6)
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }
    //funzione ausiliare che agisce in base a quale elemento del menu viene chiamato
    fun menu(number: Int){
        if(number==1){//VediProfilo
            val intent = Intent(this, VediProfilo::class.java)
            startActivity(intent)
        }
        if(number==2){//Preferiti
            val intent = Intent(this, Preferiti::class.java)
            startActivity(intent)
        }
        if(number==3){//Nuovo annuncio
            val intent = Intent(this, NuovoAnnuncio::class.java)
            startActivity(intent)
        }
        if(number==4){//Avvia ricerca
            val intent = Intent(this, Ricerca::class.java)
            startActivity(intent)
        }
        if(number==5){//MieiAnnunci
            val intent = Intent(this, MieiAnnunci::class.java)
            startActivity(intent)
        }
        if(number==6){//Logout
            val sharedPrefs =getSharedPreferences("info",Context.MODE_PRIVATE)
            sharedPrefs.edit().clear().apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item)
        return true
    }
}
