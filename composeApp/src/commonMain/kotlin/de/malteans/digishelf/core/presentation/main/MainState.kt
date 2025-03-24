package de.malteans.digishelf.core.presentation.main

import de.malteans.digishelf.core.presentation.main.components.Screen

data class MainState(
    val selectedScreen: Screen = Screen.Overview
)
