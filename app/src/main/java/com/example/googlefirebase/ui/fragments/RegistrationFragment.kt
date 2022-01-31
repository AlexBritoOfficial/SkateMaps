package com.example.googlefirebase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.googlefirebase.databinding.FragmentRegistrationBinding
import com.example.googlefirebase.signin_registration_feature.viewmodel.RegistrationViewModel
import com.example.googlefirebase.signin_registration_feature.viewmodel.RegistrationViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegistrationFragment : Fragment() {

    // View Binding
    private lateinit var registrationBinding: FragmentRegistrationBinding

    // ViewModel
    private lateinit var registrationViewModel: RegistrationViewModel

    // ViewModel Factory
    private lateinit var registrationViewModelFactory: RegistrationViewModelFactory

    // UI Elements
    private lateinit var usernameTextInputEditText: TextInputEditText
    private lateinit var firstNameTextInputEditText: TextInputEditText
    private lateinit var lastNameTextInputEditText: TextInputEditText
    private lateinit var passwordTextInputEditText: TextInputEditText
    private lateinit var confirmPasswordTextInputEditText: TextInputEditText
    private lateinit var registerButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Load UI element instances
        registrationBinding = FragmentRegistrationBinding.inflate(layoutInflater)
        usernameTextInputEditText = registrationBinding.usernameTextInputEditText
        firstNameTextInputEditText = registrationBinding.firstNameTextInputEditText
        lastNameTextInputEditText = registrationBinding.lastNameTextInputEditText
        passwordTextInputEditText = registrationBinding.passwordTextInputEditText
        confirmPasswordTextInputEditText = registrationBinding.confirmPasswordTextInputEditText
        registerButton = registrationBinding.registerInButton

        registrationViewModelFactory = RegistrationViewModelFactory(
            requireContext()
        )

        registrationViewModel = ViewModelProvider(
            this,
            registrationViewModelFactory
        ).get(RegistrationViewModel::class.java)

        // Register Button OnClickListener
        registerButton.setOnClickListener {
            val userNameInput = usernameTextInputEditText.text.toString()
            val firstNameInput = firstNameTextInputEditText.text.toString()
            val lastNameInput = lastNameTextInputEditText.text.toString()
            val passwordInput = passwordTextInputEditText.text.toString()
            val confirmPasswordInput = confirmPasswordTextInputEditText.text.toString()

            insertUserIntoRemoteAndLocalDataCenters(
                userNameInput,
                firstNameInput,
                lastNameInput,
                passwordInput,
                confirmPasswordInput
            )

        }

        return registrationBinding.root
    }

    fun insertUserIntoRemoteAndLocalDataCenters(
        userName: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmedPassword: String
    ) {
        registrationViewModel.insertUserIntoRemoteAndLocalDataCenters(
            userName,
            firstName,
            lastName,
            password,
            confirmedPassword
        )
    }
}