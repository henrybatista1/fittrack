package com.example.fittrack

import androidx.compose.runtime.Composable

@Composable
fun ExerciseListScreen(workoutId: Int,  onNavigateToDetail: (Int) -> Unit) {
    val workout = getWorkout(workoutId)!!
    val exercises = workout.exercises

    Screen(workout.name) {
        for (exercise in exercises){
            exercise.showPreview = true
            InfoCard(exercise) { onNavigateToDetail(exercise.id) }
        }
    }
}
