package uk.co.dancetrix.service.impl;

import android.content.Context;

import java.util.List;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ClassService;
import uk.co.dancetrix.util.ClassDatesParser;
import uk.co.dancetrix.util.ClassMenuParser;

public class FirebaseClassService extends FirebaseStorageService implements ClassService {

    @Override
    protected String getTag() {
        return "Classes";
    }

    @Override
    public void getClassMenu(final Context ctx,
                             final Callback<ClassMenu, Exception> callback) {
        super.loadFile(
                "classes.csv",
                csvString -> {
                    final ClassMenu classMenu = ClassMenuParser.parse(csvString);
                    callback.onSuccess(classMenu);
                },
                callback::onError);
    }

    @Override
    public void getClassDates(final Context ctx,
                              final ClassDetails classDetails,
                              final Callback<List<DateInterval>, Exception> callback) {
        super.loadFile(
                classDetails.getDatesLocation(),
                csvString -> {
                    final List<DateInterval> classDates = ClassDatesParser.parse(csvString);
                    callback.onSuccess(classDates);
                },
                callback::onError);
    }

    @Override
    public void getClassDescription(final Context ctx,
                                    final ClassDetails classDetails,
                                    final Callback<String, Exception> callback) {
        super.loadFile(
                classDetails.getDescriptionLocation(),
                callback::onSuccess,
                callback::onError);
    }

}
