package com.etasdemir.ethinspector.ui.components

import androidx.compose.runtime.*

val LocalShowAppDialog = compositionLocalOf<((DialogState) -> Unit)?> { null }

@Composable
fun AppDialog(Content: @Composable () -> Unit) {
    var appDialogState by remember {
        mutableStateOf<DialogState?>(null)
    }

    val showAppDialog = { state: DialogState ->
        appDialogState = DialogState(
            title = state.title,
            description = state.description,
            onCancel = {
                state.onCancel()
                appDialogState = null
            },
            onSuccess = if (state.onSuccess != null) {
                {
                    state.onSuccess.invoke()
                    appDialogState = null
                }
            } else null
        )
    }

    appDialogState?.let {
        DialogComponent(state = it)
    }

    CompositionLocalProvider(LocalShowAppDialog provides showAppDialog) {
        Content()
    }
}