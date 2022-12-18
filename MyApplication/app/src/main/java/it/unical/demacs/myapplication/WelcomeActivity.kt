package it.unical.demacs.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class WelcomeActivity : AppCompatActivity(), WelcomeFragment.OnWelcomeFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra(LoginFragment.TAG)

        val welcome: WelcomeFragment?= WelcomeFragment.newInstance(username!!)
        if (welcome != null) {
            supportFragmentManager.beginTransaction().add(com.google.android.material.R.id.container, welcome, WelcomeFragment.TAG).commit()
        }

    }

    override fun logout() {
        finish()
    }

    override fun goToMainSection() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}