package com.example.cis357finalprojectqbertgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cis357finalprojectqbertgame.ui.theme.CIS357FinalProjectQbertGameTheme
import com.example.cis357finalprojectqbertgame.ui.theme.pixelFontFamily

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier, navController: NavHostController,
                     authViewModel: AuthViewModel = AuthViewModel()) {

    // Fetch game stats when the screen is composed
    LaunchedEffect(Unit) {
        authViewModel.fetchUserGameStats()
        authViewModel.fetchGlobalLeaderboard()
    }

    // Observe the game stats LiveData
    val gameStats by authViewModel.gameStats.observeAsState(emptyList())
    val globalLeaderboard by authViewModel.globalLeaderboard.observeAsState(emptyList())

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
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 25.dp)
            ) {
                // Display the header for the leaderboard stats
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), // Add padding around the row if needed
                    horizontalArrangement = Arrangement.SpaceEvenly, // Evenly space the items
                    verticalAlignment = Alignment.CenterVertically // Center items vertically
                ) {
                    Text(
                        text = "RANK",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                    Text(
                        text = "USERNAME",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                    Text(
                        text = "SCORE",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                    Text(
                        text = "LEVEL",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                    Text(
                        text = "DATE",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.4f) // Only take up 30% of available height
                        .fillMaxWidth()
                ) {
                    itemsIndexed(globalLeaderboard) { index, entry ->
                        LeaderboardItem(
                            rank = index + 1,
                            username = entry.name,
                            score = entry.score,
                            level = entry.level,
                            date = entry.date
                        )
                    }
                }
            }


            Text(
                text = "PERSONAL STATS",
                fontFamily = pixelFontFamily,
                fontSize = 32.sp,
                color = Color.White,
            )

            Column() {
                // Display the header for the game stats
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), // Add padding around the row if needed
                    horizontalArrangement = Arrangement.SpaceEvenly, // Evenly space the items
                    verticalAlignment = Alignment.CenterVertically // Center items vertically
                ){
                    Text(
                        text = "SCORE",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(12.dp)
                    )
                    Text(
                        text = "LEVEL",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(12.dp)
                    )
                    Text(
                        text = "DATE",
                        color = Color.White,
                        fontFamily = pixelFontFamily,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(12.dp)
                    )
                }
                // Display the game stats for the current user
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.7f) // Only take up 70% of available height
                        .fillMaxWidth()
                ) {
                    // Display each game's stats in a scrollable list
                    items(gameStats) { gameStat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp), // Add padding around the row if needed
                            horizontalArrangement = Arrangement.SpaceEvenly, // Evenly space the items
                            verticalAlignment = Alignment.CenterVertically // Center items vertically
                        ) {
                            Text(
                                text = "${gameStat["score"]}",
                                color = Color.White,
                                fontFamily = pixelFontFamily,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                            Text(
                                text = "${gameStat["level"]}",
                                color = Color.White,
                                fontFamily = pixelFontFamily,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                            Text(
                                text = "${gameStat["date"]}",
                                color = Color.White,
                                fontFamily = pixelFontFamily,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(2.dp)
                            )
                        }
                    }
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

@Composable
fun LeaderboardItem(
    rank: Int,
    username: String,
    score: Int,
    level: Int,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Rank
        Text(
            text = rank.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = pixelFontFamily,
            color = if (rank <= 3) Color(0xFFFFD700) else Color.White, // Gold for top 3
        )

        // Name
        Text(
            text = username,
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = pixelFontFamily,
        )

        // Score
        Text(
            text = score.toString(),
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = pixelFontFamily,
        )

        // Level
        Text(
            text = level.toString(),
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = pixelFontFamily,
        )

        // Date
        Text(
            text = date,
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = pixelFontFamily,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    CIS357FinalProjectQbertGameTheme {
        StatisticsScreen(modifier = Modifier, navController = NavHostController(LocalContext.current))
    }
}