package com.etasdemir.ethinspector.ui.components.radio_dialog

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AvailableThemes
import com.etasdemir.ethinspector.data.domain_model.User

@Composable
fun ThemeRadioDialog(user: User, updateUser: (User) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val systemDefaultStr = stringResource(id = R.string.system_default)
    val availableThemes = AvailableThemes.getAvailableLocalizedNames(context)
    val items = availableThemes.plusElement(systemDefaultStr)
    val selectedIndex = if (user.useSystemTheme) items.lastIndex else user.theme.getIndex()

    val onSuccess = remember {
        { _: String, index: Int ->
            if (index == items.lastIndex) {
                user.useSystemTheme = true
                user.theme =
                    if (isSystemInDarkTheme) AvailableThemes.Dark else AvailableThemes.Light
            } else {
                user.useSystemTheme = false
                user.theme = AvailableThemes.values()[index]
            }
            updateUser(user)
        }
    }

    val state = RadioDialogState(
        title = stringResource(id = R.string.theme_select_title),
        description = stringResource(id = R.string.theme_select_description),
        items = items,
        defaultSelectedItemIndex = selectedIndex,
        onSuccess = onSuccess,
        onCancel = onCancel,
    )

    RadioDialog(state = state)

}