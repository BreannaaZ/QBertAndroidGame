package com.example.cis357finalprojectqbertgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cis357finalprojectqbertgame.ui.theme.CIS357FinalProjectQbertGameTheme
import com.example.cis357finalprojectqbertgame.ui.theme.pixelFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CIS357FinalProjectQbertGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Guest",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box (
        modifier = modifier
            .background(Color(0xFF001F3F))
            .fillMaxSize(),
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
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
                fontSize = 75.sp,
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
                    onClick = { /*TODO*/ },
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text("LOGIN",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = pixelFontFamily)
                }
            }
            // Spacer to push buttom to bottom of screen
            Spacer(modifier = Modifier.weight(1f))
            // Statistics / leaderboard screen button
            TextButton(
                onClick = { /*TODO*/ },
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
        Greeting("Guest")
    }
}