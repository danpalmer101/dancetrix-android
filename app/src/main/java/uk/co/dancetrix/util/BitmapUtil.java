package uk.co.dancetrix.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class BitmapUtil {

    public static Bitmap paintOn(Bitmap src, int colour) {
        Bitmap imageWithBG = Bitmap.createBitmap(src.getWidth(), src.getHeight(),src.getConfig());  // Create another image the same size
        imageWithBG.eraseColor(colour);  // set its background to white, or whatever color you want
        Canvas canvas = new Canvas(imageWithBG);  // create a canvas to draw on the new image
        canvas.drawBitmap(src, 0f, 0f, null); // draw old image on the background
        src.recycle();  // clear out old image

        return imageWithBG;
    }

    public static Bitmap invert(Bitmap src) {
        int height = src.getHeight();
        int width = src.getWidth();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        ColorMatrix matrixGrayscale = new ColorMatrix();
        matrixGrayscale.setSaturation(0);

        ColorMatrix matrixInvert = new ColorMatrix();
        matrixInvert.set(new float[] {
                -1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
                0.0f, -1.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 0.0f, -1.0f, 1.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f
            });
        matrixInvert.preConcat(matrixGrayscale);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrixInvert);
        paint.setColorFilter(filter);

        canvas.drawBitmap(src, 0, 0, paint);
        return bitmap;
    }

}
