package com.etasdemir.ethinspector.ui.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.User
import com.etasdemir.ethinspector.ui.account.component.AccountSettingsItem
import com.etasdemir.ethinspector.ui.account.component.AccountSettingsItemState
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.components.radio_dialog.LanguageRadioDialog
import com.etasdemir.ethinspector.ui.components.radio_dialog.ThemeRadioDialog
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import com.etasdemir.ethinspector.ui.shared.SharedAccountViewModel

private enum class AccountItem {
    THEME, LANGUAGE, TRANSACTION, BLOCK, INFO
}

@Composable
fun AccountScreen(
    accountViewModel: SharedAccountViewModel,
    navigationHandler: NavigationHandler
) {
    val userState by accountViewModel.userState.collectAsState(initial = null)

    var isThemeDialogOpen by remember {
        mutableStateOf(false)
    }
    var isLanguageDialogOpen by remember {
        mutableStateOf(false)
    }

    if (userState == null) {
        accountViewModel.getUser()
        return
    }

    val saveUser = remember {
        { newUser: User ->
            accountViewModel.saveUser(newUser)
            isLanguageDialogOpen = false
            isThemeDialogOpen = false
        }
    }

    val onItemClick = remember {
        { type: AccountItem ->
            when (type) {
                AccountItem.THEME -> {
                    isThemeDialogOpen = true
                }

                AccountItem.LANGUAGE -> {
                    isLanguageDialogOpen = true
                }

                AccountItem.TRANSACTION -> {}
                AccountItem.BLOCK -> {}
                AccountItem.INFO -> {}
            }
        }
    }

    Scaffold(topBar = {
        SimpleTopBar(title = stringResource(id = R.string.account_settings))
    }) {
        if (isLanguageDialogOpen) {
            LanguageRadioDialog(
                user = userState!!,
                updateUser = saveUser,
                onCancel = { isLanguageDialogOpen = false })
        }
        if (isThemeDialogOpen) {
            ThemeRadioDialog(
                user = userState!!,
                updateUser = saveUser,
                onCancel = { isThemeDialogOpen = false })
        }
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = 10.dp)
        ) {
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.nights_stay_24),
                    stringResource(id = R.string.account_settings_theme),
                    { onItemClick(AccountItem.THEME) },
                    { FeintText(text = userState!!.theme.getLocalizedName(LocalContext.current)) })
            )
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.language_24),
                    stringResource(id = R.string.account_settings_lang),
                    { onItemClick(AccountItem.LANGUAGE) },
                    { FeintText(text = userState!!.language.getLocalizedName(LocalContext.current)) })
            )
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.description_24),
                    stringResource(id = R.string.account_settings_tx),
                    { onItemClick(AccountItem.TRANSACTION) },
                    { ArrowIcon() }
                )
            )
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.box_outline_blank_24),
                    stringResource(id = R.string.account_settings_blocks),
                    { onItemClick(AccountItem.BLOCK) },
                    { ArrowIcon() }
                )
            )
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.info_24),
                    stringResource(id = R.string.account_settings_info),
                    { onItemClick(AccountItem.INFO) },
                    { ArrowIcon() }
                )
            )
        }
    }
}
