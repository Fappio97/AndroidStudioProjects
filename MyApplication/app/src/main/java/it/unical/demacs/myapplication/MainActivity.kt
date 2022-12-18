package it.unical.demacs.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import it.unical.demacs.myapplication.Login.Companion.instance

class MainActivity : AppCompatActivity(), LoginFragment.OnLoginFragmentInteractionListener {

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment: LoginFragment = LoginFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment, LoginFragment.TAG).commit()
    }

    override fun onLoginButtonPressed(username: String) {
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.putExtra(LoginFragment.TAG, username)
//        Log.d("DEBUG_INTENT_LOGIN", "$username")
        startActivity(intent)
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}

