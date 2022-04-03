package com.utilityhub.csapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.ActivityAuthenticationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.apply {
            bottomNav.setupWithNavController(navController)
            this@AuthenticationActivity.bottomNav = bottomNav
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