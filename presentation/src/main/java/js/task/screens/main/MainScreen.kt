package js.task.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import js.task.data.local.db.model.DailyMotion
import js.task.domain.usecase.model.DomainModel
import js.task.viewmodel.DataViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow



class MainScreen
{
    private val listDailyMotion = listOf(DailyMotion("0", "main"),
            DailyMotion("1", "other"))


    class ExampleState {
        val counter = flow {
            val list = ArrayList<DomainModel>()
            emit(list)
        }
    }

    @Composable
    fun test()
    {
        val state = ExampleState() // remember { ExampleState() }
        val count by state.counter.collectAsStateWithLifecycle(initialValue = 0)
    }



    @Composable
    fun setRecyclerView(viewModel: DataViewModel) {

       //https://developer.android.com/reference/kotlin/androidx/lifecycle/compose/package-summary#extension-functions
       //https://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3
       // val state by viewModel.dataList.collectAsStateWithLifecycle()

        val state = flow {
            viewModel.getData()
            emit(viewModel.dataList) }

        val dataList by state.collectAsStateWithLifecycle(initialValue = 0)


        LazyColumn {
            items(viewModel.dataList) {
                item ->
                ItemHolder(item)
            }
        }
//
//        LaunchedEffect(Unit) {
//            viewModel.getData()
//        }
    }



    @Composable
    fun ItemHolder(item: DomainModel) {
        Row(modifier = getClickable()) {
            Column {
                Text(text = item.userName ?: "")
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
