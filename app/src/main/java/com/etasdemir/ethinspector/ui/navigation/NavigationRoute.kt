package com.etasdemir.ethinspector.ui.navigation

import timber.log.Timber

sealed class NavigationRoute(val root: String) {

    object Home : NavigationRoute("home") {
        val route = addArgsToRoot(root)
    }

    object InvalidSearch : NavigationRoute("invalid_search") {
        const val searchedValue = "searchedValue"

        val route = addArgsToRoot(root, searchedValue)
    }

    object Wallet : NavigationRoute("wallet") {
        const val shouldUpdate = "shouldUpdate"

        val route = addArgsToRoot(root, shouldUpdate)
    }

    object Profile : NavigationRoute("profile") {
        val route = addArgsToRoot(root)
    }

    object SavedItem : NavigationRoute("saved_transaction_block") {
        const val type = "type"

        val route = addArgsToRoot(root, type)
    }

    object Transaction : NavigationRoute("transaction") {
        const val txHash = "txHash"

        val route = addArgsToRoot(root, txHash)
    }

    object Block : NavigationRoute("block") {
        const val blockNumber = "blockNumber"

        val route = addArgsToRoot(root, blockNumber)
    }

    object Account : NavigationRoute("account") {
        const val accountAddress = "accountAddress"

        val route = addArgsToRoot(root, accountAddress)
    }

    object Contract : NavigationRoute("contract") {
        const val contractAddress = "contractAddress"

        val route = addArgsToRoot(root, contractAddress)
    }


    /**
     * Args in place route: "transaction/txHash"
     * */
    fun withArgs(vararg args: String): String {
        return buildString {
            append(root)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }
}

private fun addArgsToRoot(root: String, vararg args: String) : String {
    val route = buildString {
        append(root)
        args.forEach{ arg ->
            append("/{$arg}")
        }
    }
    Timber.e("Navigation routes: $route")
    return route
}