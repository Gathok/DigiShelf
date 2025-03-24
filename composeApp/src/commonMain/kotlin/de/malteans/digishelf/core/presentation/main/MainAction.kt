package de.malteans.digishelf.core.presentation.main

import de.malteans.digishelf.core.presentation.main.components.Screen

sealed interface MainAction {
    data class SetScreen(val screen: Screen) : MainAction
}