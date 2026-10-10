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
        column1Texts = listOf("Completed:".toTextData(), "Total Time:".toTextData()),
        column2Texts = listOf(completed.toTextData(), totalTime.toTextData())
    )
}

@Composable
private fun WorkoutPicker(onNavigateToList: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        for(id in workouts.indices) {
            val text = workouts[id].name.toTextData(FontWeight.Bold)
            InfoCard(column1Texts = listOf(text)) { onNavigateToList("$id") }
        }
    }
}

private fun String.toTextData(fontWeight: FontWeight = FontWeight.Normal): TextData = TextData(this, 21.sp, fontWeight)
private fun Int.toTextData(): TextData = toString().toTextData()