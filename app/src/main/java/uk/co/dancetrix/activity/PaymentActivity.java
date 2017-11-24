package uk.co.dancetrix.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import uk.co.dancetrix.R;
import uk.co.dancetrix.util.Configuration;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView tv = findViewById(R.id.paymentInfoView);
        tv.setText(Html.fromHtml(getString(R.string.payment_info_html)));
        tv.setMovementMethod(new ScrollingMovementMethod());
    }

    public void displayPaymentLetUsKnow(View view) {
        // TODO
    }

}
