package js.task.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import js.task.application.R
import js.task.screens.main.list.ListFragmentDirections
import js.task.screens.main.list.model.PlaceholderItem


/**
 * TODO: Compose
 */
class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToDetails(item: PlaceholderItem)
    {
        val directions = ListFragmentDirections.navigateToProductUsersDetails(item)
        findNavController(this, R.id.nav_host_fragment_content_main).navigate(directions)
    }
}