package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.core.Global
import com.utilityhub.csapp.databinding.FragmentMapsBinding
import com.utilityhub.csapp.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.collections.set

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate) {

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().popBackStack(R.id.loginFragment, true)

        initData()
        doubleTapToExit()
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
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
                // TODO implement map name with safe args
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