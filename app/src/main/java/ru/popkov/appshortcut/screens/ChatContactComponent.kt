package ru.popkov.appshortcut.screens

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.popkov.appshortcut.MainActivity
import ru.popkov.appshortcut.R
import ru.popkov.appshortcut.models.ShortcutModel
import ru.popkov.appshortcut.navigation.Screens
import ru.popkov.appshortcut.utils.DYNAMIC_ID
import ru.popkov.appshortcut.utils.SHORTCUT_ID
import ru.popkov.appshortcut.utils.ShortcutManager
import ru.popkov.appshortcut.utils.contacts
import ru.popkov.appshortcut.utils.showToast

@Composable
fun BoxScope.ChatContactList(
    navController: NavController,
) {
    val context = LocalContext.current
    val shortcutManager = ShortcutManager(context)
    LazyColumn(
        modifier = Modifier.align(Alignment.TopStart)
    ) {
        item {
            Text(
                modifier = Modifier.padding(bottom = 10.dp, start = 16.dp),
                text = stringResource(id = R.string.my_contacts),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        items(contacts) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val shortcutIntent = Intent(context, MainActivity::class.java).apply {
                            action = Intent.ACTION_VIEW
                            putExtra(SHORTCUT_ID, DYNAMIC_ID)
                            putExtra("contact_photo", it.contactPhoto)
                            putExtra("contact_name", it.contactName)
                        }

                        val dynamicShortcut = ShortcutModel(
                            shortLabel = context.getString(R.string.dynamic_short_label),
                            longLabel = context.getString(R.string.dynamic_long_label),
                            shortcutIcon = Icon.createWithResource(context, it.contactPhoto)
                        )

                        shortcutManager.addDynamicShortcut(
                            applicationContext = context,
                            shortcut = dynamicShortcut,
                            shortcutId = DYNAMIC_ID,
                            shortcutIntent = shortcutIntent,
                        )

                        context.showToast(toastText = "Dynamic shortcut was created")
                        navController.navigate("${Screens.Chat.route}/${it.contactPhoto}/${it.contactName}")
                    }
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(size = 48.dp),
                    painter = painterResource(id = it.contactPhoto),
                    contentDescription = "Contact photo",
                )
                Text(
                    text = stringResource(id = it.contactName),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}