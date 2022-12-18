package it.unical.demacs.prova

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_preference.*

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val firstFragment: PreferenceFragment = PreferenceFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.preferences_activity, firstFragment, PreferenceFragment.TAG).commit()

// Get the preferences
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Get the user dark theme settings
        val isDarkTheme = prefs.getBoolean("key_dark_theme",false)
    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        Log.d("DEBUG", "RESUME")
        var colore: String = prefs.getString("colore", "") ?: ""
        Log.d("DEBUG", colore)
        var color = Color.parseColor(colore)
        Log.d("DEBUG", color.toString())
        preferences_activity.setBackgroundColor(color)

        var titolo: String = if(prefs.getBoolean("usa_titolo_custom", true)) {
            Log.d("DEBUG", "QUI")
            prefs.getString("titolo", getString(R.string.titolo_custom))!!
        } else {
            Log.d("DEBUG", "QUA")
            getString(R.string.app_name);
        }
        setTitle(titolo);
    }

    fun cambiaSettings() {
        Log.d("DEBUG", "QUIIIIIIIIIIIIIIi")
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        var colore: String = prefs.getString("colore", "") ?: ""

        var color = Color.parseColor(colore)

        preferences_activity.setBackgroundColor(color)

        var titolo: String = if(prefs.getBoolean("usa_titolo_custom", true)) {
            Log.d("DEBUG", "QUI")
            prefs.getString("titolo", getString(R.string.titolo_custom))!!
        } else {
            Log.d("DEBUG", "QUA")
            getString(R.string.app_name);
        }
        setTitle(titolo);
    }

}