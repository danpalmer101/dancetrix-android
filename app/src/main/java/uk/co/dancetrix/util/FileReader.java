package uk.co.dancetrix.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class FileReader {

    public static String readFile(Context ctx, int resource) throws Exception {
        Resources res = ctx.getResources();
        InputStream is = res.openRawResource(resource);

        String s = IOUtils.toString(is);
        IOUtils.closeQuietly(is);

        return s;
    }

}
