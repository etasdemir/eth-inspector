package com.etasdemir.ethinspector.ui.navigation

import androidx.navigation.NavHostController
import com.etasdemir.ethinspector.ui.wallet.SavedItemType
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
        navHostController.navigate(NavigationRoute.Home.root)
    }

    fun navigateToInvalidSearch(searchedValue: String) {
        navHostController.navigate(
            NavigationRoute.InvalidSearch.withArgs(
                searchedValue
            )
        )
    }

    fun navigateToWallet() {
        navHostController.navigate(NavigationRoute.Wallet.root)
    }

    fun navigateToProfile() {
        navHostController.navigate(NavigationRoute.Profile.root)
    }

    fun navigateToSavedItem(type: SavedItemType) {
        navHostController.navigate(
            NavigationRoute.SavedItem.withArgs(
                type.name
            )
        )
    }

    fun navigateToTransaction(txHash: String) {
        navHostController.navigate(
            NavigationRoute.Transaction.withArgs(
                txHash
            )
        )
    }

    fun navigateToBlock(blockNumber: String) {
        navHostController.navigate(
            NavigationRoute.Block.withArgs(
                blockNumber
            )
        )
    }

    fun navigateToAccount(accountAddress: String) {
        navHostController.navigate(
            NavigationRoute.Account.withArgs(
                accountAddress
            )
        )
    }

    fun navigateToContract(contractAddress: String) {
        navHostController.navigate(
            NavigationRoute.Contract.withArgs(
                contractAddress
            )
        )
    }

}
