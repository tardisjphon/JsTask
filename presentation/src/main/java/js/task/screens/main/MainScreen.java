package js.task.screens.main;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import js.task.application.R;
import js.task.screens.main.list.MainFragmentDirections;
import js.task.screens.main.list.MainFragmentDirections.NavigateToProductUsersDetails;
import js.task.screens.main.list.model.PlaceholderItem;



public class MainScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToDetails(PlaceholderItem item)
    {
        NavigateToProductUsersDetails directions = MainFragmentDirections.navigateToProductUsersDetails(item);
        findNavController(this, R.id.nav_host_fragment_content_main).navigate(directions);
    }
}
