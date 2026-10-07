package com.example.fittrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    Screen("FitTrack") {
        SummaryStatsCard()
        WorkoutPicker()
    }
}

@Composable
private fun SummaryStatsCard() {
    InfoCard(
        column1Texts = listOf(
            toStatsTextData("Completed:"),
            toStatsTextData("Total Time:")
        ),
        column2Texts = listOf(
            toStatsTextData("0"),
            toStatsTextData("0")
        ) //TODO Replace the 0 placeholders with viewModel values
    )
}

fun toStatsTextData(text: String): TextData {
    return TextData(text, 22.sp)
}

@Composable
private fun WorkoutPicker() {
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        for (workout in workouts) WorkoutCard(workout)
    }
}

@Composable
private fun WorkoutCard(workout: Workout) {
    val text = TextData(workout.name, 21.sp, FontWeight.Bold)
    InfoCard(column1Texts = listOf(text))
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()
}