package com.example.csapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.csapp.R
import com.example.csapp.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

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
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id in listOf(
                        R.id.loginFragment,
                        R.id.registerFragment,
                        R.id.forgotPasswordFragment
                    )
                ) {
                    bottomNav.visibility = View.GONE
                }
                if (destination.id == R.id.mapsFragment && bottomNav.visibility == View.GONE) {
                    bottomNav.visibility = View.INVISIBLE
                }
            }
        }
    }

}