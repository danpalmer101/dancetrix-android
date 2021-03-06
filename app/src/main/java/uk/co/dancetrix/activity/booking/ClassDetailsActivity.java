package uk.co.dancetrix.activity.booking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.noties.markwon.Markwon;
import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.BaseActivity;
import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Notification;

public class ClassDetailsActivity extends BaseActivity {

    public static final String INTENT_KEY_CLASS_DETAILS = "class_details";

    private ClassDetails classDetails;

    private ListView listView;
    private ArrayAdapter<DateInterval> adapter;

    @Override
    protected int getMainId() {
        return R.id.activity_class_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        listView = findViewById(R.id.classDatesList);

        classDetails = (ClassDetails) getIntent().getSerializableExtra(INTENT_KEY_CLASS_DETAILS);

        if (classDetails == null) {
            Notification.showNotification(
                    this,
                    getMainId(),
                    R.string.booking_class_dates_error,
                    Notification.WARNING_BG_COLOR,
                    Notification.WARNING_TXT_COLOR);
        } else {
            final Activity current = this;

            ServiceLocator.CLASS_SERVICE.getClassDates(this, classDetails, new Callback<List<DateInterval>, Exception>() {
                @Override
                public void onSuccess(final List<DateInterval> response) {
                    current.runOnUiThread(() -> {
                        int layout, choiceMode;
                        if (classDetails.isAllowIndividualBookings()) {
                            layout = android.R.layout.simple_list_item_multiple_choice;
                            choiceMode = ListView.CHOICE_MODE_MULTIPLE;
                        } else {
                            layout = android.R.layout.simple_list_item_1;
                            choiceMode = ListView.CHOICE_MODE_NONE;
                        }

                        adapter = new ArrayAdapter<>(current, layout, response);
                        listView.setChoiceMode(choiceMode);
                        listView.setAdapter(adapter);
                    });
                }

                @Override
                public void onError(Exception exception) {
                    Notification.showNotification(
                            current,
                            getMainId(),
                            R.string.booking_class_dates_error,
                            Notification.WARNING_BG_COLOR,
                            Notification.WARNING_TXT_COLOR);
                }
            });

            ServiceLocator.CLASS_SERVICE.getClassDescription(this, classDetails, new Callback<String, Exception>() {
                @Override
                public void onSuccess(final String response) {
                    current.runOnUiThread(() -> {
                        TextView textView = findViewById(R.id.classInfoView);
                        Markwon.setMarkdown(textView, response);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    });
                }

                @Override
                public void onError(Exception exception) {
                    // Do nothing
                }
            });
        }
    }

    public void bookDates(View view) {
        List<DateInterval> selectedDates = new ArrayList<>();

        if (classDetails.isAllowIndividualBookings()) {
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            for (int i = 0; i < checked.size(); i++) {
                int pos = checked.keyAt(i);

                selectedDates.add(adapter.getItem(pos));
            }
        } else {
            for (int i = 0; i < adapter.getCount(); i++) {
                selectedDates.add(adapter.getItem(i));
            }
        }

        if (selectedDates.size() > 0) {
            Intent intent = new Intent(this, ClassBookingActivity.class);
            intent.putExtra(ClassBookingActivity.INTENT_KEY_CLASS_DETAILS, this.classDetails);
            intent.putExtra(ClassBookingActivity.INTENT_KEY_CLASS_DATES, (Serializable) selectedDates);
            this.startActivity(intent);
        }
    }

}
