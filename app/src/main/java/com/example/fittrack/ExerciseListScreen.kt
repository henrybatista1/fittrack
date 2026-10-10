package com.example.fittrack

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ExerciseListScreen(workout: Workout) {
    Screen(workout.name) {
        for(exercise in workout.exercises){
            ExerciseCard(exercise)
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise, isPreview: Boolean = true ){
    val recommendedText = "Recommended: ${exercise.duration} minutes"
    val instructions = if(isPreview) exercise.instructionPreview else exercise.instructions
    InfoCard(
        column1Texts = listOf(
            TextData(exercise.name, 21.sp, FontWeight.Bold),
            TextData(recommendedText, color = Color.Gray),
            TextData(instructions, color = Color.Gray)
        ),
        column2Texts = listOf(
            TextData(exercise.difficulty.name)
        ),
    )

}
