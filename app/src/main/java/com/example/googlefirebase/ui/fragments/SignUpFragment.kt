package com.example.googlefirebase.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.FragmentSignInBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class SignUpFragment : Fragment() {

    // TAG
    private val TAG = "HomepageFragment"

    // Google API
    private val googleApiAvailability =  GoogleApiAvailability()

    // View Binding
    lateinit var signInBinding: FragmentSignInBinding

    // UI Elements
    private lateinit var usernameTextInputEditText: TextInputEditText
    private lateinit var passwordTextInputEditText: TextInputEditText
    private lateinit var registerTextView : MaterialTextView
    private lateinit var signInButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Check if Google API is available
        if(googleApiAvailability.isGooglePlayServicesAvailable(requireContext()) == (ConnectionResult.SUCCESS)){
            Toast.makeText(requireContext(), "Google API is available", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "Google Maps API availability: " + googleApiAvailability.isGooglePlayServicesAvailable(requireContext()).toString())
        }

        else {
            Toast.makeText(requireContext(), "Google API is not available", Toast.LENGTH_SHORT).show()

        }

        // Load UI element instances
        signInBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_sign_in, container, false)
        usernameTextInputEditText = signInBinding.usernameTextInputEditText
        passwordTextInputEditText = signInBinding.passwordTextInputEditText
        signInButton = signInBinding.signInButton
        registerTextView = signInBinding.registerTextview

        registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signFragment_to_registrationFragment)
        }

        signInButton.setOnClickListener {
            findNavController().navigate(R.id.homePageFragment)
        }


        return signInBinding.root
    }
}