package uk.co.dancetrix.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Notification;

public class ClassDetailsActivity extends BaseActivity {

    public static final String INTENT_KEY_CLASS_DETAILS = "class_details";

    ListView listView;
    ArrayAdapter<DateInterval> adapter;

    @Override
    protected int getMainId() {
        return R.id.activity_class_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        listView = findViewById(R.id.classDatesList);

        final ClassDetails classDetails = (ClassDetails) getIntent().getSerializableExtra(INTENT_KEY_CLASS_DETAILS);

        if (classDetails == null) {
            Notification.showNotification(
                    this,
                    getMainId(),
                    R.string.class_menu_error,
                    Notification.WARNING_BG_COLOR,
                    Notification.WARNING_TXT_COLOR);
        } else {
            final Activity current = this;

            ServiceLocator.CLASS_SERVICE.getClassDates(this, classDetails, new Callback<List<DateInterval>, Exception>() {
                @Override
                public void onSuccess(List<DateInterval> response) {
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
                }

                @Override
                public void onError(Exception exception) {
                    Notification.showNotification(
                            current,
                            getMainId(),
                            R.string.class_menu_error,
                            Notification.WARNING_BG_COLOR,
                            Notification.WARNING_TXT_COLOR);
                }
            });
        }
    }

}
