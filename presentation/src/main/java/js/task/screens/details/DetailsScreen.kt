package js.task.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import js.task.domain.usecase.model.DomainModel


class DetailsScreen
{
    @Composable
    fun Details(item: DomainModel)
    {
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
        }
    }
}