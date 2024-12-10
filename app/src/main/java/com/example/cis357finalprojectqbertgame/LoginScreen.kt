package com.example.cis357finalprojectqbertgame
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cis357finalprojectqbertgame.ui.theme.CIS357FinalProjectQbertGameTheme
import com.example.cis357finalprojectqbertgame.ui.theme.pixelFontFamily

/**
 * The LoginScreen function builds the log in screen
 * for logging into an account using firebase authentication.
 * The screen includes a username/email input text field,
 * password input text field, sign in button, and create account
 * button.
 *
 * @param modifier The modifier to apply to this layout
 * @param navController The navigation host controller manages screen navigation
 * @param authViewModel The view model for firebase authentication and firestore related jobs
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController,
                authViewModel: AuthViewModel = AuthViewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState = authViewModel.loginState.observeAsState(AuthViewModel.LoginState.NotLoggedIn)

    Box(
        modifier = modifier
            .background(Color(0xFF001F3F))
            .fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(
                text = "SPACE HOPPER",
                fontFamily = pixelFontFamily,
                fontSize = 60.sp,
                color = Color.White,
            )
            Box (
                modifier = modifier
                    .padding(top = 130.dp, bottom = 10.dp)
                    .background(Color.LightGray)
                    .border(3.dp, Color.White) // Adds a blue border of 2dp thickness
            ) {
                OutlinedTextField(value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "EMAIL",
                        fontFamily = pixelFontFamily,
                        fontSize = 36.sp)},
                    colors = outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
            Box (
                modifier = modifier
                    .padding(10.dp)
                    .background(Color.LightGray)
                    .border(3.dp, Color.White) // Adds a blue border of 2dp thickness
            ) {
                OutlinedTextField(value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "PASSWORD",
                        fontFamily = pixelFontFamily,
                        fontSize = 36.sp)},
                    visualTransformation = PasswordVisualTransformation(),
                    colors = outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White,
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Sign in button
                TextButton(
                    onClick = {
                        authViewModel.login(username, password)
                        // navController.popBackStack() // Return to main screen
                    },
                    modifier = Modifier
                        .padding(6.dp),
                ) {
                    Text("SIGN IN",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontFamily = pixelFontFamily)
                }
                // Check for log in success
                LaunchedEffect(loginState.value) {
                    when (loginState.value) {
                        is AuthViewModel.LoginState.Success -> {
                            Log.d("print", "Login successful, pop stack")
                            navController.popBackStack()
                        }
                        else -> {
                            Log.d("print", "Login failed")
                        }
                    }
                }
                /*
                loginState?.let { result ->
                    result.onSuccess {
                        // Avoid over popping stack since this may execute multiple times
                        // We just want to pop back stack once to return to main screen
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        if (currentRoute != "main") {
                            Log.d("print", "Login successful, pop stack")
                            navController.popBackStack()
                        }
                    }
                    result.onFailure {
                        Toast.makeText(LocalContext.current, it.message ?: "Error", Toast.LENGTH_SHORT).show()
                    }
                } */
                // Sign up button
                TextButton(
                    onClick = {
                        navController.navigate("create_account")
                    },
                    modifier = Modifier
                        .padding(6.dp),
                ) {
                    Text("SIGN UP",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontFamily = pixelFontFamily)
                }
            }
            // Cancel sign up button
            TextButton(
                onClick = {
                    navController.popBackStack() // Go back to main
                },
                modifier = Modifier
                    .padding(18.dp),
            ) {
                Text("RETURN",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontFamily = pixelFontFamily)
            }
        }
    }
}

/**
 * The LoginScreenPreview function simply provides a preview of the
 * Login and create account screen composable for development.
 *
 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CIS357FinalProjectQbertGameTheme {
        LoginScreen(modifier = Modifier, navController = NavHostController(LocalContext.current))
    }
}