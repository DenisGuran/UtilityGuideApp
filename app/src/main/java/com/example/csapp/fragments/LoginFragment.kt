package com.example.csapp.fragments

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.R
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
        private const val REQUEST_CODE_SIGN_IN = 7
        private const val TAG = "GoogleActivity"
    }

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var email = ""
    private var password = ""

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val googleLoginRequest =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if(it.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.i(TAG, "Firebase authentication")
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnLogin.setOnClickListener {
                validateDataAndLogin()
            }

            btnGoogleSignIn.setOnClickListener {
                loginWithGoogle()
            }

            btnContinue.setOnClickListener {
                val navMaps = LoginFragmentDirections.actionAuthenticationToHome()
                findNavController().navigate(navMaps)
            }

            btnCreateAccount.setOnClickListener {
                val navRegister = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(navRegister)
            }

            btnForgotPassword.setOnClickListener {
                val navForgotPassword =
                    LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
                findNavController().navigate(navForgotPassword)
            }
        }

        doubleTapToExit()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val navMaps = LoginFragmentDirections.actionAuthenticationToHome()
            findNavController().navigate(navMaps)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener(requireActivity()) { it ->
                Log.i(TAG, "Login with Google: success")
                val currentUser = auth.currentUser!!
                //if user is new, store id in database
                if (it.additionalUserInfo!!.isNewUser) {
                    val database = FirebaseFirestore.getInstance().collection(Constants.USERS)
                    database.document(currentUser.uid).set(HashMap<String, Any>())
                        .addOnSuccessListener {
                            Log.i(TAG, "Firebase Firestore : id stored in Users Database")
                            updateUI(currentUser)
                        }
                        .addOnFailureListener(requireActivity()) {
                            Log.w(TAG, "Firebase Firestore : failed to store the id", it)
                        }
                } else {
                    updateUI(currentUser)
                }
            }
            .addOnFailureListener(requireActivity()) {
                Toast.makeText(activity, "$it", Toast.LENGTH_LONG).show()
                Log.w(TAG, "Login with Google: failure", it)
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
                            Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_LONG)
                        backPressedToast.show()
                    }
                    backPressedTime = System.currentTimeMillis()
                }
            })
    }

    private fun validateDataAndLogin() {
        binding.apply {
            email = inputEmail.text.toString().trim()
            password = inputPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                inputEmail.error = "Email is required"
                inputEmail.requestFocus()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.error = "Please, provide a valid email"
                inputEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                inputPassword.error = "Password is required"
                inputPassword.requestFocus()
            } else {
                loginWithEmailAndPassword()
            }
        }
    }

    private fun loginWithEmailAndPassword() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUI(auth.currentUser)
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "login: successful")
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "login: failure ", it)
            }
    }

    private fun loginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent.putExtra("REQUEST_CODE_SIGN_IN", REQUEST_CODE_SIGN_IN)

        googleLoginRequest.launch(signInIntent)
    }

}