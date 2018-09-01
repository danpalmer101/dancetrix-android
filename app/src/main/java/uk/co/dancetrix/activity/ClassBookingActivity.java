package uk.co.dancetrix.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormButton;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormHeader;
import com.dariopellegrini.formbuilder.FormObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.util.Notification;

public class ClassBookingActivity extends AbstractFormActivity {

    public static final String INTENT_KEY_CLASS_DETAILS = "class_details";
    public static final String INTENT_KEY_CLASS_DATES = "class_dates";

    @Override
    protected int getMainId() {
        return R.id.activity_payment_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_booking);

        ClassDetails classDetails = (ClassDetails)getIntent().getSerializableExtra(INTENT_KEY_CLASS_DETAILS);
        List<DateInterval> selectedDates = (List<DateInterval>)getIntent().getSerializableExtra(INTENT_KEY_CLASS_DATES);
        List<String> selectedDateStrings = new ArrayList<>();
        for (DateInterval date : selectedDates) {
            selectedDateStrings.add(date.asText());
        }

        LinearLayout formLayout = findViewById(R.id.paymentFormContainer);

        final Activity current = this;

        this.formBuilder = new FormBuilder(this, formLayout);

        List<FormObject> formObjects = new ArrayList<>();

        formObjects.add(new FormHeader().setTitle("Class"));

        formObjects.add(new FormElement()
                .setTag("class_name")
                .setHint("Name")
                .setType(FormElement.Type.TEXT)
                .setValue(classDetails.getName())
                .setEnabled(false)
        );

        formObjects.add(new FormElement()
                .setTag("class_dates")
                .setHint("Dates")
                .setType(FormElement.Type.TEXT)
                .setValue(TextUtils.join(", ", selectedDateStrings))
                .setEnabled(false)
        );

        formObjects.add(new FormHeader().setTitle("Your details"));

        formObjects.add(new FormElement()
                .setTag("student_name")
                .setHint("Student's name")
                .setType(FormElement.Type.TEXT)
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("email")
                .setHint("Email address")
                .setType(FormElement.Type.EMAIL)
                .setRequired(true)
                .setErrorMessage("Required")
        );

        formObjects.add(new FormButton()
                .setTitle(getString(R.string.booking_class_submit))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTextColor(Color.WHITE)
                .setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        clearAllErrors();

                        boolean isValid = formBuilder.validate();

                        if (isValid) {
                            // TODO - call service
                            current.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(current, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    Notification.setNotificationInIntent(
                                            intent,
                                            R.string.booking_class_success,
                                            Notification.SUCCESS_BG_COLOR,
                                            Notification.SUCCESS_TXT_COLOR);
                                    current.startActivity(intent);
                                }
                            });
                        }
                    }
                })
        );

        this.formBuilder.build(formObjects);

        fixFormBuilderViewMap(formLayout);
    }

}
