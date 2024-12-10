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
import kotlinx.coroutines.withContext

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
    private val _uid:MutableLiveData<String?> = MutableLiveData("PthdWGwIJ8GxuQj83Ipd")
    private val auth = Firebase.auth
    val msg: LiveData<String?> get() = _msg
    val uid: LiveData<String?> get() = _uid

    // LiveData to store the current user's name
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    // LiveData to hold the game stats
    private val _gameStats = MutableLiveData<List<Map<String, Any>>>()
    val gameStats: LiveData<List<Map<String, Any>>> = _gameStats

    data class LeaderboardEntry(
        val name: String = "",
        val score: Int = 0,
        val level: Int = 0,
        val date: String = ""
    )
    private val _globalLeaderboard = MutableLiveData<List<LeaderboardEntry>>()
    val globalLeaderboard: LiveData<List<LeaderboardEntry>> get() = _globalLeaderboard

    init {
        if (auth.currentUser != null) {
            _uid.postValue(auth.uid)
        }
    }

    // MutableLiveData to hold the login state
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.postValue(LoginState.Success)
                    _uid.postValue(auth.uid)
                    setUserLoggedIn(auth.uid.toString())
                } else {
                    _loginState.postValue(LoginState.Error("Login failed"))
                }
            }
    }

    fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.postValue(LoginState.Success)
                    addUserToFirestore(name)
                } else {
                    _loginState.postValue(LoginState.Error("Signup failed"))
                }
            }
    }

    fun logout() {
        // Perform sign-out logic (clear session, token, etc.)
        Log.d("print", "signing out")
        auth.uid?.let { setUserLoggedOut(it) }
        auth.signOut() // Sign out the current user from Firebase Authentication
        _loginState.value = LoginState.NotLoggedIn // Reset to NotLoggedIn state
        _uid.postValue("PthdWGwIJ8GxuQj83Ipd") // Default Guest UID
        _userName.postValue("Guest")
        _gameStats.postValue(emptyList())
        Log.d("print", "logout, Current user: ${auth.currentUser}") // Should log null
        Log.d("print", "logout, uid: ${uid.value}")
        Log.d("print", "logout, name: ${userName.value}")
    }

    // LoginState sealed class to represent different login states
    sealed class LoginState {
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
        object NotLoggedIn : LoginState()
    }

    // Function to fetch the name of the current user
    fun fetchUserName() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userRef = db.collection("users").document(uid)
            userRef.get()
                .addOnSuccessListener { document ->
                    val name = document.getString("name") ?: "Guest"
                    _userName.postValue(name) // Update LiveData with the user's name
                }
                .addOnFailureListener { exception ->
                    Log.e("AuthViewModel", "Error fetching user name", exception)
                    _userName.postValue("Guest") // Default to "Guest" if fetching fails
                }
        } else {
            _userName.postValue("Guest") // Default to "Guest" if user is not logged in
        }
    }

    // FIRESTORE STUFF
    // Add new user to firestore db, set as logged in
    fun addUserToFirestore(name: String) {
        // Get the current user's UID
        val currentUserUID = auth.uid
        if (currentUserUID != null) {
            // Create a map of the data to store in Firestore
            val user = hashMapOf(
                "name" to name,
                "isLoggedIn" to true
            )

            // Add the user data to the Firestore collection "users"
            db.collection("users")
                .document(currentUserUID)  // Using the UID as the document ID
                .set(user)
                .addOnSuccessListener {
                    _msg.postValue("User added successfully")
                    Log.d("AuthViewModel", "User added successfully")
                }
                .addOnFailureListener { exception ->
                    _msg.postValue("Error adding user: ${exception.message}")
                    Log.e("AuthViewModel", "Error adding user", exception)
                }
        } else {
            _msg.postValue("Error: User not authenticated")
            Log.e("AuthViewModel", "User not authenticated")
        }
    }

    fun setUserLoggedIn(uid: String) {
        // Update the 'isLoggedIn' field of the user document to true
        db.collection("users")
            .document(uid)
            .update("isLoggedIn", true)
            .addOnSuccessListener {
                _msg.postValue("User logged in successfully")
                Log.d("AuthViewModel", "User logged in successfully")
            }
            .addOnFailureListener { exception ->
                _msg.postValue("Error setting user logged in: ${exception.message}")
                Log.e("AuthViewModel", "Error setting user logged in", exception)
            }
    }

    fun setUserLoggedOut(uid: String) {
        // Update the 'isLoggedIn' field of the user document to false
        db.collection("users")
            .document(uid)
            .update("isLoggedIn", false)
            .addOnSuccessListener {
                _msg.postValue("User logged out successfully")
                Log.d("AuthViewModel", "User logged out successfully")
            }
            .addOnFailureListener { exception ->
                _msg.postValue("Error setting user logged out: ${exception.message}")
                Log.e("AuthViewModel", "Error setting user logged out", exception)
            }
    }

    fun fetchUserGameStats() {

        // val currentUserId = auth.uid
        val guestId = "PthdWGwIJ8GxuQj83Ipd"  // Replace with your predefined guest ID string
        val currentUserId = auth.currentUser?.uid ?: guestId

        val statsRef = currentUserId?.let { db.collection("users").document(it).collection("stats") }
        if (statsRef != null) {
            statsRef.get()
                .addOnSuccessListener { querySnapshot ->
                    val gameStatsList = mutableListOf<Map<String, Any>>()
                    for (document in querySnapshot.documents) {
                        val gameStats = document.data ?: continue
                        gameStatsList.add(gameStats)
                    }
                    _gameStats.postValue(gameStatsList) // Update LiveData
                }
                .addOnFailureListener { exception ->
                    Log.e("AuthViewModel", "Error fetching game stats", exception)
                    _gameStats.postValue(emptyList()) // Return an empty list if error
                }
        }
    }

    fun fetchGlobalLeaderboard() {
        Log.d("print", "Fetching global leaderboard...")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allStats = mutableListOf<LeaderboardEntry>()

                // Get all users from the 'users' collection
                val usersSnapshot = db.collection("users").get().await()
                Log.d("print", "Total users found: ${usersSnapshot.size()}")

                for (userDocument in usersSnapshot) {
                    val userId = userDocument.id
                    val username = userDocument.getString("name") ?: "Unknown"

                    // Get each user's game stats from the 'stats' subcollection
                    try {
                        val gameStatsSnapshot = db.collection("users/$userId/stats").get().await()
                        Log.d("print", "Stats for user $username (ID: $userId) found: ${gameStatsSnapshot.size()}")

                        for (gameStat in gameStatsSnapshot) {
                            val score = gameStat.getLong("score")?.toInt() ?: 0
                            val level = gameStat.getLong("level")?.toInt() ?: 0
                            val date = gameStat.getString("date") ?: "N/A"

                            // Check if all required fields are present
                            if (score != 0 && level != 0) {
                                allStats.add(
                                    LeaderboardEntry(
                                        name = username,
                                        score = score,
                                        level = level,
                                        date = date
                                    )
                                )
                            } else {
                                Log.d("print", "Skipping stat for user $username - Missing score/level")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("print", "Error getting stats for user $username (ID: $userId)", e)
                    }
                }

                // Sort allStats by score (descending) and pick the top 10
                val top10 = allStats
                    .sortedByDescending { it.score }
                    .take(10)

                // Update LiveData on the main thread
                _globalLeaderboard.postValue(top10)

                Log.d("print", "Top 10 leaderboard: $top10")
            } catch (e: Exception) {
                Log.e("print", "Error fetching global leaderboard", e)
            }
        }
    }


} // End of auth VM
