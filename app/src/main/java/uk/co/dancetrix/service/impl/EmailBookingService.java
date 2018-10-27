package uk.co.dancetrix.service.impl;

import android.content.Context;

import java.util.List;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.BookingService;
import uk.co.dancetrix.service.Callback;

public class EmailBookingService implements BookingService {

    @Override
    public void bookClass(final Context ctx,
                          final ClassDetails classDetails,
                          final List<DateInterval> dates,
                          final String name,
                          final String email,
                          final Callback<Boolean, Exception> callback) {
        // TODO : Implement
        callback.onError(new UnsupportedOperationException("Not implemented"));
    }

}
