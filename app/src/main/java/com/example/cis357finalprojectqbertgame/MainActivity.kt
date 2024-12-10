package com.example.cis357finalprojectqbertgame

import AppNavigation
import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cis357finalprojectqbertgame.ui.theme.CIS357FinalProjectQbertGameTheme
import com.example.cis357finalprojectqbertgame.ui.theme.pixelFontFamily
import com.google.firebase.FirebaseApp
import android.content.Intent;
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

// import com.unity3d.player.UnityPlayerActivity;

class MainActivity : ComponentActivity() {

    // Declare the ActivityResultLauncher
    private lateinit var launchOtherAppLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the ActivityResultLauncher
        launchOtherAppLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    /*
                    RESULT_OK -> {
                        Toast.makeText(this, "Unity game ended successfully!", Toast.LENGTH_LONG)
                            .show()
                    }

                    RESULT_CANCELED -> {
                        Toast.makeText(
                            this,
                            "Unity game canceled or back button pressed",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else -> {
                        Toast.makeText(
                            this,
                            "Unity game returned unknown result",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                     */
                }
            }

        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            CIS357FinalProjectQbertGameTheme {
                AppNavigation(
                    modifier = Modifier,
                    authViewModel = authViewModel
                )
            }
        }
    }

    fun launchOtherApp() {
        try {
            val intent = Intent().apply {
                component = ComponentName(
                    "com.BreannaZinky.SpaceHopper",
                    "com.unity3d.player.UnityPlayerGameActivity"
                )
                // These flags help Android keep track of the Unity activity
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            launchOtherAppLauncher.launch(intent) // Launch Unity app
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to launch Unity app: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }


}

@Composable
fun main(modifier: Modifier = Modifier, navController: NavHostController,
         authViewModel: AuthViewModel = AuthViewModel()) {
    // Variables
    // val loginState by authViewModel.loginState.observeAsState(AuthViewModel.LoginState.NotLoggedIn)
    val loginState = authViewModel.loginState.observeAsState(AuthViewModel.LoginState.NotLoggedIn)

    // Observe currentUserId from the ViewModel
    val currentUserId by authViewModel.uid.observeAsState("PthdWGwIJ8GxuQj83Ipd") // Default to guest ID
    Log.d("print", "UID: $currentUserId")

    // Intent stuff
    // val context = LocalContext.current
    val context = LocalContext.current as? MainActivity
    // com.BreannaZinky.SpaceHopper/com.unity3d.player.UnityPlayerGameActivity

    // Fetch the user name on screen composition
    LaunchedEffect(Unit) {
        authViewModel.fetchUserName() // Fetch the user's name when the screen is opened
    }

    // Observe the user name LiveData
    val name by authViewModel.userName.observeAsState("Guest") // Default to "Guest" if no name is fetched

    Box (
        modifier = modifier
            .background(Color(0xFF001F3F))
            .fillMaxSize(),
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
        ){
            Text(
                text = "Welcome $name",
                fontFamily = pixelFontFamily,
                fontSize = 25.sp,
                color = Color.White,
                modifier = modifier
                    .padding(top = 12.dp)
            )
            Text(
                text = "SPACE HOPPER",
                fontFamily = pixelFontFamily,
                fontSize = 60.sp,
                color = Color.White,
                modifier = modifier
                    .padding(top = 250.dp)
            )
            // Buttons (Start, login/logout, statistics/leaderboard)
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .padding(top = 10.dp)
            ) {
                // Start button (launch unity game)
                TextButton(
                    onClick = {
                        context?.launchOtherApp()
                    },
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text("START",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = pixelFontFamily)
                }
                // Space between buttons to push them to the side
                Spacer(modifier = Modifier.weight(1f))

                // Login/Logout button (login or logout depending on current status)
                TextButton(
                    onClick =
                    {
                        when (loginState.value) {
                            is AuthViewModel.LoginState.Success -> {
                                // Trigger sign out action
                                authViewModel.logout()
                            }
                            else -> {
                                if (currentUserId != "PthdWGwIJ8GxuQj83Ipd") {
                                    authViewModel.logout()
                                } else {
                                    // Navigate to login screen if not logged in
                                    navController.navigate("login")
                                }
                            }
                        }
                    },
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        if (currentUserId != "PthdWGwIJ8GxuQj83Ipd") {
                                    "SIGNOUT"
                                } else {
                                    "LOGIN"
                                },
                        color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = pixelFontFamily)
                }
            }
            // Spacer to push buttom to bottom of screen
            Spacer(modifier = Modifier.weight(1f))
            // Statistics / leaderboard screen button
            TextButton(
                onClick = { navController.navigate("statistics") },
                modifier = modifier
                    .padding(vertical = 30.dp)
            ) {
                Text("STATS",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = pixelFontFamily)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CIS357FinalProjectQbertGameTheme {
        main(modifier = Modifier, navController = NavHostController(LocalContext.current))
    }
}