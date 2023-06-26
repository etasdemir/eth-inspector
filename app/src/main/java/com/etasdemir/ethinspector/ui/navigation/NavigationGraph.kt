package com.etasdemir.ethinspector.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.etasdemir.ethinspector.ui.account.AccountScreen
import com.etasdemir.ethinspector.ui.address.AddressDetailScreen
import com.etasdemir.ethinspector.ui.block.BlockDetailScreen
import com.etasdemir.ethinspector.ui.contract.ContractDetailScreen
import com.etasdemir.ethinspector.ui.home.HomeScreen
import com.etasdemir.ethinspector.ui.saved_tx_and_block.SavedItemScreen
import com.etasdemir.ethinspector.ui.saved_tx_and_block.SavedItemType
import com.etasdemir.ethinspector.ui.search.InvalidSearchScreen
import com.etasdemir.ethinspector.ui.shared.SharedAccountViewModel
import com.etasdemir.ethinspector.ui.transaction.TransactionDetailScreen
import com.etasdemir.ethinspector.ui.wallet.WalletScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    sharedAccountViewModel: SharedAccountViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val navigationHandler = NavigationHandler(navController, coroutineScope)
    val bottomBar = @Composable {
        BottomBar(navController = navController, navHandler = navigationHandler)
    }

    NavHost(navController = navController, startDestination = NavigationRoute.Home.root) {
        composable(NavigationRoute.Home.route) {
            HomeScreen(navigationHandler, bottomBar)
        }

        composable(
            NavigationRoute.InvalidSearch.route,
            arguments = listOf(navArgument(NavigationRoute.InvalidSearch.searchedValue) {
                type = NavType.StringType
            })
        ) {
            val args = it.arguments
            val searchedValue = args?.getString(NavigationRoute.InvalidSearch.searchedValue)
            InvalidSearchScreen(searchedValue!!, navigationHandler)
        }

        composable(
            NavigationRoute.Wallet.route,
            arguments = listOf(navArgument(NavigationRoute.Wallet.shouldUpdate) {
                type = NavType.BoolType
                defaultValue = false
            })
        ) {
            val args = it.arguments
            val shouldUpdate = args?.getBoolean(NavigationRoute.Wallet.shouldUpdate) ?: false
            WalletScreen(navigationHandler, bottomBar, shouldUpdate)
        }

        composable(NavigationRoute.Profile.route) {
            AccountScreen(sharedAccountViewModel, bottomBar, navigationHandler)
        }

        composable(
            NavigationRoute.SavedItem.route,
            arguments = listOf(navArgument(NavigationRoute.SavedItem.type) {
                type = NavType.StringType
            })
        ) {
            val args = it.arguments
            val type = args?.getString(NavigationRoute.SavedItem.type)
            SavedItemScreen(SavedItemType.valueOf(type!!), navigationHandler)
        }

        composable(
            NavigationRoute.Transaction.route,
            arguments = listOf(navArgument(NavigationRoute.Transaction.txHash) {
                type = NavType.StringType
            })
        ) {
            val args = it.arguments
            val txHash = args?.getString(NavigationRoute.Transaction.txHash)
            TransactionDetailScreen(txHash!!, navigationHandler)
        }

        composable(
            NavigationRoute.Block.route,
            arguments = listOf(navArgument(NavigationRoute.Block.blockNumber) {
                type = NavType.StringType
            })
        ) {
            val args = it.arguments
            val blockNumber = args?.getString(NavigationRoute.Block.blockNumber)
            BlockDetailScreen(blockNumber!!, navigationHandler)
        }

        composable(
            NavigationRoute.Account.route,
            arguments = listOf(navArgument(NavigationRoute.Account.accountAddress) {
                type = NavType.StringType
            })
        ) {
            val args = it.arguments
            val address = args?.getString(NavigationRoute.Account.accountAddress)
            AddressDetailScreen(address!!, navigationHandler)
        }

        composable(
            NavigationRoute.Contract.route,
            arguments = listOf(navArgument(NavigationRoute.Contract.contractAddress) {
                type = NavType.StringType
            })
        ) {
            val args = it.arguments
            val address = args?.getString(NavigationRoute.Contract.contractAddress)
            ContractDetailScreen(address!!, navigationHandler)
        }
    }
}
