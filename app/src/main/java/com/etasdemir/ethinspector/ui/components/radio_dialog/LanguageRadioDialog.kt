package com.etasdemir.ethinspector.ui.components.radio_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.EthInspectorApp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AvailableLanguages
import com.etasdemir.ethinspector.data.domain_model.User
import com.etasdemir.ethinspector.utils.setAppLocale

@Composable
fun LanguageRadioDialog(user: User, updateUser: (User) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val systemDefaultStr = stringResource(id = R.string.system_default)
    val availableLanguages = AvailableLanguages.getAvailableLocalizedNames(context)
    val items = availableLanguages.plusElement(systemDefaultStr)
    val selectedIndex = if (user.useSystemLanguage) items.lastIndex else user.language.getIndex()
    val onSuccess = remember {
        { _: String, index: Int ->
            val newLanguage: AvailableLanguages
            if (index == items.lastIndex) {
                user.useSystemLanguage = true
                val systemLanguageCode =
                    (context.applicationContext as EthInspectorApp).systemLanguage
                user.language = AvailableLanguages.getFromISOCode(systemLanguageCode)
                    ?: AvailableLanguages.English
                newLanguage = user.language
            } else {
                user.useSystemLanguage = false
                user.language = AvailableLanguages.values()[index]
                newLanguage = user.language
            }
            context.setAppLocale(newLanguage.iso639Code, true)
            updateUser(user)
        }
    }

    val state = RadioDialogState(
        title = stringResource(id = R.string.language_select_title),
        description = stringResource(id = R.string.language_select_description),
        items = items,
        defaultSelectedItemIndex = selectedIndex,
        onSuccess = onSuccess,
        onCancel = onCancel,
    )

    RadioDialog(state = state)
}
