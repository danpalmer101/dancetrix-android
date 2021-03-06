package uk.co.dancetrix.service.mock;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.BookingService;
import uk.co.dancetrix.service.Callback;

public class MockBookingService implements BookingService {

    @Override
    public void bookClass(final Context ctx,
                          final ClassDetails classDetails,
                          final List<DateInterval> dates,
                          final String name,
                          final String email,
                          final Callback<Boolean, Exception> callback) {
        AsyncTask.execute(() -> callback.onSuccess(true));
    }

}
