package uk.co.dancetrix.activity.payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import ru.noties.markwon.Markwon;
import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.BaseActivity;
import uk.co.dancetrix.service.impl.FirebaseStorageService;
import uk.co.dancetrix.util.Notification;

public class PaymentActivity extends BaseActivity {

    @Override
    protected int getMainId() {
        return R.id.activity_payment_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        FirebaseStorageService markdownStorageService = new FirebaseStorageService() {
            @Override
            protected String getTag() {
                return "Payment";
            }
        };

        markdownStorageService.loadFile("text-payment.md",
                paymentText -> {
                    TextView tv = findViewById(R.id.paymentInfoView);
                    Markwon.setMarkdown(tv, paymentText);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                },
                e -> Notification.showNotification(this,
                            getMainId(),
                            R.string.unexpected_error,
                            Notification.ERROR_BG_COLOR,
                            Notification.ERROR_TXT_COLOR)
                );
    }

    public void displayPaymentLetUsKnow(View view) {
        startActivity(new Intent(this, PaymentFormActivity.class));
    }

}
