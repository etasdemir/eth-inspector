package com.etasdemir.ethinspector.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


data class DetailTopBarState(
    val barTitle: String,
    val isFavouriteEnabled: Boolean,
    val onFavouriteClick: (previous: Boolean, now: Boolean) -> Unit,
    val textToCopy: String,
)

@Composable
fun DetailTopBar(state: DetailTopBarState, navigateBack: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(2f)
            ) {
                BackButton(navigateBack)
                Text(
                    text = state.barTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                FavouriteButton(state.isFavouriteEnabled, state.onFavouriteClick)
                ClipboardButton(state.textToCopy)
                ShareButton()
            }
        }
        Divider(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary)
    }
}


@Composable
@Preview
fun DetailTopBarPreview() {
    val state = DetailTopBarState(
        barTitle = "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2",
        isFavouriteEnabled = true,
        onFavouriteClick = { _: Boolean, _: Boolean -> },
        textToCopy = "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2",
    )
    DetailTopBar(state) {}
}