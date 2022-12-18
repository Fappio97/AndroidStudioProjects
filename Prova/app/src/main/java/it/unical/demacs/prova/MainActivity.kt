package it.unical.demacs.prova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), FirstFragment.OnFirstFragment {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment: FirstFragment = FirstFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.first_activity, firstFragment, FirstFragment.TAG).commit()

    }

    override fun onSecondActivity(string: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(FirstFragment.TAG, string)
        startActivity(intent)
    }

    override fun onPreferenceActivity() {
        val intent = Intent(this, PreferenceActivity::class.java)
        startActivity(intent)
    }

    override fun onTaskActivity() {
        val intent = Intent(this, TaskActivity::class.java)
        startActivity(intent)
    }

}