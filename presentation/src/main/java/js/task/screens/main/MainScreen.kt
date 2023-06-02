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
import js.task.data.local.db.model.DailyMotion


class MainScreen {
    private val listDailyMotion = listOf(DailyMotion("0", "main"),
            DailyMotion("1", "other"))

    @Composable
    fun setRecyclerView() {

        LazyColumn {
            items(listDailyMotion) {
                item ->
                ItemHolder(item)
            }
        }
    }

    @Composable
    fun ItemHolder(item: DailyMotion) {
        Row(modifier = getClickable()) {
            Column {
                Text(text = item.screenname ?: "")
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    private fun getClickable() : Modifier
    {
        return Modifier.combinedClickable(onClick = {
            println("clicked !")
        })
    }
}
