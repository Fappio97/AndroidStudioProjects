package it.unical.demacs.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailPerson : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_person)

        val username = intent.getStringExtra(PeopleFragment.PEOPLE_TAG)

        val person: DetailPersonFragment ?= username?.let { DetailPersonFragment.newInstance(it) }
        if (person != null) {
            supportFragmentManager.beginTransaction().add(R.id.container, person, PeopleFragment.PEOPLE_TAG).commit()
        }
    }
}