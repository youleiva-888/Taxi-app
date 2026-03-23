package com.taxibooking.app.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Thin wrapper around [FirebaseAuth] so ViewModels depend on this helper
 * instead of Firebase types directly. Replace [google-services.json] with your
 * Firebase project file from the console before shipping.
 */
class FirebaseAuthHelper(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onResult: (Result<FirebaseUser>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) onResult(Result.success(user))
                    else onResult(Result.failure(IllegalStateException("No user after sign-in")))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Sign-in failed")))
                }
            }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        onResult: (Result<FirebaseUser>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) onResult(Result.success(user))
                    else onResult(Result.failure(IllegalStateException("No user after sign-up")))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Sign-up failed")))
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
