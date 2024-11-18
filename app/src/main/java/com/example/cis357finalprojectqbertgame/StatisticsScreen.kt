package com.example.cis357finalprojectqbertgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier, navController: NavHostController,
                     authViewModel: AuthViewModel = AuthViewModel()) {
    Box(
        modifier = modifier
            .background(Color(0xFF001F3F))
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "SPACE HOPPER",
                fontFamily = pixelFontFamily,
                fontSize = 60.sp,
                color = Color.White,
            )
            Text(
                text = "GLOBAL LEADERBOARD",
                fontFamily = pixelFontFamily,
                fontSize = 32.sp,
                color = Color.White,
            )
            Box(
                modifier = modifier
                    .background(Color.LightGray)
            ) {
                Text("USER - SCORE - LEVEL - DATE - TIME",
                    fontFamily = pixelFontFamily,
                    fontSize = 24.sp,
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 100.dp, horizontal = 12.dp))
            }
            Text(
                text = "PERSONAL STATS",
                fontFamily = pixelFontFamily,
                fontSize = 32.sp,
                color = Color.White,
            )
            Box(
                modifier = modifier
                    .background(Color.LightGray)
            ) {
                Column () {
                    Text("TOP GAME - SCORE - LEVEL - TIME",
                        fontFamily = pixelFontFamily,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp))
                    Text("LAST GAME - SCORE - LEVEL - DATE - TIME",
                        fontFamily = pixelFontFamily,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp))
                    Text("GAMES PLAYED - GAMES WON - GAMES LOST - AVG SCORE - AVG TIME",
                        fontFamily = pixelFontFamily,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp))
                }
            }
            TextButton(
                onClick = {
                    navController.popBackStack() // Return back to main screen
                },
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Text("RETURN",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontFamily = pixelFontFamily)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    CIS357FinalProjectQbertGameTheme {
        StatisticsScreen(modifier = Modifier, navController = NavHostController(LocalContext.current))
    }
}