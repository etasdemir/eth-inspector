package com.etasdemir.ethinspector.ui.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.*

enum class AccountItem {
    THEME, LANGUAGE, TRANSACTION, BLOCK, INFO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun AccountScreen() {
    // TODO delete later
    val theme = "Dark"
    val language = "English"

    val onItemClick = remember {
        { type: AccountItem ->
            when (type) {
                AccountItem.THEME -> {}
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
        Column(modifier = Modifier
            .padding(it)
            .padding(top = 10.dp)) {
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.nights_stay_24),
                    stringResource(id = R.string.account_settings_theme),
                    { onItemClick(AccountItem.THEME) },
                    { FeintText(text = theme) })
            )
            AccountSettingsItem(
                state = AccountSettingsItemState(
                    painterResource(id = R.drawable.language_24),
                    stringResource(id = R.string.account_settings_lang),
                    { onItemClick(AccountItem.LANGUAGE) },
                    { FeintText(text = language) })
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
