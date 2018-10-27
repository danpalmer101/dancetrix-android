package uk.co.dancetrix.service.mock;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Map;

import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.EmailService;

public class MockEmailService implements EmailService {

    @Override
    public void sendEmail(final Context ctx,
                          final String templateName,
                          final String from,
                          final String to,
                          final Map<String, Object> templateParameters,
                          final Callback<Boolean, Exception> callback) {
        AsyncTask.execute(() -> callback.onSuccess(true));
    }

}
