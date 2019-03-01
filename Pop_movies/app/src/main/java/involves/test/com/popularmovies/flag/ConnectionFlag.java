package involves.test.com.popularmovies.flag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

/**
 * This class is to control the message in the SnackBar class whether the app is online or offline
 * so that the message "Back online" from string file does't show up when the app is executed, it will
 * be shown only when the network connection is restored
 */
public class ConnectionFlag {

    private static final String TAG = ConnectionFlag.class.getSimpleName();

    // Shared Preferences
    public SharedPreferences pref;

    // Editor for Shared preferences
    public SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PopMovies";

    // All Shared Preferences Key
    public static final String ONLINE = "isOnline";

    // Constructor
    public ConnectionFlag(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        putValue(false);
    }

    /**
     * Add boolean value
     * */
    public void putValue(boolean online){

        // Storing online value as TRUE
        editor.putBoolean(ONLINE, online);

        // commit changes
        editor.commit();
    }

    /**
     * Quick check for connection
     */
    public boolean isOnline(){
        return pref.getBoolean(ONLINE, false);
    }
}
