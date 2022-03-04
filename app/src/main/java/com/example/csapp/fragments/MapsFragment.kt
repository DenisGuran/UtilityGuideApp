package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.csapp.Global
import com.example.csapp.NaviActivity
import com.example.csapp.R
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(R.layout.fragment_maps) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_mirage.setOnClickListener {
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["mirage"] = true
        }

        card_inferno.setOnClickListener{
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["inferno"] = true
        }

        card_dust2.setOnClickListener{
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["dust2"] = true
        }

        card_overpass.setOnClickListener{
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["overpass"] = true
        }

        card_nuke.setOnClickListener{
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["nuke"] = true
        }

        card_vertigo.setOnClickListener{
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["vertigo"] = true
        }

        card_ancient.setOnClickListener{
            startActivity(Intent(activity, NaviActivity::class.java))
            for (l in Global.maps)
                l.setValue(false)
            Global.maps["ancient"] = true
        }
    }

}