package involves.test.com.popularmovies.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import involves.test.com.popularmovies.listener.ConnectivityReceiverListener;
import involves.test.com.popularmovies.utils.ConnectionDetector;

/**
 * BroadcastReceiver to verify the network connection status
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String TAG = NetworkChangeReceiver.class.getSimpleName();

    public NetworkChangeReceiver(){
        super();
    }

    /**
     * Method that verifies if the network connection is connected or not
     *
     * @param context The context that verifies if the connection is fine
     * @param intent The intent which makes the broadcast work
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive() inside method");

        boolean isConnected = ConnectionDetector.isConnectingToInternet(context);

        ((ConnectivityReceiverListener)context).onNetworkConnectionChanged(isConnected);

    }

}
