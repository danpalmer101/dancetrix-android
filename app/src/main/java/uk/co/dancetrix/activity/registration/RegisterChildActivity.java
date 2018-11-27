package uk.co.dancetrix.activity.registration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormButton;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormHeader;
import com.dariopellegrini.formbuilder.FormObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.AbstractFormActivity;
import uk.co.dancetrix.domain.RegistrationChild;
import uk.co.dancetrix.util.Notification;

public class RegisterChildActivity extends AbstractFormActivity {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected int getMainId() {
        return R.id.activity_register_child;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_child);

        buildForm();
    }

    private void buildForm() {
        LinearLayout formLayout = findViewById(R.id.registerFormContainer);
        this.formBuilder = new FormBuilder(this, formLayout);

        List<FormObject> formObjects = new ArrayList<>();

        formObjects.add(new FormHeader().setTitle("Student details"));
        formObjects.add(new FormElement()
                .setTag("student_name")
                .setHint("Name")
                .setType(FormElement.Type.TEXT)
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("date_of_birth")
                .setHint("Date of birth")
                .setType(FormElement.Type.DATE)
                .setDateFormat(DATE_FORMAT.toLocalizedPattern())
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("date_joined")
                .setHint("Date joined Dance Trix")
                .setType(FormElement.Type.DATE)
                .setDateFormat(DATE_FORMAT.toLocalizedPattern())
                .setValue(DATE_FORMAT.format(new Date()))
                .setRequired(true)
                .setErrorMessage("Required")
        );

        formObjects.add(new FormHeader().setTitle("Medical conditions/injuries"));
        formObjects.add(new FormElement()
                .setTag("medical")
                .setType(FormElement.Type.TEXTVIEW)
        );
        formObjects.add(new FormElement()
                .setTag("medical_note")
                .setType(FormElement.Type.TEXTVIEW)
                .setEnabled(false)
                .setValue(getResources().getString(R.string.register_medical_note))
        );

        formObjects.add(new FormHeader().setTitle("Previous dance experience"));
        formObjects.add(new FormElement()
                .setTag("experience")
                .setType(FormElement.Type.TEXTVIEW)
        );

        formObjects.add(new FormHeader().setTitle("Parent/Guardian details"));
        formObjects.add(new FormElement()
                .setTag("name")
                .setHint("Name")
                .setType(FormElement.Type.TEXT)
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("phone")
                .setHint("Phone number")
                .setType(FormElement.Type.PHONE)
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

        formObjects.add(new FormHeader().setTitle("Address"));
        formObjects.add(new FormElement()
                .setTag("address")
                .setType(FormElement.Type.TEXTVIEW)
                .setRequired(true)
                .setErrorMessage("Required")
        );

        formObjects.add(new FormHeader().setTitle("How did you hear about us?"));
        formObjects.add(new FormElement()
                .setTag("hear_about")
                .setType(FormElement.Type.TEXTVIEW)
        );

        formObjects.add(new FormHeader().setTitle("Contact"));
        FormElement packageName = new SingleSelectFormElement();
        formObjects.add(packageName
                .setTag("contact")
                .setHint("Preferred contact method")
                .setType(FormElement.Type.SELECTION)
                .setOptions(Arrays.asList(
                        "Email",
                        "Text",
                        "Printed information",
                        "Text to highlight an email has been sent"))
        );
        formObjects.add(new FormElement()
                .setTag("contact_note")
                .setType(FormElement.Type.TEXTVIEW)
                .setEnabled(false)
                .setValue(getResources().getString(R.string.register_contact_note))
        );

        formObjects.add(new FormButton()
                .setTitle(getString(R.string.register_next))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTextColor(Color.WHITE)
                .setRunnable(() -> {
                    clearAllErrors();

                    boolean isValid = formBuilder.validate();

                    if (isValid) {
                        try {
                            RegistrationChild child = new RegistrationChild(
                                    formBuilder.formMap.get("student_name").getValue(),
                                    DATE_FORMAT.parse(formBuilder.formMap.get("date_of_birth").getValue()),
                                    formBuilder.formMap.get("email").getValue(),
                                    formBuilder.formMap.get("phone").getValue(),
                                    formBuilder.formMap.get("address").getValue(),
                                    formBuilder.formMap.get("medical").getValue(),
                                    formBuilder.formMap.get("experience").getValue(),
                                    null,
                                    formBuilder.formMap.get("contact").getValue(),
                                    DATE_FORMAT.parse(formBuilder.formMap.get("date_joined").getValue()),
                                    formBuilder.formMap.get("hear_about").getValue(),
                                    formBuilder.formMap.get("name").getValue(),
                                    null
                            );

                            Log.d("Register", "Collected child data: " + child);

                            Intent intent = new Intent(this, RegisterPhotoConsentActivity.class);
                            intent.putExtra("registration", child);

                            startActivity(intent);
                        } catch (ParseException e) {
                            Log.e("Register", "Unexpected error submitting form", e);

                            Notification.showNotification(
                                    this,
                                    getMainId(),
                                    R.string.unexpected_error,
                                    Notification.WARNING_BG_COLOR,
                                    Notification.WARNING_TXT_COLOR);
                        }
                    }
                })
        );

        this.formBuilder.build(formObjects);

        formatMultiLineTextView(this.formBuilder.viewMap.get("address"), 4);
        formatMultiLineTextView(this.formBuilder.viewMap.get("hear_about"), 3);
        formatMultiLineTextView(this.formBuilder.viewMap.get("experience"), 3);
        formatMultiLineTextView(this.formBuilder.viewMap.get("medical"), 3);
        formatMultiLineTextView(this.formBuilder.viewMap.get("medical_note"));
        formatMultiLineTextView(this.formBuilder.viewMap.get("contact_note"));

        fixFormBuilderViewMap(formLayout);
    }

}
