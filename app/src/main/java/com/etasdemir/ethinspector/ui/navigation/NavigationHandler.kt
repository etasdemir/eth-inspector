package com.etasdemir.ethinspector.ui.navigation

import androidx.navigation.NavHostController
import com.etasdemir.ethinspector.ui.saved_tx_and_block.SavedItemType
import timber.log.Timber

class NavigationHandler(
    private val navHostController: NavHostController
) {

    private fun printStack() {
        val prevRoutes = arrayListOf<String>()
        navHostController.currentBackStack.value.forEach {
            it.destination.route?.let { route ->
                prevRoutes.add(route)
            }
        }
        Timber.e("Current stack: $prevRoutes")
    }

    fun popBackStack() {
        if (navHostController.currentBackStack.value.size >= 2) {
            navHostController.popBackStack()
        }
        printStack()
    }

    fun navigateToHome() {
        navHostController.navigate(NavigationRoute.Home.root) {
            popUpTo(NavigationRoute.Home.route)
            launchSingleTop = true
        }
    }

    fun navigateToInvalidSearch(searchedValue: String) {
        navHostController.navigate(
            NavigationRoute.InvalidSearch.withArgs(
                searchedValue
            )
        ) {
            launchSingleTop = true
        }
    }

    fun navigateToWallet(shouldUpdate: Boolean) {
        navHostController.navigate(NavigationRoute.Wallet.withArgs(shouldUpdate.toString())) {
            popUpTo(NavigationRoute.Wallet.route)
            launchSingleTop = true
        }
    }

    fun navigateToProfile() {
        navHostController.navigate(NavigationRoute.Profile.root) {
            popUpTo(NavigationRoute.Profile.route)
            launchSingleTop = true
        }
    }

    fun navigateToSavedItem(type: SavedItemType) {
        navHostController.navigate(
            NavigationRoute.SavedItem.withArgs(
                type.name
            )
        ) {
            launchSingleTop = true
        }
    }

    fun navigateToTransaction(txHash: String) {
        navHostController.navigate(
            NavigationRoute.Transaction.withArgs(
                txHash
            )
        ) {
            launchSingleTop = true
        }
    }

    fun navigateToBlock(blockNumber: String) {
        navHostController.navigate(
            NavigationRoute.Block.withArgs(
                blockNumber
            )
        ) {
            launchSingleTop = true
        }
    }

    fun navigateToAccount(accountAddress: String) {
        navHostController.navigate(
            NavigationRoute.Account.withArgs(
                accountAddress
            )
        ) {
            launchSingleTop = true
        }
    }

    fun navigateToContract(contractAddress: String) {
        navHostController.navigate(
            NavigationRoute.Contract.withArgs(
                contractAddress
            )
        ) {
            launchSingleTop = true
        }
    }

}
