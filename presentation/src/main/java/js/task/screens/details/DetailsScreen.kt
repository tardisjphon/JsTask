package js.task.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


class DetailsScreen
{
    @Composable
    fun Details(id: Int?)
    {
        Column {
            Row {
                Text(text = "$id")
            }
        }
    }
}