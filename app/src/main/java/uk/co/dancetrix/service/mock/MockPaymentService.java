package uk.co.dancetrix.service.mock;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Date;

import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.PaymentService;

public class MockPaymentService implements PaymentService {

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
        AsyncTask.execute(() -> callback.onSuccess(true));
    }

}
