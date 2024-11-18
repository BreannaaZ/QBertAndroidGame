package com.example.cis357finalprojectqbertgame
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    // Declare variables for firebase auth and firestore
    // private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    //private val _authState = MutableLiveData<AuthState>()
    //val authState: LiveData<AuthState> = _authState
    private val db = Firebase.firestore
    // User's data from firestore
    //private val _userData = MutableLiveData<UserData?>(null)
    //val userData: LiveData<UserData?> = _userData

    private val _msg:MutableLiveData<String?> = MutableLiveData(null)
    private val _uid:MutableLiveData<String?> = MutableLiveData(null)
    private val auth = Firebase.auth
    val msg: LiveData<String?> get() = _msg
    val uid: LiveData<String?> get() = _uid

    init {
        _uid.postValue(auth.uid)
    }

    // MutableLiveData to hold the login state
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.postValue(LoginState.Success)
                } else {
                    _loginState.postValue(LoginState.Error("Login failed"))
                }
            }
    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.postValue(LoginState.Success)
                } else {
                    _loginState.postValue(LoginState.Error("Signup failed"))
                }
            }
    }

    fun logout() {
        // Perform sign-out logic (clear session, token, etc.)
        _loginState.value = LoginState.NotLoggedIn // Reset to NotLoggedIn state
    }

    // LoginState sealed class to represent different login states
    sealed class LoginState {
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
        object NotLoggedIn : LoginState()
    }

} // End of auth VM
