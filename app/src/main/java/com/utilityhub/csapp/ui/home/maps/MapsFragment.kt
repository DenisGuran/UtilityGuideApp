package com.utilityhub.csapp.ui.home.maps

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.core.Constants.MAP
import com.utilityhub.csapp.databinding.FragmentMapsBinding
import com.utilityhub.csapp.domain.model.Map
import com.utilityhub.csapp.ui.adapters.MapAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.core.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate),
    MapAdapter.OnMapClickListener {

    @Inject
    lateinit var applicationContext : Context

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private var adapter = MapAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        doubleTapToExit()
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

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

    private fun setAdapter() {
        adapter.submitList(maps)
        binding.recyclerView.adapter = adapter
    }

    override fun onMapClick(map: Map) {
        val selectedMapName = map.name
        val navLand = MapsFragmentDirections.actionGlobalNavUtility()
        findNavController().navigate(navLand)
        findNavController().setGraph(R.navigation.nav_utility, bundleOf(MAP to selectedMapName))
    }

    companion object{

        private val maps = arrayListOf(
            Map(
                "Mirage",
                background = R.drawable.mirage_background
            ),
            Map(
                "Inferno",
                background = R.drawable.inferno_background
            ),
            Map(
                "Dust2",
                background = R.drawable.dust2_background
            ),
            Map(
                "Vertigo",
                background = R.drawable.vertigo_background
            ),
            Map(
                "Overpass",
                background = R.drawable.overpass_background
            ),
            Map(
                "Nuke",

                background = R.drawable.nuke_background
            ),
            Map(
                "Ancient",
                background = R.drawable.ancient_background
            )
        )

    }
}