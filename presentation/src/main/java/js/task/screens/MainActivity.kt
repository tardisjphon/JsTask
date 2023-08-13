package js.task.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import js.task.domain.usecase.model.DomainModel
import js.task.screens.details.DetailsScreen
import js.task.screens.main.MainScreen
import js.task.screens.model.ScreenArgument
import js.task.screens.model.ScreenName
import js.task.viewmodel.DataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity()
{
    private val viewModel : DataViewModel by viewModel()
    private val mainScreen by lazy { MainScreen() }
    private val detailsScreen by lazy { DetailsScreen() }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)

        setScreens()

        viewModel.getData()
    }

    private fun setScreens()
    {
        setContent {

            val data = viewModel.dataList.collectAsStateWithLifecycle(
                        initialValue = arrayListOf(
                                DomainModel()
                        )
                ).value

            MaterialTheme {

                val navController = rememberNavController()
                NavHost(
                        navController = navController,
                        startDestination = ScreenName.MAIN.title
                ) {
                    composable(ScreenName.MAIN.title) {
                        mainScreen.SetRecyclerView(
                                data,
                                navController
                        )
                    }
                    composable(ScreenName.DETAILS.title + "?${ScreenArgument.ID.title}={${ScreenArgument.ID.title}}" + "&${ScreenArgument.API.title}={${ScreenArgument.API.title}}",
                               listOf(navArgument(ScreenArgument.ID.title) {
                                   type = NavType.IntType
                               },
                                      navArgument(ScreenArgument.API.title) {
                                          type = NavType.StringType
                                      })) { backStackEntry ->

                        val id = backStackEntry.arguments?.getInt(ScreenArgument.ID.title)
                        val api = backStackEntry.arguments?.getString(ScreenArgument.API.title)
                        if (id != null && api != null)
                        {
                            val selectedData = data.firstOrNull { it.id == id && it.apiName?.name == api }
                            if (selectedData != null)
                            {
                                detailsScreen.Details(selectedData)
                            }
                        }
                    }
                }
            }
        }
    }
}