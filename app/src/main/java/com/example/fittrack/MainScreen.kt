package com.example.fittrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(viewModel: AppViewModel, onNavigateToList: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Screen("FitTrack") {
        SummaryStatsCard(uiState.completed, uiState.totalTime)
        WorkoutPicker(onNavigateToList)
        HistoryLog(Modifier.weight(1f).fillMaxSize(), uiState.activities) { viewModel.clear() }
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
private fun WorkoutPicker(onNavigateToList: (Int) -> Unit) {
    InfoCardList(items = workouts, onNavigateTo = onNavigateToList)
}

@Composable
private fun HistoryLog(modifier: Modifier, activities: List<Activity>, onClear: ()-> Unit) {
    BaseCard (modifier) {
        Text("Recent Activity", color = Color.Black)
        if(activities.isEmpty()){
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally){
                Text("No Recent Activity", fontSize = 30.sp)
            }
        }
        else {
            InfoCardList(modifier = Modifier.weight(1f), items = activities.reversed())
            Button(onClear, Modifier.fillMaxWidth()) { Text("Clear") }
        }
    }
}

private fun String.toTextData(): TextData = TextData(this, 21.sp)
private fun Int.toTextData(): TextData = toString().toTextData()