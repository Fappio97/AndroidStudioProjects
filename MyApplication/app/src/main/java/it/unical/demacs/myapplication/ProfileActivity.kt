package it.unical.demacs.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProfileActivity : AppCompatActivity(), PeopleFragment.OnPeopleFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val fragment: PeopleFragment = PeopleFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.people_container, fragment, LoginFragment.TAG).commit()
    }

    override fun onPersonListener(username: String) {
        val intent = Intent(this, DetailPerson::class.java)
        intent.putExtra(PeopleFragment.PEOPLE_TAG, username)
        startActivity(intent)
    }
}