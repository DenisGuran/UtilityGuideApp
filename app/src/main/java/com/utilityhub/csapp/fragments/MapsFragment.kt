package com.utilityhub.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.Global
import com.utilityhub.csapp.R
import com.utilityhub.csapp.activities.AuthenticationActivity
import com.utilityhub.csapp.databinding.FragmentMapsBinding
import com.utilityhub.csapp.utils.Constants
import kotlin.collections.set

class MapsFragment : Fragment(R.layout.fragment_maps) {

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        doubleTapToExit()
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as AuthenticationActivity).bottomNav
        if (bottomNav.visibility == View.INVISIBLE) {
            bottomNav.visibility = View.VISIBLE
        }
        if (bottomNav.menu.getItem(0).itemId == R.id.nav_utility_smoke) {
            bottomNav.menu.clear()
            bottomNav.inflateMenu(R.menu.home_menu)
        }
    }

    private fun doubleTapToExit() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressedTime + Constants.EXIT_TIME > System.currentTimeMillis()) {
                        backPressedToast.cancel()
                        requireActivity().finish()
                        return
                    } else {
                        backPressedToast =
                            Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT)
                        backPressedToast.show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })
    }

    private fun initData() {
        binding.apply {
            cardMirage.setOnClickListener {
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["mirage"] = true
            }

            cardInferno.setOnClickListener {
                val navUtilities = MapsFragmentDirections.actionGlobalNavUtility()
                findNavController().navigate(navUtilities)
                findNavController().setGraph(R.navigation.nav_utility)
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["inferno"] = true
            }

            cardDust2.setOnClickListener {
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["dust2"] = true
            }

            cardOverpass.setOnClickListener {
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["overpass"] = true
            }

            cardNuke.setOnClickListener {
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["nuke"] = true
            }

            cardVertigo.setOnClickListener {
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["vertigo"] = true
            }

            cardAncient.setOnClickListener {
                for (l in Global.maps)
                    l.setValue(false)
                Global.maps["ancient"] = true
            }
        }
    }

}