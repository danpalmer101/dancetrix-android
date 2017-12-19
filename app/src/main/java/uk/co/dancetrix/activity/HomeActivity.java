package uk.co.dancetrix.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import uk.co.dancetrix.R;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.Notification;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Notification.showNotificationFromIntent(this, R.id.activity_home);
    }

    public void displayBookings(View view) {
        // TODO
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
