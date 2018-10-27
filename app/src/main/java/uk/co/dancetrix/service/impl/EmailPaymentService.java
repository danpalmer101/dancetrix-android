package uk.co.dancetrix.service.impl;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.PaymentService;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.StringFormatter;

public class EmailPaymentService implements PaymentService {

    @Override
    public void notify(final Context ctx,
                       final Date date,
                       final Double amount,
                       final String name,
                       final String studentName,
                       final String email,
                       final String method,
                       final String reason,
                       final String additionalInfo,
                       final Callback<Boolean, Exception> callback) {
        Log.d("Payment", "Sending payment notification email");

        ServiceLocator.EMAIL_SERVICE.sendEmail(ctx,
                "payment_notify",
                Configuration.fromPaymentEmailAddress(),
                Configuration.toEmailAddress(),
                Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("date", StringFormatter.formatDate(date));
                        put("amount", StringFormatter.formatCurrency(amount));
                        put("name", name);
                        put("studentName", studentName);
                        put("email", email);
                        put("method", method);
                        put("reason", reason);
                        put("additionalInfo", additionalInfo);
                    }
                }),
                new Callback<Boolean, Exception>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        Log.d("Payment", "Payment notification sent");
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.w("Payment", "Payment notification not sent", exception);
                        callback.onError(exception);
                    }
                });
    }

}
