package uk.co.dancetrix.util;

import android.content.Context;

public class Display {

    public static final int convertDpToPixels(Context ctx, int dp) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
