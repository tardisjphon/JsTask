package js.task.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import js.task.screens.details.DetailsScreen
import js.task.screens.main.MainScreen
import js.task.screens.model.ScreenArgument
import js.task.screens.model.ScreenName


class MainActivity : AppCompatActivity()
{
    private val mainScreen by lazy { MainScreen() }
    private val detailsScreen by lazy { DetailsScreen() }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)

        setScreensNavigation()
    }

    private fun setScreensNavigation()
    {
        setContent {

            MaterialTheme {

                val navController = rememberNavController()
                NavHost(
                        navController = navController,
                        startDestination = ScreenName.MAIN.title
                ) {
                    composable(ScreenName.MAIN.title) {
                        SetScreenMain(navController)
                    }
                    composable(
                            getScreenDetailsRoute(),
                            getScreenDetailsArguments()
                    ) { navBackStackEntry ->

                        SetScreenDetails(navBackStackEntry)
                    }
                }
            }
        }
    }

    @Composable
    private fun SetScreenMain(navigation : NavHostController)
    {
        mainScreen.SetList(
                navigation = navigation
        )
    }

    @Composable
    private fun SetScreenDetails(backStackEntry : NavBackStackEntry)
    {
        val id = backStackEntry.arguments?.getInt(ScreenArgument.ID.title)
        val api = backStackEntry.arguments?.getString(ScreenArgument.API.title)

        detailsScreen.Details(
                id = id,
                api = api
        )
    }

    private fun getScreenDetailsRoute() : String
    {
        return ScreenName.DETAILS.title + "?${ScreenArgument.ID.title}={${ScreenArgument.ID.title}}" + "&${ScreenArgument.API.title}={${ScreenArgument.API.title}}"
    }

    private fun getScreenDetailsArguments() : List<NamedNavArgument>
    {
        return listOf(navArgument(ScreenArgument.ID.title) {
            type = NavType.IntType
        },
                      navArgument(ScreenArgument.API.title) {
                          type = NavType.StringType
                      })
    }
}