package uk.co.dancetrix.service.mock;

import android.content.Context;
import android.os.AsyncTask;

import uk.co.dancetrix.domain.RegistrationBase;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.RegistrationService;

public class MockRegistrationService implements RegistrationService {

    @Override
    public void register(final Context ctx,
                         final RegistrationBase registration,
                         final Callback<Boolean, Exception> callback) {
        AsyncTask.execute(() -> callback.onSuccess(true));
    }

}
