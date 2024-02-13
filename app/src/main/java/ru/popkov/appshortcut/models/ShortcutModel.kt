package ru.popkov.appshortcut.models

import android.graphics.drawable.Icon

data class ShortcutModel(
    val shortcutIcon: Icon,
    val shortLabel: String,
    val longLabel: String,
)