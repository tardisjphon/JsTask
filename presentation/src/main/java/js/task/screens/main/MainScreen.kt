package js.task.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import js.task.domain.usecase.model.DomainModel


class MainScreen
{
    @OptIn(ExperimentalFoundationApi::class)
    private fun getClickable(item : DomainModel, navigation: NavHostController) : Modifier
    {
        return Modifier.combinedClickable(onClick = {
            println("clicked !")
            //navigation.navigate("")
        })
    }

    @Composable
    fun ItemHolder(
        item : DomainModel,
        navigation: NavHostController
    )
    {
        Row(modifier = getClickable(item, navigation)) {
            Column {
                Row {
                    Text(text = item.userName)
                }
                Row {
                    Text(text = item.imageUrl)
                }
                Row {
                    Text(text = item.apiName)
                }
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }


    @Preview
    @Composable
    fun SetRecyclerView(list : List<DomainModel> = arrayListOf(
                DomainModel(
                        0,
                        "userName",
                        "imageUrl",
                        "apiName"
                ))
    )
    {
        val navController = rememberNavController()

        list.let { domainData ->
            LazyColumn {
                items(domainData) { item ->
                    ItemHolder(item, navController)
                }
            }
        }
    }
}