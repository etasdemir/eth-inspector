package com.etasdemir.ethinspector.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.etasdemir.ethinspector.EthInspectorApp
import com.etasdemir.ethinspector.data.domain_model.AvailableLanguages
import com.etasdemir.ethinspector.ui.shared.SharedAccountViewModel
import com.etasdemir.ethinspector.utils.setAppLocale

@Composable
fun EthInspectorLanguage(
    accountViewModel: SharedAccountViewModel,
    Content: @Composable () -> Unit,
) {
    val userState by accountViewModel.userState.collectAsState(initial = null)

    var isAppReady by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = "fetch_user") {
        accountViewModel.getUser()
    }

    if (userState == null) {
        return
    }

    LaunchedEffect(key1 = "initialize_app_language") {
        val language: String = if (userState!!.useSystemLanguage) {
            val systemLanguageCode = (context.applicationContext as EthInspectorApp).systemLanguage
            val systemLanguage = AvailableLanguages.getFromISOCode(systemLanguageCode)
                ?: AvailableLanguages.English
            systemLanguage.iso639Code
        } else {
            userState!!.language.iso639Code
        }
        context.setAppLocale(language, false)
        isAppReady = true
    }

    if (isAppReady) {
        Content()
    }
}