package com.example.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.csapp.Global
import com.example.csapp.R
import com.example.csapp.databinding.FragmentFlashBinding

class FlashFragment : Fragment(R.layout.fragment_flash) {

    private var _binding: FragmentFlashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    private fun initData(){

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