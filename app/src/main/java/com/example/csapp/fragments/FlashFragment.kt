package com.example.csapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.csapp.Global
import com.example.csapp.R
import com.example.csapp.databinding.FragmentFlashBinding

class FlashFragment : Fragment(R.layout.fragment_flash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFlashBinding.bind(view)

        binding.apply {
            initData(this)
        }
    }

    private fun initData(binding: FragmentFlashBinding){

        val flashLayout = binding.flashLayout

        if(Global.maps["mirage"] == true) {
            flashLayout.setBackgroundResource(R.drawable.mirage_background_blur)
        }

        if(Global.maps["inferno"] == true) {
            flashLayout.setBackgroundResource(R.drawable.inferno_background_blur)
        }

        if(Global.maps["dust2"] == true) {
            flashLayout.setBackgroundResource(R.drawable.dust2_background_blur)
        }

        if(Global.maps["overpass"] == true) {
            flashLayout.setBackgroundResource(R.drawable.overpass_background_blur)
        }

        if(Global.maps["nuke"] == true) {
            flashLayout.setBackgroundResource(R.drawable.nuke_background_blur)
        }

        if(Global.maps["vertigo"] == true) {
            flashLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
        }

        if(Global.maps["ancient"] == true) {
            flashLayout.setBackgroundResource(R.drawable.ancient_background_blur)
        }

    }

}