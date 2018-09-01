package uk.co.dancetrix.service.mock;

import android.os.AsyncTask;

import java.util.Date;

import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.PaymentService;

public class MockPaymentService implements PaymentService {

    @Override
    public void notify(Date date,
                       Double amount,
                       String name,
                       String studentName,
                       String email,
                       String method,
                       String reason,
                       String additionalInfo,
                       final Callback<Boolean, Exception> callback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(true);
            }
        });
    }

}
