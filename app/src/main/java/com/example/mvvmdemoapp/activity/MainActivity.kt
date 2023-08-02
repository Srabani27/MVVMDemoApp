package com.example.mvvmdemoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvmdemoapp.R
import com.example.mvvmdemoapp.fragment.ContactStatusFragment
import com.example.mvvmdemoapp.fragment.LocationMapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(LocationMapFragment())


        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.navigation_location_track -> {
                    loadFragment(LocationMapFragment())

                    return@setOnNavigationItemReselectedListener
                }
                R.id.navigation_contact_status -> {
                    loadFragment(ContactStatusFragment())
                    return@setOnNavigationItemReselectedListener
                }

            }
        }
    }

    private fun loadFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
