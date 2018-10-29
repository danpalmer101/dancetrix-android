package uk.co.dancetrix.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Configuration;
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
        final Activity current = this;

        ServiceLocator.CLASS_SERVICE.getClassMenu(this, new Callback<ClassMenu, Exception>() {
            @Override
            public void onSuccess(final ClassMenu response) {
                current.runOnUiThread(() -> {
                    Intent intent = new Intent(current, ClassMenuActivity.class);
                    intent.putExtra(ClassMenuActivity.INTENT_KEY_CLASS_MENU, response);
                    current.startActivity(intent);
                });
            }

            @Override
            public void onError(Exception exception) {
                Log.w("Classes", "Error loading class menu", exception);

                Notification.showNotification(
                        current,
                        getMainId(),
                        R.string.booking_class_menu_error,
                        Notification.WARNING_BG_COLOR,
                        Notification.WARNING_TXT_COLOR);
            }
        });
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
