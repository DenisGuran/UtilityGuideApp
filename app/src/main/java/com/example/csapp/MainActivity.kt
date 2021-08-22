package com.example.csapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.csapp.Global.Companion.maps
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        card_mirage.setOnClickListener {
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["mirage"] = true
        }

        card_inferno.setOnClickListener{
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["inferno"] = true
        }

        card_dust2.setOnClickListener{
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["dust2"] = true
        }

        card_overpass.setOnClickListener{
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["overpass"] = true
        }

        card_nuke.setOnClickListener{
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["nuke"] = true
        }

        card_vertigo.setOnClickListener{
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["vertigo"] = true
        }

        card_ancient.setOnClickListener{
            startActivity(Intent(this,NaviActivity::class.java))
            for (l in maps)
                l.setValue(false)
            maps["ancient"] = true
        }

    }
}
