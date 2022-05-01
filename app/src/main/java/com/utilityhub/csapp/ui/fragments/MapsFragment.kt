package com.utilityhub.csapp.ui.fragments

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.core.Constants.MAP
import com.utilityhub.csapp.databinding.FragmentMapsBinding
import com.utilityhub.csapp.domain.model.Map
import com.utilityhub.csapp.ui.activities.MainActivity
import com.utilityhub.csapp.ui.adapters.MapAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate),
    MapAdapter.OnMapClickListener {

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private var maps = ArrayList<Map>()
    private var adapter = MapAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
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
        binding.recyclerView.adapter = adapter
    }

    private fun getPinBitmap(drawable: Int) =
        (ResourcesCompat.getDrawable(
            this.resources,
            drawable,
            null
        ) as VectorDrawable).toBitmap()

    override fun onMapClick(position: Int) {
        val selectedMapName = maps[position].name
        val navLand = MapsFragmentDirections.actionGlobalNavUtility()
        findNavController().navigate(navLand)
        findNavController().setGraph(R.navigation.nav_utility, bundleOf(MAP to selectedMapName))
    }

    private fun initData() {

        maps = arrayListOf(
            Map(
                "Mirage",
                pin = getPinBitmap(R.drawable.mirage_pin),
                background = R.drawable.mirage_background
            ),
            Map(
                "Inferno",
                pin = getPinBitmap(R.drawable.mirage_pin),
                background = R.drawable.inferno_background
            ),
            Map(
                "Dust2",
                pin = getPinBitmap(R.drawable.dust2_pin),
                background = R.drawable.dust2_background
            ),
            Map(
                "Vertigo",
                pin = getPinBitmap(R.drawable.vertigo_pin),
                background = R.drawable.vertigo_background
            ),
            Map(
                "Overpass",
                pin = getPinBitmap(R.drawable.overpass_pin),
                background = R.drawable.overpass_background
            ),
            Map(
                "Nuke",
                pin = getPinBitmap(R.drawable.nuke_pin),
                background = R.drawable.nuke_background
            ),
            Map(
                "Ancient",
                pin = getPinBitmap(R.drawable.ancient_pin),
                background = R.drawable.ancient_background
            )
        )
        adapter.submitList(maps)
    }
}