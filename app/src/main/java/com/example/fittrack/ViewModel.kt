package com.example.fittrack

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val completed: Int = 0,
    val totalTime: Int = 0,
    val activity: List<Activity> = emptyList()
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    fun update(activity: Activity) {
        val activities = _uiState.value.activity.toMutableList()
        activities.add(activity)
        _uiState.update { it.copy(
            completed = it.completed + 1,
            totalTime = it.totalTime + activity.duration,
            activity = activities
        ) }
    }

    fun clear(){
        _uiState.update { it.copy(
            completed = 0,
            totalTime = 0,
            activity = emptyList()
        ) }
    }

}