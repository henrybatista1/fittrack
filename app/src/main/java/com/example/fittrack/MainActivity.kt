package com.example.fittrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fittrack.ui.theme.FitTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitTrackTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    FitTrackTheme {
        AppNavigation()
    }
}

private fun createArgument(id: String): List<NamedNavArgument> {
    return listOf(navArgument(id) { type = NavType.IntType })
}

@Composable
fun AppNavigation(viewModel: AppViewModel = viewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(viewModel) { navController.navigate("list/$it") }
        }

        composable("list/{workoutId}", createArgument("workoutId")) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getInt("workoutId")!!
            ExerciseListScreen(workoutId) { navController.navigate("detail/$it") }
        }

        composable("detail/{exerciseId}", createArgument("exerciseId")) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getInt("exerciseId")!!
            ExerciseDetailScreen(exerciseId, viewModel)
        }
    }
}

@Composable
fun Screen(title: String, content: @Composable (ColumnScope.() -> Unit)) {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(240, 240, 240))
            .padding(18.dp),
        Arrangement.spacedBy(20.dp),
    ) {
        Title(title)
        Column(
            Modifier
                .fillMaxSize(),
            Arrangement.spacedBy(20.dp),
            Alignment.CenterHorizontally,
            content
        )
    }
}

@Composable
private fun Title(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Serif,
    )
}