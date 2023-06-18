package com.etasdemir.ethinspector.ui.components.radio_dialog

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AvailableLanguages
import com.etasdemir.ethinspector.data.domain_model.User
import java.util.Locale as JavaLocale

@Composable
fun LanguageRadioDialog(user: User, saveUser: (User) -> Unit) {
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
                val systemLanguageCode = Locale.current.language
                newLanguage = AvailableLanguages.getFromISOCode(systemLanguageCode)
                    ?: AvailableLanguages.English
            } else {
                user.useSystemLanguage = false
                user.language = AvailableLanguages.values()[index]
                newLanguage = user.language
            }
            context.setAppLocale(newLanguage.iso639Code)
            saveUser(user)
        }
    }

    val state = RadioDialogState(
        title = stringResource(id = R.string.language_select_title),
        description = stringResource(id = R.string.language_select_description),
        items = items,
        defaultSelectedItemIndex = selectedIndex,
        onSuccess = onSuccess,
        onCancel = {},
    )

    RadioDialog(state = state)
}

fun Context.setAppLocale(language: String): Context {
    val locale = JavaLocale(language)
    JavaLocale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}
