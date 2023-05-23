package com.etasdemir.ethinspector.ui.address

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etasdemir.ethinspector.R
import com.etasdemir.ethinspector.ui.address.components.*
import com.etasdemir.ethinspector.ui.components.*
import org.w3c.dom.Text
import timber.log.Timber

@Composable
@Preview
fun AddressDetailScreen() {
    val onTransferItemClick = remember {
        {
            Timber.e("AddressDetailScreen::onTransferItemClick")
        }
    }

    val onTransactionItemClick = remember {
        { txHash: String ->
            Timber.e("navigate to transaction detail with $txHash")
        }
    }

    val onTokenItemClick = remember {
        { address: String ->
            Timber.e("navigate to token detail with $address")
        }
    }

    // TODO static data
    val address = "0x1412AGHDK1H23G123HJH2KJ32H"
    val balanceEth = "29.03841209"
    val balanceUsd = "143,324.03841209"
    val txCount = 62489.0

    val isAddressFavourite = false
    val topBarTitle = stringResource(id = R.string.address_details)
    val topBarState = remember {
        DetailTopBarState(
            barTitle = topBarTitle,
            isFavouriteEnabled = isAddressFavourite,
            onFavouriteClick = {},
            textToCopy = address
        )
    }

    val addressInfoState = AddressInfoColumnState(
        address,
        balanceEth,
        balanceUsd,
        txCount
    )

    val txItemState = listOf(
        AddressTransactionItemState(
            "0x9868768A6SD86A87ASD6A8S787A66S87D6A8",
            4232.3030,
            "142353532",
            "21.02.2020",
            "15:23:17"
        ) {},
        AddressTransactionItemState(
            "0x9868768A6SD86A87ASD6A8S787A66S87D6A8",
            4232.3030,
            "142353532",
            "21.02.2020",
            "15:23:17"
        ) {}
    )

    val tokenItems = listOf(
        TokenItemState(
            "Furucombo (COMBO)", "0X12837987HG12JGH12GH3F89FS7", "12831894289312831200"
        ) {},
        TokenItemState(
            "Furucombo (COMBO)", "0X12837987HG12JGH12GH3F89FS7", "12831894289312831200"
        ) {}
    )

    val transferItems = listOf(
        TransferItemState(
            "0xA78SD6A8S6C87F87S6DF8S7DF6ASD987AS9D",
            "Maker (MKR)",
            1532.112323,
            "530430240",
            "21.02.2020 13:57:45"
        ) {},
        TransferItemState(
            "0xA78SD6A8S6C87F87S6DF8S7DF6ASD987AS9D",
            "Maker (MKR)",
            -1532.112323,
            "530430240",
            "21.02.2020 13:57:45"
        ) {}
    )



    Scaffold(topBar = { DetailTopBar(state = topBarState) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            item {
                AddressInfoColumn(state = addressInfoState)
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.tokens),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(tokenItems) { token ->
                TokenItem(state = token)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.transactions),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(txItemState) { item ->
                AddressTransactionItem(item)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    text = stringResource(id = R.string.transfers),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(transferItems) { transfer ->
                TransferItem(state = transfer)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }
    }
}