import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cis357finalprojectqbertgame.AuthViewModel
import com.example.cis357finalprojectqbertgame.CreateAccountScreen
import com.example.cis357finalprojectqbertgame.LoginScreen
import com.example.cis357finalprojectqbertgame.MainActivity
import com.example.cis357finalprojectqbertgame.StatisticsScreen
import androidx.compose.ui.Modifier
import com.example.cis357finalprojectqbertgame.main

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        // Main starting screen
        composable("main") {
            main("guest", modifier, navController, authViewModel)
        }
        // Login screen
        composable("login") {
            LoginScreen(modifier, navController, authViewModel)
        }
        // Create Account Screen
        composable("create_account") {
            CreateAccountScreen(modifier, navController, authViewModel)
        }
        // Statistics Screen
        composable("statistics") {
            StatisticsScreen(modifier, navController, authViewModel)
        }
    }
}