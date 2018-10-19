package uk.co.dancetrix.service;

import java.util.List;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.DateInterval;

public interface BookingService {

    void bookClass(ClassDetails classDetails,
                   List<DateInterval> dates,
                   String name,
                   String email,
                   Callback<Boolean, Exception> callback);

}
