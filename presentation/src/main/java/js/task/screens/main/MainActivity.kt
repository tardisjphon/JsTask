package js.task.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import js.task.viewmodel.DataViewModel


class MainActivity : AppCompatActivity()
{
    private val viewModel : DataViewModel by viewModels { DataViewModel.Factory }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen().setRecyclerView(viewModel)
        }
    }
}