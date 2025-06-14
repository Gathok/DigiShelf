package de.malteans.digishelf.core.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(

) : ViewModel() {

    private val _state = MutableStateFlow(MainState())

    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainState()
        )

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.SetScreen -> {
                _state.value = state.value.copy(
                    selectedScreen = action.screen
                )
            }
        }
    }
}