package uk.co.dancetrix.activity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.dancetrix.util.NetworkChangeReceiver;
import uk.co.dancetrix.util.Notification;

public abstract class BaseActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.broadcastReceiver = new NetworkChangeReceiver();

        this.registerReceiver(this.broadcastReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Notification.showNotificationFromIntent(this, getMainId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.unregisterReceiver(this.broadcastReceiver);
    }

    protected abstract int getMainId();

}
