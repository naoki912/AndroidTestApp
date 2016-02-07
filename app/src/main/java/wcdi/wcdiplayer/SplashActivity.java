package wcdi.wcdiplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);

        Handler handler = new Handler();

        handler.postDelayed(new spashHandler(), 500);
    }

    class spashHandler implements Runnable {

        @Override
        public void run() {

            Intent intent = new Intent(getApplication(), MainActivity.class);

            startActivity(intent);

            SplashActivity.this.finish();
        }
    }

}
