package com.utilityhub.csapp.ui.utility.landing

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.FragmentHeGrenadeBinding
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeGrenadeFragment : BaseFragment<FragmentHeGrenadeBinding>(FragmentHeGrenadeBinding::inflate) {

    private val viewModel by hiltNavGraphViewModels<LandViewModel>(R.id.nav_utility)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressedGoToMaps()
    }

    private fun onBackPressedGoToMaps() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                    findNavController().setGraph(R.navigation.nav_graph)
                    val navHome = MapsFragmentDirections.actionGlobalHome()
                    findNavController().navigate(navHome)
                }
            })
    }

}