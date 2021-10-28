package com.example.practice.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.practice.R

class SettingsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Log.d("Settings","press")
            finish()
            return true
        }

        return  super.onOptionsItemSelected(item)


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private val CONTACT_US_URL = "https://www.google.com/contact"

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val contactPref = findPreference<Preference>("web")
            if (contactPref != null) {
                contactPref.onPreferenceClickListener =
                    Preference.OnPreferenceClickListener { preference: Preference? ->
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(CONTACT_US_URL)
                        startActivity(intent)
                        true
                    }
            }
        }
    }
}