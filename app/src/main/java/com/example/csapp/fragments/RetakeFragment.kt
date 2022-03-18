package com.example.csapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.csapp.Global
import com.example.csapp.R
import com.example.csapp.databinding.FragmentRetakeBinding


class RetakeFragment : Fragment(R.layout.fragment_retake) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRetakeBinding.bind(view)

        binding.apply {
            initData(this)
        }
    }

    private fun initData(binding: FragmentRetakeBinding){

        val retakeLayout = binding.retakeLayout

        if(Global.maps["mirage"] == true) {

            retakeLayout.setBackgroundResource(R.drawable.mirage_background_blur)
        }

        if(Global.maps["inferno"] == true) {

            retakeLayout.setBackgroundResource(R.drawable.inferno_background_blur)
        }

        if(Global.maps["dust2"] == true) {
            retakeLayout.setBackgroundResource(R.drawable.dust2_background_blur)
        }

        if(Global.maps["overpass"] == true) {
            retakeLayout.setBackgroundResource(R.drawable.overpass_background_blur)
        }

        if(Global.maps["nuke"] == true) {
            retakeLayout.setBackgroundResource(R.drawable.nuke_background_blur)
        }

        if(Global.maps["vertigo"] == true) {
            retakeLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
        }

        if(Global.maps["ancient"] == true) {
            retakeLayout.setBackgroundResource(R.drawable.ancient_background_blur)
        }

    }

}