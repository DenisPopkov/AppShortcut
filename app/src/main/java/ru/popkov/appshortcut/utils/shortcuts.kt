package ru.popkov.appshortcut.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import ru.popkov.appshortcut.models.ShortcutModel

const val SHORTCUT_ID = "shortcut_id"
const val PINNED_ID = "pinned"
const val DYNAMIC_ID = "dynamic"

class ShortcutManager(
    applicationContext: Context
) {
    private val shortcutManager = getSystemService(applicationContext, ShortcutManager::class.java)

    fun addDynamicShortcut(
        applicationContext: Context,
        shortcut: ShortcutModel,
        shortcutIntent: Intent,
        shortcutId: String,
    ) {
        val shortcutInfo = ShortcutInfoCompat.Builder(applicationContext, shortcutId)
            .setShortLabel(shortcut.shortLabel)
            .setLongLabel(shortcut.longLabel)
            .setIcon(IconCompat.createFromIcon(applicationContext, shortcut.shortcutIcon))
            .setIntent(shortcutIntent)
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(applicationContext, shortcutInfo)
    }

    fun addPinnedShortcut(
        applicationContext: Context,
        shortcut: ShortcutModel,
        shortcutIntent: Intent,
        shortcutId: String,
    ) {
        if (shortcutManager!!.isRequestPinShortcutSupported) {
            val shortcutInfo = ShortcutInfo.Builder(applicationContext, shortcutId)
                .setShortLabel(shortcut.shortLabel)
                .setLongLabel(shortcut.longLabel)
                .setIcon(shortcut.shortcutIcon)
                .setIntent(shortcutIntent)
                .build()
            pinShortcut(applicationContext, shortcutInfo)
        }
    }

    private fun pinShortcut(
        applicationContext: Context,
        shortcut: ShortcutInfo,
    ) {
        val callbackIntent = shortcutManager!!.createShortcutResultIntent(shortcut)
        val successPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            callbackIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        shortcutManager.requestPinShortcut(shortcut, successPendingIntent.intentSender)
    }
}