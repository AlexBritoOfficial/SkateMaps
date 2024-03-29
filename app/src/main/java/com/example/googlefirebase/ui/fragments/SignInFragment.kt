package com.example.googlefirebase.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.googlefirebase.R
import com.example.googlefirebase.databinding.FragmentSignInBinding
import com.example.googlefirebase.signin_registration_feature.data.local.entity.RegisteredUserTuple
import com.example.googlefirebase.signin_registration_feature.viewmodel.RegistrationViewModel
import com.example.googlefirebase.signin_registration_feature.viewmodel.RegistrationViewModelFactory
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.*

class SignInFragment : Fragment() {

    // TAG
    private val TAG = "HomepageFragment"

    // Username String Parcelable
    private val USERNAME_STRING_BUNDLE_TAG = "UserNameString"

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

    // RegisteredUserTuple
    private var registeredUserTuple: RegisteredUserTuple? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finishAffinity()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Check if Google API is available
        if (googleApiAvailability.isGooglePlayServicesAvailable(requireContext()) == (ConnectionResult.SUCCESS)) {
            //Toast.makeText(requireContext(), "Google API is available", Toast.LENGTH_SHORT).show()
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

        // Create an instance of the RegistrationViewModelFactory
        registrationViewModelFactory = RegistrationViewModelFactory(
            requireContext()
        )

        // Create an instance of the RegistrationViewModel
        registrationViewModel = ViewModelProvider(
            this,
            registrationViewModelFactory
        ).get(RegistrationViewModel::class.java)

        /*** Load UI element instances ***/

        // FragmentSignInBinding Binding
        signInBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_sign_in, container, false)

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

            // Get values from TextInputEditText boxes
            var userName: String? = usernameTextInputEditText.text.toString().trim()
            var userPassword: String? = passwordTextInputEditText.text.toString().trim()

            lifecycleScope.launch(Dispatchers.IO) {
                if (checkIfUserExists(userName, userPassword) != null) {
                    launch(Dispatchers.Main) {

                        // Display TOAST notifying user he/she/they have successsfully logged in.
                        Toast.makeText(context, "Successful Log-In User Found", Toast.LENGTH_SHORT).show()

                        // Create a Bundle object to pass information to HomePageFragment
                        val bundle = Bundle()

                        // Place username in the bundle, this will serve as a means to retrieving the user's full information from the local database.
                        bundle.putString(USERNAME_STRING_BUNDLE_TAG, userName)

                        findNavController().navigate(R.id.action_signFragment_to_homePageFragment, bundle)
                    }
                }

                else{
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "UnSuccessful Log-In User Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return signInBinding.root
    }

    // onResume LifeCycle Method
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    suspend fun checkIfUserExists(userName: String?, userPassword: String?): RegisteredUserTuple? {
        return registrationViewModel.checkIfUserExists(userName, userPassword)
    }
}