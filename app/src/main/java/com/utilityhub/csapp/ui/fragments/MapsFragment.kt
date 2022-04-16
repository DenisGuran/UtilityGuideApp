package com.utilityhub.csapp.ui.fragments

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.core.Global
import com.utilityhub.csapp.databinding.FragmentMapsBinding
import com.utilityhub.csapp.domain.model.Map
import com.utilityhub.csapp.ui.activities.MainActivity
import com.utilityhub.csapp.ui.adapters.MapAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.collections.set

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MapsFragment : BaseFragment<FragmentMapsBinding>(FragmentMapsBinding::inflate) {

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private var maps = ArrayList<Map>()
    private var adapter: MapAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setRecyclerView()
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

    private fun setRecyclerView() {
        adapter =
            MapAdapter(maps, object : MapAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    val selectedMap = maps[position]
                    for (map in Global.maps) {
                        map.setValue(false)
                    }
                    Global.maps[selectedMap.name!!.lowercase()] = true
                    val navLand = MapsFragmentDirections.actionGlobalNavUtility()
                    findNavController().navigate(navLand)
                    findNavController().setGraph(R.navigation.nav_utility)
                }
            })
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@MapsFragment.activity)
            recyclerView.adapter = adapter
        }
    }

    private fun getBitmap(drawable: Int) =
        (ResourcesCompat.getDrawable(
            this.resources,
            drawable,
            null
        ) as VectorDrawable).toBitmap()


    private fun initData() {

        maps.clear()

        maps.add(
            Map(
                "Mirage",
                pin = getBitmap(R.drawable.mirage_pin),
                background = R.drawable.mirage_background
            )
        )
        maps.add(
            Map(
                "Inferno",
                pin = getBitmap(R.drawable.mirage_pin),
                background = R.drawable.inferno_background
            )
        )
        maps.add(
            Map(
                "Dust2",
                pin = getBitmap(R.drawable.dust2_pin),
                background = R.drawable.dust2_background
            )
        )
        maps.add(
            Map(
                "Vertigo",
                pin = getBitmap(R.drawable.vertigo_pin),
                background = R.drawable.vertigo_background
            )
        )

        maps.add(
            Map(
                "Overpass",
                pin = getBitmap(R.drawable.overpass_pin),
                background = R.drawable.overpass_background
            )
        )
        maps.add(
            Map(
                "Nuke",
                pin = getBitmap(R.drawable.nuke_pin),
                background = R.drawable.nuke_background
            )
        )
        maps.add(
            Map(
                "Ancient",
                pin = getBitmap(R.drawable.ancient_pin),
                background = R.drawable.ancient_background
            )
        )
    }
}