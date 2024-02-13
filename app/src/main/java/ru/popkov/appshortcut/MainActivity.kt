package ru.popkov.appshortcut

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.popkov.appshortcut.models.ContactModel
import ru.popkov.appshortcut.models.ShortcutData
import ru.popkov.appshortcut.models.ShortcutModel
import ru.popkov.appshortcut.models.ShortcutType
import ru.popkov.appshortcut.navigation.Navigation
import ru.popkov.appshortcut.navigation.Screens
import ru.popkov.appshortcut.screens.ChatContactList
import ru.popkov.appshortcut.ui.theme.AppShortcutTheme
import ru.popkov.appshortcut.utils.PINNED_ID
import ru.popkov.appshortcut.utils.SHORTCUT_ID
import ru.popkov.appshortcut.utils.ShortcutManager
import ru.popkov.appshortcut.utils.showToast

class MainActivity : ComponentActivity() {

    private val shortcutViewModel: ShortcutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            AppShortcutTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.surface),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Navigation(navController = navController)

                    val shortcutData by shortcutViewModel.shortcutData.collectAsState()
                    when (shortcutData.type) {
                        ShortcutType.STATIC -> context.showToast(toastText = stringResource(id = R.string.static_clicked_text))
                        ShortcutType.DYNAMIC -> {
                            context.showToast(toastText = stringResource(id = R.string.dynamic_clicked_text))
                            navController.navigate("${Screens.Chat.route}/${shortcutData.data.contactPhoto}/${shortcutData.data.contactName}")
                        }

                        ShortcutType.PINNED -> context.showToast(toastText = stringResource(id = R.string.pinned_clicked_text))
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.let {
            val shortcutType = when (intent.getStringExtra(SHORTCUT_ID)) {
                "static" -> ShortcutType.STATIC
                "dynamic" -> ShortcutType.DYNAMIC
                else -> ShortcutType.PINNED
            }

            shortcutViewModel.onShortcutClicked(
                ShortcutData(
                    type = shortcutType,
                    data = ContactModel(
                        contactPhoto = it.getIntExtra("contact_photo", 0),
                        contactName = it.getIntExtra("contact_name", 0)
                    )
                ),
            )
        }
    }

}

@Composable
fun MainScreen(
    navController: NavController = rememberNavController(),
) {
    val context = LocalContext.current
    val shortcutManager = ShortcutManager(context)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ChatContactList(navController = navController)
        Button(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                val shortcutIntent = Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    putExtra(SHORTCUT_ID, PINNED_ID)
                }

                val pinnedShortcut = ShortcutModel(
                    shortLabel = context.getString(R.string.pinned_short_label),
                    longLabel = context.getString(R.string.pinned_long_label),
                    shortcutIcon = Icon.createWithResource(context, R.drawable.ic_call)
                )

                shortcutManager.addPinnedShortcut(
                    applicationContext = context,
                    shortcut = pinnedShortcut,
                    shortcutId = PINNED_ID,
                    shortcutIntent = shortcutIntent,
                )
            }) {
            Text(text = stringResource(id = R.string.add_pinned_shortcut))
        }
    }
}