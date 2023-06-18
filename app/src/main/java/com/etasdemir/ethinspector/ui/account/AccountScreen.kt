package com.etasdemir.ethinspector.ui.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.data.domain_model.User
import com.etasdemir.ethinspector.ui.account.component.AccountSettingsItem
import com.etasdemir.ethinspector.ui.account.component.AccountSettingsItemState
import com.etasdemir.ethinspector.ui.components.*
import com.etasdemir.ethinspector.ui.components.radio_dialog.LanguageRadioDialog

private enum class AccountItem {
    THEME, LANGUAGE, TRANSACTION, BLOCK, INFO
}

@Composable
@Preview
fun AccountScreen(
    accountViewModel: AccountViewModel = viewModel()
) {
    val userState by accountViewModel.userState.collectAsStateWithLifecycle()
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
                AccountItem.LANGUAGE -> {}
                AccountItem.TRANSACTION -> {}
                AccountItem.BLOCK -> {}
                AccountItem.INFO -> {}
            }
        }
    }

    Scaffold(topBar = {
        SimpleTopBar(title = stringResource(id = R.string.account_settings))
    }) {
        if (isLanguageDialogOpen) LanguageRadioDialog(user = userState!!, saveUser = saveUser)
        Column(modifier = Modifier
            .padding(it)
            .padding(top = 10.dp)) {
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.nights_stay_24),
                    stringResource(id = R.string.account_settings_theme),
                    { onItemClick(AccountItem.THEME) },
                    { FeintText(text = userState!!.theme.name) })
            )
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.language_24),
                    stringResource(id = R.string.account_settings_lang),
                    { onItemClick(AccountItem.LANGUAGE) },
                    { FeintText(text = userState!!.language.name) })
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
