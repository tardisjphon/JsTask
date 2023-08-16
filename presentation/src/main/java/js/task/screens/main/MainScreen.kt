package js.task.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import js.task.domain.usecase.model.DomainModel
import js.task.screens.model.ScreenArgument
import js.task.screens.model.ScreenName
import js.task.viewmodel.MainViewModel
import org.koin.compose.koinInject


class MainScreen
{

    @Composable
    fun SetList(
        viewModel : MainViewModel = koinInject(),
        navigation : NavHostController = rememberNavController()
    )
    {
        val list = getDomainModelList(viewModel)

        if (list.isNotEmpty())
        {
            list.let { domainData ->
                LazyColumn {
                    items(domainData) { item ->
                        ItemHolder(
                                item,
                                navigation
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun getDomainModelList(viewModel : MainViewModel) : List<DomainModel>
    {
        LaunchedEffect(Unit) {
            viewModel.getData()
        }

        return viewModel.dataList.collectAsStateWithLifecycle(emptyList()).value
    }

    @Composable
    fun ItemHolder(
        item : DomainModel, navigation : NavHostController
    )
    {
        Row(
                modifier = getClickable(
                        item,
                        navigation
                )
        ) {
            Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(text = item.userName ?: "")
                }
                if (item.imageUrl?.isNotBlank() == true)
                {
                    Row {
                        AsyncImage(
                                model = item.imageUrl,
                                alignment = Alignment.Center,
                                contentDescription = null,
                                modifier = Modifier.size(300.dp),
                        )
                    }
                }
                Row {
                    Text(text = item.apiName?.name ?: "")
                }
                Divider(
                        color = Color.LightGray,
                        thickness = 1.dp
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun getClickable(item : DomainModel, navigation : NavHostController) : Modifier
    {
        return Modifier.combinedClickable(onClick = {
            val route = ScreenName.DETAILS.title + "?${ScreenArgument.ID.title}=${item.id}&${ScreenArgument.API.title}=${item.apiName}"
            navigation.navigate(route)
        })
    }
}