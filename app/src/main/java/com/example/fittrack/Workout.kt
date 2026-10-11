package com.example.fittrack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val items = mutableListOf<Item>()

val workouts: List<Workout> = createWorkouts()

abstract class Item {
    val id: Int = items.size
    init { items.add(this) }
    abstract fun toInfoCardData(): InfoCardData
}

fun Int.isValidId(): Boolean = this in items.indices


fun getExercise(id: Int): Exercise? {
    return if (id.isValidId()) (items[id] as? Exercise) else null
}

fun getWorkout(id: Int): Workout? {
    return if (id.isValidId()) (items[id] as? Workout) else null
}

class Activity(val name:String, val duration: Int): Item(){
    override fun toInfoCardData(): InfoCardData =
        InfoCardData(
            column1Texts = listOf(TextData(name)),
            column2Texts = listOf(TextData(duration.toString()))
        )

}

class Workout(
    val name: String,
    val exercises: List<Exercise> = listOf(Exercise())
): Item() {
    override fun toInfoCardData() =
        InfoCardData(column1Texts = listOf( TextData(name, 21.sp, FontWeight.Bold)) )
}

class Exercise(
    val name: String = "N/A",
    val difficulty: Difficulty = Difficulty.Beginner,
    val instructionPreview: String = "N/A",
    val instructions: String = instructionPreview,
    val duration: Int = 0

): Item() {

    var showPreview = false

    override fun toInfoCardData(): InfoCardData {
        val instructions = if(showPreview) instructionPreview else this.instructions
        val recommendedText = "Recommended: $duration minutes"

        return InfoCardData(
            column1Texts = listOf(
                TextData(name, 21.sp, FontWeight.Bold),
                TextData(recommendedText, color = Color.Gray),
                TextData(instructions, color = Color.Gray)
            ),
            column2Texts = listOf(
                TextData(difficulty.name, isBorderVisible = true, backgroundColor = difficulty.color)
            ),
        )
    }
}

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