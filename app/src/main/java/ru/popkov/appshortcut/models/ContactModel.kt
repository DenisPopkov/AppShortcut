package ru.popkov.appshortcut.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ContactModel(
    @DrawableRes val contactPhoto: Int = 0,
    @StringRes val contactName: Int = 0,
)