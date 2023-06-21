package js.task.screens.details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import js.task.application.R;


public class DetailsScreen
        extends AppCompatActivity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_details);

        if (navHostFragment != null)
        {
            navHostFragment.getNavController()
                    .setGraph(R.navigation.nav_graph_details, getIntent().getExtras());
        }
    }
}
