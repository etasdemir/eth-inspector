package com.etasdemir.ethinspector.ui.components

import android.content.*
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R

@Composable
fun ClipboardButton(textToCopy: String) {
    val context = LocalContext.current
    val onCopyClick = remember {
        {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(textToCopy.substring(0, 10), textToCopy)
            clipboard.setPrimaryClip(clip)
        }
    }

    IconButton(onClick = onCopyClick, modifier = Modifier.size(36.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.content_copy_36),
            contentDescription = "Copy to clipboard",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
@Preview
fun ClipboardButtonPreview() {
    val someString = "test"
    ClipboardButton(textToCopy = someString)
}
