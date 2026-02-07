package com.cypress.xingcodechallengeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cypress.xingcodechallengeapplication.Screen.Companion.ID_ARG
import com.cypress.xingcodechallengeapplication.presentation.ui.XingDetailsRoute
import com.cypress.xingcodechallengeapplication.presentation.ui.XingRepoRoute
import com.cypress.xingcodechallengeapplication.ui.theme.XingCodeChallengeApplicationTheme

sealed class Screen(val route: String) {
    companion object{
        const val ID_ARG = "id"
    }
    object XingList: Screen("xing_list_screen")
    object XingDetails: Screen("xing_detail_screen/{$ID_ARG}")

}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            XingCodeChallengeApplicationTheme {

                val navController = rememberNavController()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Screen.XingList.route
                    ) {

                        composable(Screen.XingList.route) {
                            XingRepoRoute(navController)
                        }

                        composable(
                            route = Screen.XingDetails.route,
                            arguments = listOf(navArgument(ID_ARG) { type = NavType.LongType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong(ID_ARG) ?: 0L
                            XingDetailsRoute(id = id)
                        }
                    }
                }
            }

        }
    }
}
