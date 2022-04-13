package com.utilityhub.csapp.domain.use_case.auth

data class AuthUseCases(
    val resetPassword: ResetPassword,
    val addFirestoreUser: AddFirestoreUser,
    val getUserProfile: GetUserProfile,
    val getAuthState: GetAuthState,
    val signInWithGoogle: SignInWithGoogle,
    val signOut: SignOut,
    val signInWithEmail: SignInWithEmail,
    val register: Register
)
