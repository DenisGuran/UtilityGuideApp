package com.example.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.csapp.Global
import com.example.csapp.R
import com.example.csapp.databinding.FragmentMolotovBinding

class MolotovFragment : Fragment(R.layout.fragment_molotov) {

    private var _binding: FragmentMolotovBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMolotovBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initData() {

        val molotovLayout = binding.molotovLayout

        if (Global.maps["mirage"] == true) {

            molotovLayout.setBackgroundResource(R.drawable.mirage_background_blur)
        }

        if (Global.maps["inferno"] == true) {

            molotovLayout.setBackgroundResource(R.drawable.inferno_background_blur)
        }

        if (Global.maps["dust2"] == true) {
            molotovLayout.setBackgroundResource(R.drawable.dust2_background_blur)
        }

        if (Global.maps["overpass"] == true) {
            molotovLayout.setBackgroundResource(R.drawable.overpass_background_blur)
        }

        if (Global.maps["nuke"] == true) {
            molotovLayout.setBackgroundResource(R.drawable.nuke_background_blur)
        }

        if (Global.maps["vertigo"] == true) {
            molotovLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
        }

        if (Global.maps["ancient"] == true) {
            molotovLayout.setBackgroundResource(R.drawable.ancient_background_blur)
        }

    }

}