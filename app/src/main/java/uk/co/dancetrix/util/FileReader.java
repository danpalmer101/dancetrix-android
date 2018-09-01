package uk.co.dancetrix.util;

import android.content.Context;
import android.content.res.Resources;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class FileReader {

    public static String stripExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        if (extensionIndex > 0) {
            return fileName.substring(0, extensionIndex);
        } else {
            return fileName;
        }
    }

    public static String readFile(Context ctx, int resource) throws Exception {
        Resources res = ctx.getResources();
        InputStream is = res.openRawResource(resource);

        String s = IOUtils.toString(is);
        IOUtils.closeQuietly(is);

        return s;
    }

}
