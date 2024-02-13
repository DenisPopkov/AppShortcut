package ru.popkov.appshortcut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.popkov.appshortcut.models.ShortcutData

class ShortcutViewModel : ViewModel() {
    private val _shortcutData = MutableStateFlow(ShortcutData())
    val shortcutData: StateFlow<ShortcutData> = _shortcutData

    fun onShortcutClicked(shortcutData: ShortcutData) {
        viewModelScope.launch {
            _shortcutData.value = _shortcutData.value.copy(
                data = shortcutData.data,
                type = shortcutData.type
            )
        }
    }
}
