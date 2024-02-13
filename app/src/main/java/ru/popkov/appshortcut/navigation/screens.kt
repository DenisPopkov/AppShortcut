package ru.popkov.appshortcut.navigation

sealed class Screens(val route: String) {
    data object Main : Screens("main_screen")
    data object Chat : Screens("chat_screen")
}