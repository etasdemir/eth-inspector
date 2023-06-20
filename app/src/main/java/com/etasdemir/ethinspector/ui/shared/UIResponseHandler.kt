package com.etasdemir.ethinspector.ui.shared

import androidx.compose.runtime.Composable
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.navigation.NavigationHandler
import timber.log.Timber

@Composable
fun <T> UIResponseHandler(
    state: UIResponseState<T>,
    navigationHandler: NavigationHandler,
    Content: @Composable (state: T) -> Unit
) {
    val stack = buildString {
        val stack = Thread.currentThread().stackTrace
        for (trace in stack) {
            appendLine(trace.toString())
        }
    }

    if (state is UIResponseState.Loading) {
        Timber.e("UIResponseHandler: Loading")
    }
    if (state is UIResponseState.Error) {
        Timber.e("UIResponseHandler: Error ${state.errorMessage}")
        Timber.e("Stack trace: $stack")
        navigationHandler.popBackStack()
    }
    if (state is UIResponseState.Success) {
        if (state.data == null) {
            Timber.e("UIResponseHandler: Response is success but data null")
            Timber.e("Stack trace: $stack")
            navigationHandler.popBackStack()
        } else {
            Content(state.data)
        }
    }
}