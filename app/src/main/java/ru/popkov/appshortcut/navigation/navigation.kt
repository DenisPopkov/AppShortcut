package ru.popkov.appshortcut.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.popkov.appshortcut.MainScreen
import ru.popkov.appshortcut.models.ContactModel
import ru.popkov.appshortcut.models.ShortcutData
import ru.popkov.appshortcut.screens.ChatScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screens.Main.route) {
        composable(route = Screens.Main.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = "${Screens.Chat.route}/{contact_photo}/{contact_name}",
            arguments = listOf(
                navArgument("contact_photo") { type = NavType.IntType; defaultValue = 0 },
                navArgument("contact_name") { type = NavType.IntType; defaultValue = 0 })
        ) {
            ChatScreen(
                chatData = ShortcutData(
                    data = ContactModel(
                        contactName = it.arguments?.getInt("contact_name") ?: 0,
                        contactPhoto = it.arguments?.getInt("contact_photo") ?: 0
                    )
                )
            )
        }
    }
}