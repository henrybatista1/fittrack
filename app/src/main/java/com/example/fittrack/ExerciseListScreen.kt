package com.example.fittrack

import androidx.compose.runtime.Composable

@Composable
fun ExerciseListScreen(workoutId: Int,  onNavigateToDetail: (Int) -> Unit) {
    val workout = getWorkout(workoutId)!!

    Exercise.showPreview = true
    Screen(workout.name) {
        InfoCardList(items = workout.exercises, onNavigateTo = onNavigateToDetail)
    }
}
