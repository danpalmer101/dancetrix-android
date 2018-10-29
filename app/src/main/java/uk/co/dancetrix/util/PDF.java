package uk.co.dancetrix.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import uk.co.dancetrix.service.Callback;

public class PDF {

    public static void open(final Context context,
                            final Uri uri,
                            final Callback<Boolean, Exception> callback) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            try {
                context.startActivity(intent);
                callback.onSuccess(true);
            } catch (ActivityNotFoundException ex) {
                callback.onError(ex);
            }
        } else {
            callback.onError(new UnsupportedOperationException("Unable to find activity for viewing PDF"));
        }
    }

}
