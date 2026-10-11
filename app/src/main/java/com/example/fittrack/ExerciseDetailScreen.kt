package com.example.fittrack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseDetailScreen(exerciseId: Int, viewModel: AppViewModel) {
    val exercise = getExercise(exerciseId)!!

    Screen(exercise.name) {
        InfoCard(exercise)
        LoggingForm(exercise, viewModel)
    }
}

@Composable
private fun LoggingForm(exercise: Exercise, viewModel: AppViewModel) {
    var text by rememberSaveable { mutableStateOf("") }
    val duration = text.toIntOrNull() ?: 0

    BaseCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(15.dp)) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                isError = duration <= 0,
                colors = OutlinedTextFieldDefaults.colors(Color.Black),
                singleLine = true,
                supportingText = { Text("Invalid Input") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    text = ""
                    viewModel.update(Activity(exercise.name, duration))
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = duration > 0

            ) {
                Text("Submit")
            }
        }

    }
}