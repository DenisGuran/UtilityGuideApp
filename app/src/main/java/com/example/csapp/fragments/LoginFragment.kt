package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.R
import com.example.csapp.activities.MainActivity
import com.example.csapp.databinding.FragmentLoginBinding
import com.example.csapp.utils.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var email = ""
    private var password = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnLogin.setOnClickListener {
                validateDataAndLogin(this)
            }

            btnGoogleSignIn.setOnClickListener{
                loginWithGoogle()
            }

            btnContinue.setOnClickListener {
                startActivity(Intent(activity, MainActivity::class.java))
                requireActivity().finish()
            }

            btnCreateAccount.setOnClickListener{
                findNavController().navigate(R.id.registerFragment)
            }

            btnForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }

        doubleTapToExit()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            startActivity(Intent(activity, MainActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.i(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener(requireActivity()) {
                Log.i(TAG, "login with Google: success")
                val currentUser = auth.currentUser!!
                //if user is new, store id in database
                if(it.additionalUserInfo!!.isNewUser){
                    val database = FirebaseFirestore.getInstance().collection(Constants.USERS)
                    database.document(currentUser.uid).set(HashMap<String, Any>())
                }
                //update interface
                updateUI(currentUser)
            }
            .addOnFailureListener(requireActivity()){
                Log.w(TAG, "login with Google: failure", it)
                updateUI(null)
            }
    }

    private fun doubleTapToExit() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(backPressedTime + Constants.EXIT_TIME > System.currentTimeMillis()){
                    backPressedToast.cancel()
                    requireActivity().finish()
                    return
                }else{
                    backPressedToast = Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT)
                    backPressedToast.show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })
    }

    private fun validateDataAndLogin(binding: FragmentLoginBinding) {
        binding.apply {
            email = inputEmail.text.toString().trim()
            password = inputPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                inputEmail.error = "Email is required"
                inputEmail.requestFocus()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                inputEmail.error = "Please, provide a valid email"
                inputEmail.requestFocus()
            }
            else if (TextUtils.isEmpty(password)){
                inputPassword.error = "Password is required"
                inputPassword.requestFocus()
            }
            else{
                loginWithEmailAndPassword()
            }
        }
    }

    private fun loginWithEmailAndPassword() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUI(auth.currentUser)
                Toast.makeText(activity,"Login successful",Toast.LENGTH_SHORT).show()
                Log.i(TAG, "login: successful")
            }
            .addOnFailureListener {
                Toast.makeText(activity,"Login failed",Toast.LENGTH_SHORT).show()
                Log.w(TAG, "login: failure ", it)
            }
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

}