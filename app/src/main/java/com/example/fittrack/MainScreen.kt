package com.example.fittrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(uiState: UiState, onNavigateToList: (String) -> Unit) {
    Screen("FitTrack") {
        SummaryStatsCard(uiState.completed, uiState.totalTime)
        WorkoutPicker(onNavigateToList)
    }
}

@Composable
private fun SummaryStatsCard(completed: Int, totalTime: Int) {
    InfoCard(
        column1Texts = listOf(
            toStatsTextData("Completed:"),
            toStatsTextData("Total Time:")
        ),
        column2Texts = listOf(
            toStatsTextData("$completed"),
            toStatsTextData("$totalTime")
        )
    )
}

fun toStatsTextData(text: String): TextData {
    return TextData(text, 22.sp)
}

@Composable
private fun WorkoutPicker(onNavigateToList: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        for(i in workouts.indices) WorkoutCard(workouts[i]) { onNavigateToList("$i") }
    }
}

@Composable
private fun WorkoutCard(workout: Workout, oncClick: () -> Unit){
    val text = TextData(workout.name, 21.sp, FontWeight.Bold)
    InfoCard(column1Texts = listOf(text), onClick = oncClick)
}