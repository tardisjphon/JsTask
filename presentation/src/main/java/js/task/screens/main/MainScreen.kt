package js.task.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import js.task.domain.usecase.model.DomainModel


class MainScreen
{
    @OptIn(ExperimentalFoundationApi::class)
    private fun getClickable() : Modifier
    {
        return Modifier.combinedClickable(onClick = {
            println("clicked !")
        })
    }

    @Composable
    fun ItemHolder(item: DomainModel) {
        Row(modifier = getClickable()) {
            Column {
                Text(text = item.userName)
            }
        }
    }

    @Composable
    fun SetRecyclerView(list: List<DomainModel>) {
        list.let { domainData ->
            LazyColumn {
                items(domainData) {
                    item ->
                    ItemHolder(item)
                }
            }
        }
    }
}