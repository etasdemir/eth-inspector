package com.etasdemir.ethinspector.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.utils.clip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class AddressSaveModalState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val address: String,
    val onCancel: () -> Unit,
    val onSave: (name: String) -> Unit,
    val sheetState: SheetState
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressSaveModal(
    state: AddressSaveModalState
) {
    val coroutineScope = rememberCoroutineScope()
    val textFieldStyle = remember {
        TextStyle(
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
    }
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    var addressName by remember {
        mutableStateOf("")
    }
    val successButtonText =
        if (addressName.isNotBlank()) stringResource(id = R.string.create)
        else stringResource(id = R.string.skip)

    val onTextChange = remember {
        { newText: String ->
            addressName = newText
        }
    }

    val closeBottomSheet = remember {
        {
            focusManager.clearFocus(true)
            focusRequester.freeFocus()
            if (addressName.isNotBlank()) {
                state.onSave(addressName)
            } else {
                state.onCancel()
            }
            coroutineScope.launch(Dispatchers.Main) {
                state.sheetState.hide()
            }
        }
    }

    BackHandler(state.sheetState.isVisible) {
        closeBottomSheet()
    }
    ModalBottomSheet(
        sheetState = state.sheetState,
        onDismissRequest = { closeBottomSheet() },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.modal_address_title),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            Text(
                text = stringResource(id = R.string.modal_address_hash, state.address.clip(8)),
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .focusRequester(focusRequester)
                    .onPlaced {
                        focusRequester.requestFocus()
                    },
                shape = RoundedCornerShape(20.dp),
                value = addressName,
                onValueChange = onTextChange,
                textStyle = textFieldStyle,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.tertiary
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { closeBottomSheet() }) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 16.sp
                    )
                }
                Button(onClick = { closeBottomSheet() }) {
                    Text(
                        text = successButtonText,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}