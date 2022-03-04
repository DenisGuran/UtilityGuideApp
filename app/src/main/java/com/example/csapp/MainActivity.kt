package com.example.csapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.csapp.fragments.MapsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn : Button = findViewById(R.id.btnmaps)
        btn.setOnClickListener {
            val fragmentManager  = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            btn.visibility = View.GONE
            fragmentTransaction.replace(R.id.main_activity_fragment, MapsFragment())
            fragmentTransaction.commit()
        }



    }
}
