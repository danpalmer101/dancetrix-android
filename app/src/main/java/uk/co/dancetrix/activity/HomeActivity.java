package uk.co.dancetrix.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.NetworkUtil;
import uk.co.dancetrix.util.Notification;
import uk.co.dancetrix.util.PDF;

import static uk.co.dancetrix.util.Configuration.getUniformCatalog;

public class HomeActivity extends BaseActivity {

    @Override
    protected int getMainId() {
        return R.id.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Fabric.with(this, new Crashlytics());

        removeView(R.id.bookClassButton, !Configuration.bookClassEnabled());
        //removeView(R.id.calendarButton, !Configuration.calendarEnabled());
        removeView(R.id.calendarButton, true);
        removeView(R.id.makePaymentButton, !Configuration.paymentEnabled());
        removeView(R.id.orderUniformButton, !Configuration.uniformEnabled());
        removeView(R.id.aboutUsButton, !Configuration.aboutEnabled());

        // Check network status in case network is unavailable when the app starts
        if (NetworkUtil.getConnectivityStatus(this) == NetworkUtil.TYPE_NOT_CONNECTED) {
            Toast.makeText(this, NetworkUtil.getConnectivityStatusString(this), Toast.LENGTH_LONG).show();
        }
    }

    private void removeView(int id, boolean ifFlag) {
        if (ifFlag) {
            View view = findViewById(id);
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent != null && parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(view);
                }
            }
        }
    }

    public void displayBookings(View view) {
        startActivity(new Intent(this, ClassMenuActivity.class));
    }

    public void displayCalendar(View view) {
        // TODO
    }

    public void displayPayment(View view) {
        startActivity(new Intent(this, PaymentActivity.class));
    }

    public void displayUniforms(View view) {
        startActivity(new Intent(this, UniformActivity.class));
    }

    public void displayAboutUs(View view) {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    public void visitWebsite(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(Configuration.getWebsiteUrl()));
        startActivity(i);
    }

}
