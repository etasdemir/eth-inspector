package com.etasdemir.ethinspector.ui.components.radio_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.components.DialogComponent
import com.etasdemir.ethinspector.ui.components.DialogState
import timber.log.Timber


data class RadioDialogState(
    val title: String,
    val description: String,
    val items: List<String>,
    val defaultSelectedItemIndex: Int,
    val onSuccess: (item: String, index: Int) -> Unit,
    val onCancel: () -> Unit = {}
)

@Composable
fun RadioDialog(state: RadioDialogState) {
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = MaterialTheme.colorScheme.tertiary,
        unselectedColor = MaterialTheme.colorScheme.tertiary
    )
    var selectedIndex by remember {
        mutableIntStateOf(state.defaultSelectedItemIndex)
    }
    val dialogState = remember {
        DialogState(
            title = state.title,
            description = state.description,
            onCancel = state.onCancel,
            onSuccess = {
                state.onSuccess(state.items[selectedIndex], selectedIndex)
            }
        )
    }

    DialogComponent(state = dialogState) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            state.items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedIndex = index },
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = index == selectedIndex,
                        onClick = null,
                        colors = radioButtonColors
                    )
                    Text(text = item, fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun RadioDialogPreview() {
    val onCancel = {
        Timber.e("Cancel dialog")
    }
    val onSuccess = { item: String, index: Int ->
        Timber.e("Success dialog $item $index")
    }
    val items = listOf("System Default", "English", "Turkish")
    val default = 1
    val state = RadioDialogState(
        stringResource(id = R.string.language_select_title),
        stringResource(id = R.string.language_select_description),
        items,
        default,
        onSuccess,
        onCancel
    )
    RadioDialog(state)
}
