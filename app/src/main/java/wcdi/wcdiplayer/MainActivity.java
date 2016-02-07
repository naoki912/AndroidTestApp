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
import android.view.View;

import java.util.ArrayList;

import wcdi.wcdiplayer.Items.SongObject;


public class MainActivity extends AppCompatActivity
        implements SongFragment.OnSongClickListener, PlayingFragment.OnPlayingFragmentListener {

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

                if (id == R.id.nav_player) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, PlayingFragment.getInstance())
                            .addToBackStack(null)
                            .commit();
                } else if (id == R.id.nav_cloud) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, CloudFragment.newInstance(""))
                            .addToBackStack(null)
                            .commit();
                } else if (id == R.id.nav_album) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, AlbumFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                } else if (id == R.id.nav_Artists) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, ArtistFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // 画面下のコントローラをタッチしたときのリスナを設定
        findViewById(R.id.controls_container)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment, PlayingFragment.getInstance())
                                .addToBackStack(null)
                                .commit();
                    }
                });

        // デフォルト画面
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, AlbumFragment.newInstance())
                .commit();

        // hideしているPlayingUnderControlsFragmentが殺された場合を考えるとこの実装方法はマズイかも
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.controls_container, PlayingUnderControlsFragment.getInstance())
                .hide(PlayingUnderControlsFragment.getInstance())
                .commit();
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
    public void onSongClick(ArrayList<SongObject> mSongObjectList, int position) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, PlayingFragment.newInstance(mSongObjectList, position))
//                .replace(R.id.fragment, PlayingStub.newInstance(mSongObjectList, position))
                .addToBackStack(null)
                .commit();

        getFragmentManager()
                .beginTransaction()
                .show(PlayingUnderControlsFragment.getInstance())
                .commit();
    }

    @Override
    public void onChangeSong(SongObject songObject) {

        PlayingUnderControlsFragment.getInstance().setSong(songObject);

    }
}
