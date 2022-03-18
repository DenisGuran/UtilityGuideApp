package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.Global
import com.example.csapp.activities.NaviActivity
import com.example.csapp.R
import com.example.csapp.databinding.FragmentMapsBinding

class MapsFragment : Fragment(R.layout.fragment_maps) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMapsBinding.bind(view)

        binding.apply {
            buttonProfile.setOnClickListener {
                findNavController().navigate(R.id.profileFragment)
            }
            cardMirage.setOnClickListener {
                startActivity(Intent(activity, NaviActivity::class.java))
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["mirage"] = true
            }

            cardInferno.setOnClickListener{
                startActivity(Intent(activity, NaviActivity::class.java))
                activity?.finish()
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["inferno"] = true
            }

            cardDust2.setOnClickListener{
                startActivity(Intent(activity, NaviActivity::class.java))
                activity?.finish()
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["dust2"] = true
            }

            cardOverpass.setOnClickListener{
                startActivity(Intent(activity, NaviActivity::class.java))
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["overpass"] = true
            }

            cardNuke.setOnClickListener{
                startActivity(Intent(activity, NaviActivity::class.java))
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["nuke"] = true
            }

            cardVertigo.setOnClickListener{
                startActivity(Intent(activity, NaviActivity::class.java))
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["vertigo"] = true
            }

            cardAncient.setOnClickListener{
                startActivity(Intent(activity, NaviActivity::class.java))
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["ancient"] = true
            }
        }
    }

}