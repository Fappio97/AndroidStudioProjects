package it.unical.demacs.prova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity(), SecondFragment.OnSecondFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val testo = intent.getStringExtra(FirstFragment.TAG)

        val secondFragment = SecondFragment.newInstance(testo!!)
        supportFragmentManager.beginTransaction()
            .replace(R.id.second_activity, secondFragment, SecondFragment.TAG).commit()

    }

    override fun onFirstActivity(toString: String) {
 /*       val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(SecondFragment.TAG, toString)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)*/
        finish()
    }
}