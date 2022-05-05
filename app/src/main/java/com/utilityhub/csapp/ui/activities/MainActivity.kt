package com.utilityhub.csapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            val navHostFragment = navHostFragment.getFragment<NavHostFragment>()
            val navController = navHostFragment.navController

            bottomNav.setupWithNavController(navController)
            this@MainActivity.bottomNav = bottomNav

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.loginFragment && bottomNav.visibility != View.GONE) {
                    bottomNav.visibility = View.GONE
                }else if (destination.id == R.id.mapsFragment && bottomNav.visibility == View.GONE) {
                    bottomNav.visibility = View.INVISIBLE
                }
            }
        }
    }

}