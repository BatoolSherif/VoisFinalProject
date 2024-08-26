package com.example.voisfinalproject
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voisfinalproject.ui.DetailsScreen
import com.example.voisfinalproject.ui.MainScreen

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MainScreen()
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                    val navController = rememberNavController()
                    // Set up the navigation graph
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MainScreen(navController = navController)
                        }
                        composable("details/{username}") { backStackEntry ->
                            val username = backStackEntry.arguments?.getString("username")
                            username?.let {
                                DetailsScreen(username = it)
                            }
                        }
                    }


        }
    }
}
