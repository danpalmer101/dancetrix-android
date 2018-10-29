package uk.co.dancetrix.service.impl;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.BookingService;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.StringFormatter;

public class EmailBookingService implements BookingService {

    @Override
    public void bookClass(final Context ctx,
                          final ClassDetails classDetails,
                          final List<DateInterval> dates,
                          final String name,
                          final String email,
                          final Callback<Boolean, Exception> callback) {
        Log.d("Booking", "Sending class booking email");

        ArrayList<String> dateStrings = new ArrayList<>();
        for (DateInterval date : dates) {
            dateStrings.add(StringFormatter.formatDateTime(date.getStart()));
        }

        ServiceLocator.EMAIL_SERVICE.sendEmail(
                ctx,
                "class_booking",
                Configuration.fromBookingEmailAddress(),
                Configuration.toEmailAddress(),
                Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("class", classDetails.getName());
                        put("classMenu", classDetails.getPath());
                        put("dates", dateStrings);
                        put("name", name);
                        put("email", email);
                    }
                }),
                new Callback<Boolean, Exception>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        Log.d("Booking", "Class booking sent");
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.w("Booking", "Class booking not sent", exception);
                        callback.onError(exception);
                    }
                });
    }

}
