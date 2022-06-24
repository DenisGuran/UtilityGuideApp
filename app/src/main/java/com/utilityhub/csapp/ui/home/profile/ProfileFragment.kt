package com.utilityhub.csapp.ui.home.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.FragmentProfileBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUserState()

    }

    private fun handleUserState() {
        if (viewModel.isLoggedIn) {
            getUserProfile()
            binding.apply {
                loggedInLayout.visibility = View.VISIBLE
                loggedOutLayout.visibility = View.GONE
                btnLogout.setOnClickListener {
                    signOut()
                }
            }
            initSpinner()
        } else {
            binding.btnLogin.setOnClickListener {
                navigateToAuth()
            }
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.utilities,
            R.layout.dropdown_item
        )
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(R.layout.dropdown_item)
                binding.utilitySpinner.adapter = arrayAdapter
            }

        binding.utilitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.i("ASD",adapterView!!.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    private fun navigateToAuth() {
        val navAuth = ProfileFragmentDirections.actionHomeToAuthentication()
        findNavController().navigate(navAuth)
    }

    private fun signOut() {
        viewModel.signOut().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> navigateToAuth()
                is Response.Failure -> print(response.errorMessage)
            }
        }
    }

    private fun getUserProfile() {
        viewModel.userProfile.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    val currentUser = response.data
                    binding.username.text =
                        if (currentUser.username!!.endsWith("s"))
                            "${currentUser.username}'"
                        else
                            "${currentUser.username}'s"
                }
                is Response.Failure -> {
                    print(response.errorMessage)
                }
            }
        }
    }
}