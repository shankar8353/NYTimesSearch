package com.codepath.nytimessearch.net;

import android.util.Log;

import java.io.IOException;

/**
 * Created by ssunda1 on 5/30/16.
 */
public class NetworkUtils {

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            Log.e("NETWORK", "Error checking if online", e);
        }
        return false;
    }
}
