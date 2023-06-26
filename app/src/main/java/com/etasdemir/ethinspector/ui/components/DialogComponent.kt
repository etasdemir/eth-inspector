package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import com.etasdemir.ethinspector.R

data class DialogState(
    val title: String,
    val description: String,
    val onCancel: () -> Unit = {},
    val onSuccess: (() -> Unit)? = null,
)

@Composable
fun DialogComponent(state: DialogState, AdditionalContent: (@Composable () -> Unit)? = null) {
    val dialogProperties = remember {
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOff,
        )
    }

    Dialog(state.onCancel, dialogProperties) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 30.dp, vertical = 50.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    color = MaterialTheme.colorScheme.tertiary,
                    text = state.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            item {
                Text(
                    text = state.description,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                AdditionalContent?.invoke()
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (state.onSuccess == null) Arrangement.End else Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButtonComponent(
                        onClick = state.onCancel,
                        text = stringResource(id = R.string.cancel)
                    )
                    if (state.onSuccess != null) {
                        OutlinedButtonComponent(
                            onClick = {
                                state.onSuccess.invoke()
                            },
                            text = stringResource(id = R.string.confirm)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF)
@Composable
fun DialogPreview() {
    val state = DialogState(
        "Title",
        "Message",
        {},
        { }
    )
    DialogComponent(state)
}
