package involves.test.com.popularmovies.listener;

/**
 * Interface to communicate the Broadcast receiver with the activity as a callback to pass the boolean
 * connection verification
 */
public interface ConnectivityReceiverListener {

    void onNetworkConnectionChanged(boolean isConnected);

}
