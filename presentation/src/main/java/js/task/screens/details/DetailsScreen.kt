package js.task.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import js.task.domain.usecase.model.DomainModel


class DetailsScreen
{
    @Composable
    fun Details(item : DomainModel)
    {
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
}