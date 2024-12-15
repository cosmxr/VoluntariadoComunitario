package com.example.voluntariadocomunitario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, SearchFragment())
            }
        }

        // Fetch and display the list of events
        // fetchAndDisplayEvents()
    }

    private fun fetchAndDisplayEvents() {
        lifecycleScope.launch {
            val firebaseHelper = FirebaseHelper()
            val events = firebaseHelper.getVoluntaryActs()
            // Pass the events to the fragment or update the UI directly
        }
    }
}