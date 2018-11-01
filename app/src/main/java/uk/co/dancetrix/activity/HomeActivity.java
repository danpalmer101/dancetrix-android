package uk.co.dancetrix.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.about.AboutUsActivity;
import uk.co.dancetrix.activity.booking.ClassMenuActivity;
import uk.co.dancetrix.activity.payment.PaymentActivity;
import uk.co.dancetrix.activity.registration.RegisterMenuActivity;
import uk.co.dancetrix.activity.uniform.UniformActivity;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.NetworkUtil;

public class HomeActivity extends BaseActivity {

    private static boolean firstRun = true;

    @Override
    protected int getMainId() {
        return R.id.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Fabric.with(this, new Crashlytics());

        removeView(R.id.registerDancerButton, !Configuration.registerDancerEnabled());
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

        if (firstRun) {
            firstRun = false;

            // Set toolbar to full height
            Toolbar toolbar = findViewById(R.id.toolbar);
            int toolbarHeight = toolbar.getLayoutParams().height;
            toolbar.getLayoutParams().height = findViewById(R.id.activity_home).getLayoutParams().height;

            // Animate it to original height
            new Handler().postDelayed(() -> {
                ValueAnimator va = ValueAnimator.ofInt(toolbar.getHeight(), toolbarHeight);
                va.setDuration(200);
                va.addUpdateListener(valueAnimator -> {
                            toolbar.getLayoutParams().height = (Integer) valueAnimator.getAnimatedValue();
                            toolbar.requestLayout();
                        }
                );
                va.start();
            }, 1000);
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

    public void displayRegisterDancer(View view) {
        startActivity(new Intent(this, RegisterMenuActivity.class));
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
