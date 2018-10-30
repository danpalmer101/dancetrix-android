package uk.co.dancetrix.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.dancetrix.R;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_reverse, R.anim.slide_out_reverse);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_CLEAR_TOP) != 0) {
            // If we are clearing the activity stack, then slide in reverse (left to right)
            overridePendingTransition(R.anim.slide_in_reverse, R.anim.slide_out_reverse);
        } else {
            // Otherwise use a slide animation (right to left)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    protected abstract int getMainId();

}
