package com.example.fittrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        for(workout in workouts) {
            val text = workout.name.toTextData(FontWeight.Bold)
            InfoCard(column1Texts = listOf(text)) { onNavigateToList(workout.id) }
        }
    }
}

@Composable
private fun HistoryLog(modifier: Modifier, activities: List<Activity>, onClear: ()-> Unit) {

    BaseCard (
        modifier,
    ){
        Text("Recent Activity", color = Color.Black)
        if(activities.isEmpty()){
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally){
                Text("No Recent Activity", fontSize = 30.sp)
            }
        }
        else{
            LazyColumn(
                modifier,
                verticalArrangement = Arrangement.spacedBy(15.dp)

            ) {
                items(activities.reversed(), {it.id}) {
                    InfoCard(
                        column1Texts = listOf(it.name.toTextData()),
                        column2Texts = listOf(it.duration.toTextData())
                    )
                }
            }

            Button(onClear, Modifier.fillMaxWidth()) { Text("Clear") }
        }

    }
}

private fun String.toTextData(fontWeight: FontWeight = FontWeight.Normal): TextData = TextData(this, 21.sp, fontWeight)
private fun Int.toTextData(): TextData = toString().toTextData()