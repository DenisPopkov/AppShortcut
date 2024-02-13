package ru.popkov.appshortcut.utils

import ru.popkov.appshortcut.R
import ru.popkov.appshortcut.models.ContactModel

val contacts = listOf(
    ContactModel(
        contactPhoto = R.drawable.ic_first_contact,
        contactName = R.string.first_contact_name
    ),
    ContactModel(
        contactPhoto = R.drawable.ic_second_contact,
        contactName = R.string.second_contact_name
    ),
)