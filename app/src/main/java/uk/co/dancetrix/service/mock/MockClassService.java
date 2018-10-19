package uk.co.dancetrix.service.mock;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    public void getClassMenu(final Context ctx,
                             final Callback<ClassMenu, Exception> callback) {
        AsyncTask.execute(() -> {
            try {
                String csv = FileReader.readFile(ctx, R.raw.classes);

                callback.onSuccess(ClassMenuParser.parse(csv));
            } catch (Exception e) {
                Log.e("Classes", "Error reading class menu", e);

                callback.onError(e);
            }
        });
    }

    @Override
    public void getClassDates(final Context ctx,
                              final ClassDetails classDetails,
                              final Callback<List<DateInterval>, Exception> callback) {
        AsyncTask.execute(() -> {
            int rawId = ctx.getResources().getIdentifier(
                    FileReader.stripExtension(classDetails.getDatesLocation()),
                    "raw",
                    ctx.getPackageName());

            try {
                String csv = FileReader.readFile(ctx, rawId);

                callback.onSuccess(ClassDatesParser.parse(csv));
            } catch (Exception e) {
                Log.e("Classes", "Error reading class dates", e);

                callback.onError(e);
            }
        });
    }

    @Override
    public void getClassDescription(final Context ctx,
                                    final ClassDetails classDetails,
                                    final Callback<String, Exception> callback) {
            AsyncTask.execute(() -> {
                int rawId = ctx.getResources().getIdentifier(
                        FileReader.stripExtension(classDetails.getDescriptionLocation()),
                        "raw",
                        ctx.getPackageName());

                try {
                    String text = FileReader.readFile(ctx, rawId);

                    callback.onSuccess(text);
                } catch (
                        Exception e)

                {
                    Log.e("Classes", "Error reading class description", e);

                    callback.onError(e);
                }
            });
    }

}
