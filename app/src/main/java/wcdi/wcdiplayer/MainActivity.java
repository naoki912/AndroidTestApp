package wcdi.wcdiplayer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DirectoryFragment.OnFileClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                if (id == R.id.nav_camera) {
                } else if (id == R.id.nav_gallery) {
                } else if (id == R.id.nav_slideshow) {
                } else if (id == R.id.nav_manage) {
                } else if (id == R.id.nav_share) {
                } else if (id == R.id.nav_send) {
                } else if (id == R.id.nav_directory) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, DirectoryFragment.newInstance(new File("/")))
                            .addToBackStack(null)
                            .commit();
                } else if (id == R.id.nav_cloud) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, CloudFragment.newInstance(""))
                            .addToBackStack(null)
                            .commit();
                } else if (id == R.id.nav_player) {
                    getFragmentManager()
                            .beginTransaction()
//                            .replace(R.id.fragment, PlayingFragment.newInstance(new ArrayList<String>(), 0))
                            .replace(R.id.fragment, PlayingFragment.getInstance())
                            .addToBackStack(null)
                            .commit();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() != 0) {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDirectoryClick(File path) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, DirectoryFragment.newInstance(path))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFileClick(ArrayList<String> mediaPathList, int position) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, PlayingFragment.newInstance(mediaPathList, position))
                .addToBackStack(null)
                .commit();
    }

}
