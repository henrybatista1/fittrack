package com.example.fittrack

import androidx.compose.ui.graphics.Color

val workouts: List<Workout> = createWorkouts()

data class Activity(val name:String, val duration: Int)

data class Workout(
    val name: String,
    val exercises: List<Exercise> = listOf(Exercise())
)

data class Exercise(
    val name: String = "N/A",
    val difficulty: Difficulty = Difficulty.Beginner,
    val instructionPreview: String = "N/A",
    val instructions: String = instructionPreview,
    val duration: Int = 0
)

class Difficulty private constructor(val name: String, val color: Color) {
    companion object {
        val Beginner = Difficulty("Beginner", Color(175, 225, 175))
        val Intermediate = Difficulty("Intermediate", Color(255, 255, 143))
        val Advanced = Difficulty("Advanced", Color(196, 30, 58))
    }
}

private fun createWorkouts(): List<Workout>{
    return listOf(
        Workout("Strength"),
        Workout("Cardio"),
        Workout("Flexibility"),
        Workout("Balance")
    )
}