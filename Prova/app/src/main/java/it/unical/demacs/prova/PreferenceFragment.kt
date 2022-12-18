package it.unical.demacs.prova

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.*
import it.unical.demacs.prova.R.xml.preference
import java.lang.RuntimeException

/**
 * A simple [Fragment] subclass.
 * Use the [PreferenceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchDarkMode: SwitchPreferenceCompat? = findPreference("key_dark_theme")
        val check: CheckBoxPreference? = findPreference("usa_titolo_custom")
        val testo: EditTextPreference? = findPreference("titolo")
        val colore: ListPreference? = findPreference("colore")

        Log.d("DEBUG", "FUORI IF")
        if (colore != null) {
            Log.d("DEBUG", "DENTRO IF")
            colore.summary = colore.entry
        }

        if(check != null) {
            testo!!.summary = R.string.titolo_custom.toString()
            if(check.isChecked) testo!!.summary = testo!!.text
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
        // Get the switch preference
        val switchDarkMode: SwitchPreferenceCompat? = findPreference("key_dark_theme")
        val check: CheckBoxPreference? = findPreference("usa_titolo_custom")
        val testo: EditTextPreference? = findPreference("titolo")
        val colore: ListPreference? = findPreference("colore")

        // Switch preference change listener
        switchDarkMode?.setOnPreferenceChangeListener{ preference, newValue ->
            if (newValue == true){
                switchDarkMode.summary = "Enabled"
                Toast.makeText(activity,"enabled",Toast.LENGTH_LONG).show()
            }else{
                switchDarkMode.summary = "Disabled"
                Toast.makeText(activity,"disabled",Toast.LENGTH_LONG).show()
            }

            true
        }

        testo?.setOnPreferenceChangeListener{ preference, newValue ->
            Log.d("DEbug", newValue.toString())
            Log.d("DEbug", preference.toString())
            testo.summary = newValue.toString()
            testo.text = newValue.toString()
            (activity as PreferenceActivity).cambiaSettings()
            true
        }

        check?.setOnPreferenceChangeListener { preference, newValue ->
            if(newValue == true && testo != null) {
                testo.summary = testo.text
                check.isChecked = true
            } else {
                testo?.summary = getString(R.string.titolo_custom)
                check.isChecked = false
            }
            (activity as PreferenceActivity).cambiaSettings()
            true
        }

        colore?.setOnPreferenceChangeListener { preference, newValue ->
            colore.value = newValue.toString()
            colore.summary = colore.entry
            (activity as PreferenceActivity).cambiaSettings()
            true
        }


    }

    companion object {

        val TAG: String = "PreferenceFragment"

        val COLORE_DEFAULT: String  = "#000000";

        val COLORE_PREF: String = "colore";

        val TITOLO_PREF: String = "titolo";

        val USA_TITOLO_CUSTOM_PREF: String = "usa_titolo_custom";

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PreferenceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = PreferenceFragment()
    }
}