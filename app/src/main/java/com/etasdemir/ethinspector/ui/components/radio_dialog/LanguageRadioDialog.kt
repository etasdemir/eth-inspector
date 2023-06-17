package com.etasdemir.ethinspector.ui.components.radio_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.AvailableLanguages
import timber.log.Timber

@Composable
fun ThemeRadioDialog() {
    // TODO Static data
    val currentLanguage = AvailableLanguages.English

    val availableLanguages = AvailableLanguages.getAvailableLocalizedNames(LocalContext.current)
    val selectedIndex = currentLanguage.getIndex()
    val onSuccess = remember {
        { name: String, index: Int ->
            Timber.e("save $name $index")
        }
    }

    val state = RadioDialogState(
        title = stringResource(id = R.string.language_select_title),
        description = stringResource(id = R.string.language_select_description),
        items = availableLanguages,
        defaultSelectedItemIndex = selectedIndex,
        onSuccess = onSuccess,
        onCancel = {},
    )

    RadioDialog(state = state)
}