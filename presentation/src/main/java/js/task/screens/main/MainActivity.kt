package js.task.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import js.task.viewmodel.DataViewModel


/**
 * TODO: Compose
 */
class MainActivity : AppCompatActivity()
{

    private val viewModel : DataViewModel by viewModels { DataViewModel.Factory }

    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.getData()
    }

    private fun setObservers()
    {
        viewModel.dataList.observe(this) { data ->
            setContent {
                MaterialTheme {
                    MainScreen().SetRecyclerView(data)
                }
            }
        }
    }
}