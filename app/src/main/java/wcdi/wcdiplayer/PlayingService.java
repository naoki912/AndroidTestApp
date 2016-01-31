package wcdi.wcdiplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlayingService extends Service {
    public PlayingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate(){

    }

    public void onDestory(){

    }
}
