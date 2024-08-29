package com.gomsoo.shaken

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.gomsoo.shaken.core.designsystem.theme.ShakenTheme
import com.gomsoo.shaken.feature.favorite.navigation.navigateToFavorite
import com.gomsoo.shaken.feature.search.navigation.navigateToSearch
import com.gomsoo.shaken.feature.video.navigation.navigateToVideo
import com.gomsoo.shaken.navigation.ShakenNavHost
import com.gomsoo.shaken.navigation.TopLevelDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShakenTheme {
                var currentDestination by rememberSaveable {
                    mutableStateOf(TopLevelDestination.SEARCH)
                }
                val navController = rememberNavController()
                NavigationSuiteScaffold(
                    navigationSuiteItems = {
                        TopLevelDestination.entries.forEach {
                            item(
                                icon = {
                                    Icon(
                                        it.defaultIcon,
                                        contentDescription = stringResource(id = it.textId)
                                    )
                                },
                                label = { Text(stringResource(id = it.textId)) },
                                selected = currentDestination == it,
                                onClick = {
                                    currentDestination = it

                                    val navOptions = navOptions {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    when (it) {
                                        TopLevelDestination.SEARCH ->
                                            navController.navigateToSearch(navOptions)

                                        TopLevelDestination.FAVORITE ->
                                            navController.navigateToFavorite(navOptions)

                                        TopLevelDestination.VIDEO ->
                                            navController.navigateToVideo(navOptions)
                                    }
                                }
                            )
                        }
                    },
                    modifier = Modifier.imePadding()
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        ShakenNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShakenTheme {
        Greeting("Android")
    }
}
