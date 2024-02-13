package ru.popkov.appshortcut.models

enum class ShortcutType {
    STATIC,
    DYNAMIC,
    PINNED,
    ;
}

data class ShortcutData(
    val data: ContactModel = ContactModel(),
    val type: ShortcutType = ShortcutType.STATIC,
)