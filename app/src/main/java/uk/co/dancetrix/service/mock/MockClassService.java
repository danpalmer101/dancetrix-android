package uk.co.dancetrix.service.mock;

import android.content.Context;

import java.util.List;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ClassService;
import uk.co.dancetrix.util.ClassDatesParser;
import uk.co.dancetrix.util.ClassMenuParser;
import uk.co.dancetrix.util.FileReader;

public class MockClassService implements ClassService {

    @Override
    public void getClassMenu(Context ctx,
                             Callback<ClassMenu, Exception> callback) {
        try {
            String csv = FileReader.readFile(ctx, R.raw.classes);

            callback.onSuccess(ClassMenuParser.parse(csv));
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    @Override
    public void getClassDates(Context ctx,
                              ClassDetails classDetails,
                              Callback<List<DateInterval>, Exception> callback) {
        int rawId = ctx.getResources().getIdentifier(
                classDetails.getDatesLocation(),
                "raw",
                ctx.getPackageName());

        try {
            String csv = FileReader.readFile(ctx, rawId);

            callback.onSuccess(ClassDatesParser.parse(csv));
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    @Override
    public void getClassDescription(Context ctx,
                                    ClassDetails classDetails,
                                    Callback<String, Exception> callback) {
        int rawId = ctx.getResources().getIdentifier(
                classDetails.getDescriptionLocation(),
                "raw",
                ctx.getPackageName());

        try {
            String text = FileReader.readFile(ctx, rawId);

            callback.onSuccess(text);
        } catch (Exception e) {
            callback.onError(e);
        }
    }

}
