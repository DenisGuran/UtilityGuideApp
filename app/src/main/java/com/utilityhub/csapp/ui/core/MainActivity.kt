package com.utilityhub.csapp.ui.core

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
                bottomNav.apply {
                    if (destination.id == R.id.loginFragment &&
                        visibility != View.GONE
                    ) {
                        visibility = View.GONE
                    } else if (destination.id == R.id.mapsFragment) {
                        if (visibility == View.GONE) {
                            visibility = View.INVISIBLE
                        }
                        if (menu.getItem(0).itemId == R.id.nav_utility_smoke) {
                            menu.clear()
                            inflateMenu(R.menu.home_menu)
                        }
                    } else if (destination.id == R.id.smokeFragment &&
                        menu.getItem(0).itemId == R.id.mapsFragment
                    ) {
                        menu.clear()
                        inflateMenu(R.menu.utility_menu)
                    }
                }
            }
        }
    }
}