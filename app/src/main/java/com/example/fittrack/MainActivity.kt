package com.example.fittrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fittrack.ui.theme.FitTrackTheme
import kotlin.math.max

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
    return listOf(navArgument(id) { NavType.StringType })
}

//TODO Make cards clickable for navigation
@Composable
fun AppNavigation(viewModel: AppViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(uiState, onNavigateToList = { navController.navigate("list/$it") })
        }

        composable("list/{workoutId}", createArgument("workoutId")) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("workoutId")!!
            ExerciseListScreen(id, onNavigateToDetail = { navController.navigate("detail/$it") })
        }

        composable(route = "detail/{workout&ExerciseId}", createArgument("workout&ExerciseId")) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("workout&ExerciseId")!!
            ExerciseDetailScreen(id, viewModel)
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


data class TextData(
    val text: String,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontWeight: FontWeight = FontWeight.Normal,
    val color: Color = Color.Black,
    val isBorderVisible: Boolean = false,
    val backgroundColor: Color = Color.Transparent
) {
    companion object{
        val borderShape = RoundedCornerShape(10.dp)
    }
    fun isBlank(): Boolean = text.isBlank()
    fun isNotBlank(): Boolean = text.isNotBlank()
}

@Composable
private fun Text(text: TextData) {

    var modifier = Modifier.clip(TextData.borderShape).background(text.backgroundColor)
    if(text.isBorderVisible) {
        modifier = modifier.border(1.dp, Color.Black, TextData.borderShape)
    }

    Box(modifier){
        Text(
            text = text.text,
            modifier = Modifier.padding(5.dp, 1.dp, 5.dp, 1.dp),
            fontSize = text.fontSize,
            fontWeight = text.fontWeight,
            color = text.color
        )
    }
}

//All texts in column1 should be aligned to the start
//All texts in column2 should be aligned to the end
//A row is skipped if both columns are blank at the same index

private val blankText = TextData("")

@Composable
private fun TextRow(text1: TextData = blankText, text2: TextData = blankText) {
    if (text1.isBlank() && text2.isBlank()) return

    val arrangement: Arrangement.Horizontal =
        if (text2.isBlank()) Arrangement.Start
        else if (text1.isBlank()) Arrangement.End
        else Arrangement.SpaceBetween
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(text1.isNotBlank()) Text(text1)
        if(text2.isNotBlank()) Text(text2)
    }
}

@Composable
private fun TextRows(column1Texts: List<TextData>, column2Texts: List<TextData>) {
    val numRows = max(column1Texts.size, column2Texts.size)
    for (i in 0 until numRows) {
        if (i >= column1Texts.size) {
            TextRow(text2 = column2Texts[i])
        } else if (i >= column2Texts.size) {
            TextRow(text1 = column1Texts[i])
        } else {
            TextRow(column1Texts[i], column2Texts[i])
        }

    }
}

//There will be no visual changes to the card if it's disabled.
//This Card will have 2 columns and max(c1.size, c2.size) rows.
//Column1 texts will be displayed on the left side and column2 texts
//will be displayed on the right.

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    column1Texts: List<TextData> = emptyList(),
    column2Texts: List<TextData> = emptyList(),
    onClick: (() -> Unit)? = null
) {
    BaseCard(modifier, onClick) {
        TextRows(column1Texts, column2Texts)
    }
}

@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val cd = CardDefaults
    val color = Color.White
    val elevation = 5.dp

    Card(
        onClick = onClick ?: {},
        modifier = modifier,
        enabled = onClick != null,
        colors = cd.cardColors(containerColor = color, disabledContainerColor = color),
        elevation = cd.cardElevation(defaultElevation = elevation, disabledElevation = elevation),
    ) {
        Column(
            Modifier
                .padding(5.dp, 9.dp, 5.dp, 9.dp)
                .fillMaxWidth(),
            Arrangement.spacedBy(8.dp),
            Alignment.CenterHorizontally,
            content = content
        )
    }

}