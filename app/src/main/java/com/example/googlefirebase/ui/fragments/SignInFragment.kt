package com.example.googlefirebase.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.FragmentSignInBinding
import com.example.googlefirebase.signin_registration_feature.viewmodel.RegistrationViewModel
import com.example.googlefirebase.signin_registration_feature.viewmodel.RegistrationViewModelFactory
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignInFragment : Fragment() {

    // TAG
    private val TAG = "HomepageFragment"

    // Google API
    private val googleApiAvailability = GoogleApiAvailability()

    // View Binding
    lateinit var signInBinding: FragmentSignInBinding

    // ViewModel
    private lateinit var registrationViewModel: RegistrationViewModel

    // ViewModel Factory
    private lateinit var registrationViewModelFactory: RegistrationViewModelFactory


    // UI Elements
    private lateinit var usernameTextInputEditText: TextInputEditText
    private lateinit var passwordTextInputEditText: TextInputEditText
    private lateinit var registerTextView: MaterialTextView
    private lateinit var signInButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Check if Google API is available
        if (googleApiAvailability.isGooglePlayServicesAvailable(requireContext()) == (ConnectionResult.SUCCESS)) {
            Toast.makeText(requireContext(), "Google API is available", Toast.LENGTH_SHORT).show()
            Log.i(
                TAG,
                "Google Maps API availability: " + googleApiAvailability.isGooglePlayServicesAvailable(
                    requireContext()
                ).toString()
            )
        } else {
            Toast.makeText(requireContext(), "Google API is not available", Toast.LENGTH_SHORT)
                .show()
        }

        registrationViewModelFactory = RegistrationViewModelFactory(
            requireContext()
        )

        registrationViewModel = ViewModelProvider(
            this,
            registrationViewModelFactory
        ).get(RegistrationViewModel::class.java)

        /*** Load UI element instances ***/

        // FragmentSignInBinding Binding
        signInBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_in, container, false)

        // UserName TextInputEditText
        usernameTextInputEditText = signInBinding.usernameTextInputEditText

        // Password TextInputEditText
        passwordTextInputEditText = signInBinding.passwordTextInputEditText

        // Register MaterialTextView
        registerTextView = signInBinding.registerTextview

        // Register a OnClickListener to Register MaterialTextView widget
        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signFragment_to_registrationFragment)
        }


        // Sign In MaterialButton
        signInButton = signInBinding.signInButton


        signInButton.setOnClickListener {

//            var userName: String? = usernameTextInputEditText.text.toString().trim()
//            var userPassword: String? = passwordTextInputEditText.text.toString().trim()
//
//
//
//            CoroutineScope(Dispatchers.IO).launch {
//                if (registrationViewModel.checkIfUserExists(userName, userPassword) != null) {
//
//
//                }
//            }
            findNavController().navigate(R.id.action_signFragment_to_homePageFragment)

        }


        return signInBinding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}