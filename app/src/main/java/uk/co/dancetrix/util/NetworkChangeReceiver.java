package uk.co.dancetrix.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.d("Network", String.format("Network status changed: %s", status));

        if (NetworkUtil.getConnectivityStatus(context) == NetworkUtil.TYPE_NOT_CONNECTED) {
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }
    }

}
