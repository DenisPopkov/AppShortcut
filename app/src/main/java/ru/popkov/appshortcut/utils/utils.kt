package ru.popkov.appshortcut.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(toastText: String) =
    Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()