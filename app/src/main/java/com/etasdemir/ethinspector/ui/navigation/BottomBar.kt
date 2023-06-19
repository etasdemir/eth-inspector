package com.etasdemir.ethinspector.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.etasdemir.ethinspector.R

data class BottomNavItem(
    val name: String,
    val route: String,
    val navigate: () -> Unit,
    val painter: Painter
)

@Composable
fun BottomBar(
    navController: NavHostController,
    navHandler: NavigationHandler
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            stringResource(id = R.string.bottom_bar_home),
            NavigationRoute.Home.route,
            navHandler::navigateToHome,
            painterResource(id = R.drawable.bottom_bar_home_36)
        ),
        BottomNavItem(
            stringResource(id = R.string.bottom_bar_wallet),
            NavigationRoute.Wallet.route,
            navHandler::navigateToWallet,
            painterResource(id = R.drawable.bottom_bar_wallet_36)
        ),
        BottomNavItem(
            stringResource(id = R.string.bottom_bar_profile),
            NavigationRoute.Profile.route,
            navHandler::navigateToProfile,
            painterResource(id = R.drawable.bottom_bar_person_36)
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 10.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        val stack = navController.currentBackStackEntryAsState()
        val currentRoute = stack.value?.destination?.route

        bottomNavItems.forEach {
            val isItemSelected = currentRoute == it.route
            val color =
                if (isItemSelected) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    MaterialTheme.colorScheme.tertiary
                }

            NavigationBarItem(
                selected = isItemSelected,
                onClick = it.navigate,
                label = {
                    Text(
                        text = it.name,
                        color = color
                    )
                },
                icon = {
                    Icon(
                        painter = it.painter,
                        contentDescription = null,
                        tint = color
                    )
                }
            )

        }
    }

}

@Composable
@Preview
private fun BottomBarPreview() {
    val testController = rememberNavController()
    val navHandler = NavigationHandler(testController)
    BottomBar(testController, navHandler)
}