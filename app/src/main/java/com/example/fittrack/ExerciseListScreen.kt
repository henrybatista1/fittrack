package com.example.fittrack

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ExerciseListScreen(workoutId: Int,  onNavigateToDetail: (Int) -> Unit) {
    val workout = getWorkout(workoutId)!!
    val exercises = workout.exercises

    Screen(workout.name) {
        for (exercise in exercises){
            ExerciseCard(exercise){onNavigateToDetail(exercise.id)}
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise, isPreview: Boolean = true, onClick: (()-> Unit)? = null) {
    val recommendedText = "Recommended: ${exercise.duration} minutes"
    val instructions = if(isPreview) exercise.instructionPreview else exercise.instructions
    val difficulty = exercise.difficulty
    InfoCard(
        column1Texts = listOf(
            TextData(exercise.name, 21.sp, FontWeight.Bold),
            TextData(recommendedText, color = Color.Gray),
            TextData(instructions, color = Color.Gray)
        ),
        column2Texts = listOf(
            TextData(difficulty.name, isBorderVisible = true, backgroundColor = difficulty.color)
        ),
        onClick = onClick
    )

}
