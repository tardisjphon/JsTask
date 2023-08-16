package js.task.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import js.task.domain.usecase.model.DomainModel
import js.task.viewmodel.MainViewModel
import org.koin.compose.koinInject


class DetailsScreen
{
    @Composable
    fun Details(viewModel : MainViewModel = koinInject(), id : Int?, api : String?)
    {
        val item = getItem(
                viewModel,
                id,
                api
        ) ?: return

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
        }
    }

    @Composable
    private fun getItem(viewModel : MainViewModel, id : Int?, api : String?) : DomainModel?
    {
        if (id == null || api == null) return null

        LaunchedEffect(Unit) {
            viewModel.getData()
        }

        val dataList = viewModel.dataList.collectAsStateWithLifecycle(emptyList()).value
        if (dataList.isEmpty()) return null

        return dataList.firstOrNull { it.id == id && it.apiName?.name == api }
    }
}