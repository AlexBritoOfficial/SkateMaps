package com.example.googlefirebase.signin_registration_feature.domain.models


data class User(
    var userName: String?,
    var firstName: String?,
    var lastName: String?,
    var userPassword: String?,
    var confirmedPassword: String?
) {
}