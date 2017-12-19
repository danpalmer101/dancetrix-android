package uk.co.dancetrix.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import uk.co.dancetrix.R;

public class Notification {

    private static final String INTENT_KEY_TEXT_ID = "snackbar_text_id";
    private static final String INTENT_KEY_BG_COLOR = "snackbar_bg_color";
    private static final String INTENT_KEY_TXT_COLOR = "snackbar_txt_color";

    public static final int SUCCESS_BG_COLOR = Color.parseColor("#00C060");
    public static final int SUCCESS_TXT_COLOR = Color.WHITE;
    public static final int WARNING_BG_COLOR = Color.parseColor("#FFCC00");
    public static final int WARNING_TXT_COLOR = Color.BLACK;
    public static final int ERROR_BG_COLOR = Color.parseColor("#FF2D55");
    public static final int ERROR_TXT_COLOR = Color.WHITE;

    public static void showNotification(Activity activity, int viewId, int textId, int bgColor, int txtColor) {
        if (textId > Integer.MIN_VALUE) {
            Snackbar snackbar = Snackbar.make(activity.findViewById(viewId), textId, Snackbar.LENGTH_LONG);

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(bgColor);

            int snackbarTextViewId = android.support.design.R.id.snackbar_text;
            TextView textView = snackbarView.findViewById(snackbarTextViewId);
            textView.setTextColor(txtColor);

            snackbar.show();
        }
    }

    public static void showNotificationFromIntent(Activity activity, int viewId) {
        int snackbarTextId = activity.getIntent().getIntExtra(
                Notification.INTENT_KEY_TEXT_ID, Integer.MIN_VALUE);

        int bgColor = activity.getIntent().getIntExtra(
                Notification.INTENT_KEY_BG_COLOR, activity.getResources().getColor(R.color.colorAccent));
        int txtColor = activity.getIntent().getIntExtra(
                Notification.INTENT_KEY_TXT_COLOR, Color.WHITE);

        Notification.showNotification(activity, viewId, snackbarTextId, bgColor, txtColor);
    }

    public static void setNotificationInIntent(Intent intent, int textId, int bgColor, int txtColor) {
        intent.putExtra(Notification.INTENT_KEY_TEXT_ID, textId);
        intent.putExtra(Notification.INTENT_KEY_BG_COLOR, bgColor);
        intent.putExtra(Notification.INTENT_KEY_TXT_COLOR, txtColor);
    }

}
