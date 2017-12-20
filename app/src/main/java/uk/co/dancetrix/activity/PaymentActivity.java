package uk.co.dancetrix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import uk.co.dancetrix.R;

public class PaymentActivity extends BaseActivity {

    @Override
    protected int getMainId() {
        return R.id.activity_payment_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView tv = findViewById(R.id.paymentInfoView);
        tv.setText(Html.fromHtml(getString(R.string.payment_info_html)));
        tv.setMovementMethod(new ScrollingMovementMethod());
    }

    public void displayPaymentLetUsKnow(View view) {
        startActivity(new Intent(this, PaymentFormActivity.class));
    }

}
