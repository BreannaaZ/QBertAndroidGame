package com.example.cis357finalprojectqbertgame

import AppNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cis357finalprojectqbertgame.ui.theme.CIS357FinalProjectQbertGameTheme
import com.example.cis357finalprojectqbertgame.ui.theme.pixelFontFamily
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}

@Composable
fun main(name: String, modifier: Modifier = Modifier, navController: NavHostController,
         authViewModel: AuthViewModel = AuthViewModel()) {
    // Variables
    // val loginState by authViewModel.loginState.observeAsState(AuthViewModel.LoginState.NotLoggedIn)
    val loginState = authViewModel.loginState.observeAsState(AuthViewModel.LoginState.NotLoggedIn)


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
                    onClick = { /*TODO*/ },
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
                                authViewModel.logout()  // You should implement this in your ViewModel
                            }
                            else -> {
                                // Navigate to login screen if not logged in
                                navController.navigate("login")
                            }
                        }
                    },
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text( text = when (loginState.value) {
                            is AuthViewModel.LoginState.Success -> "SIGNOUT"
                            else -> "LOGIN"
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
                    .padding(vertical = 16.dp)
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
        main("Guest", modifier = Modifier, navController = NavHostController(LocalContext.current))
    }
}