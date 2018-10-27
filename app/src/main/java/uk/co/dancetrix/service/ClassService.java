package uk.co.dancetrix.service;

import android.content.Context;

import java.util.List;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.domain.DateInterval;

public interface ClassService {

    void getClassMenu(Context ctx,
                      Callback<ClassMenu, Exception> callback);

    void getClassDates(Context ctx,
                       ClassDetails classDetails,
                       Callback<List<DateInterval>, Exception> callback);

    void getClassDescription(Context ctx,
                             ClassDetails classDetails,
                             Callback<String, Exception> callback);

}
