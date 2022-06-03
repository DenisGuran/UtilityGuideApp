package com.utilityhub.csapp.ui.home.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.utilityhub.csapp.databinding.FragmentFavoritesBinding
import com.utilityhub.csapp.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}