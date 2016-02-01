package wcdi.wcdiplayer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements DirectoryFragment.OnFileClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Fragment albumFragment = DirectoryFragment.newInstance(new File("/"));
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, albumFragment)
                .commit();

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
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
