package js.task.screens.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import js.task.application.R


class DetailsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_details) as NavHostFragment
        val navController = navHostFragment.navController

        navController.setGraph(
                R.navigation.nav_graph_details,
                intent.extras
        )
    }
}