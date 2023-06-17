package com.etasdemir.ethinspector.ui.components.radio_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import com.etasdemir.ethinspector.R
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
    val dialogProperties = remember {
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOff,
        )
    }
    val radioButtonColors = RadioButtonDefaults.colors(
        selectedColor = MaterialTheme.colorScheme.primary,
        unselectedColor = MaterialTheme.colorScheme.primary
    )
    var selectedIndex by remember {
        mutableStateOf(state.defaultSelectedItemIndex)
    }

    Dialog(state.onCancel, dialogProperties) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                color = MaterialTheme.colorScheme.tertiary,
                text = state.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = state.description, fontSize = 15.sp)
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionButton(
                    onClick = state.onCancel,
                    text = stringResource(id = R.string.cancel)
                )
                ActionButton(
                    onClick = {
                        state.onSuccess(state.items[selectedIndex], selectedIndex)
                    },
                    text = stringResource(id = R.string.save)
                )
            }
        }
    }
}

@Composable
private fun ActionButton(onClick: () -> Unit, text: String) {
    val buttonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.tertiary
    )
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 0.dp),
        colors = buttonColors
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
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
