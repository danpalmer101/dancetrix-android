package uk.co.dancetrix.activity.payment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
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
import java.util.List;
import java.util.Locale;

import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.AbstractFormActivity;
import uk.co.dancetrix.activity.HomeActivity;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Notification;

public class PaymentFormActivity extends AbstractFormActivity {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected int getMainId() {
        return R.id.activity_payment_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);

        LinearLayout formLayout = findViewById(R.id.paymentFormContainer);

        final Activity current = this;

        this.formBuilder = new FormBuilder(this, formLayout);

        List<FormObject> formObjects = new ArrayList<>();

        formObjects.add(new FormHeader().setTitle("Your details"));

        formObjects.add(new FormElement()
                .setTag("name")
                .setHint("Your name")
                .setType(FormElement.Type.TEXT)
                .setRequired(true)
                .setErrorMessage("Required")
        );
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

        formObjects.add(new FormHeader().setTitle("Your payment"));
        formObjects.add(new FormElement()
                .setTag("date")
                .setHint("Date")
                .setType(FormElement.Type.DATE)
                .setDateFormat(DATE_FORMAT.toLocalizedPattern())
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("amount")
                .setHint("Amount")
                .setType(FormElement.Type.NUMBER) // TODO decimals
                .setRequired(true)
                .setErrorMessage("Required")
        );
        FormElement method = new SingleSelectFormElement();
        formObjects.add(method
                .setTag("method")
                .setHint("Method")
                .setType(FormElement.Type.SELECTION)
                .setOptions(Arrays.asList(
                        "Bank transfer",
                        "PayPal",
                        "Credit/Debit card",
                        "Cash"))
                .setFormValidation(new RequiredSelectFormValidation(method))
                .setErrorMessage("Required")
        );
        FormElement reason = new SingleSelectFormElement();
        formObjects.add(reason
                .setTag("reason")
                .setHint("Reason")
                .setType(FormElement.Type.SELECTION)
                .setOptions(Arrays.asList(
                        "Children's lesson fees (termly)",
                        "Children's lesson fee (pay as you go)",
                        "Adult's lesson fees (quarterly or monthly)",
                        "Uniform / dancewear",
                        "Show fee",
                        "Exam fee",
                        "Private lesson",
                        "Ballet Burn loyalty card"))
                .setFormValidation(new RequiredSelectFormValidation(reason))
                .setErrorMessage("Required")
        );

        formObjects.add(new FormHeader().setTitle("Anything else we should know?"));
        formObjects.add(new FormElement()
                .setTag("additional")
                .setType(FormElement.Type.TEXTVIEW)
        );

        formObjects.add(new FormButton()
                .setTitle(getString(R.string.payment_submit))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTextColor(Color.WHITE)
                .setRunnable(() -> {
                    clearAllErrors();

                    if (formBuilder.validate()) {
                        try {
                            ServiceLocator.PAYMENT_SERVICE.notify(
                                    current,
                                    DATE_FORMAT.parse(formBuilder.formMap.get("date").getValue()),
                                    Double.parseDouble(formBuilder.formMap.get("amount").getValue()),
                                    formBuilder.formMap.get("name").getValue(),
                                    formBuilder.formMap.get("student_name").getValue(),
                                    formBuilder.formMap.get("email").getValue(),
                                    formBuilder.formMap.get("method").getValue(),
                                    formBuilder.formMap.get("reason").getValue(),
                                    formBuilder.formMap.get("additional").getValue(),
                                    new Callback<Boolean, Exception>() {
                                        @Override
                                        public void onSuccess(Boolean response) {
                                            current.runOnUiThread(() -> {
                                                Intent intent = new Intent(current, HomeActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                Notification.setNotificationInIntent(
                                                        intent,
                                                        R.string.payment_submit_success,
                                                        Notification.SUCCESS_BG_COLOR,
                                                        Notification.SUCCESS_TXT_COLOR);
                                                current.startActivity(intent);
                                            });
                                        }

                                        @Override
                                        public void onError(Exception exception) {
                                            Log.w("Payment", "Error submitting the payment details", exception);

                                            Notification.showNotification(current,
                                                    R.id.activity_payment_form,
                                                    R.string.payment_submit_error,
                                                    Notification.ERROR_BG_COLOR,
                                                    Notification.ERROR_TXT_COLOR);
                                        }
                                    });
                        } catch (ParseException e) {
                            // TODO display error
                        }
                    }
                })
        );

        this.formBuilder.build(formObjects);

        fixFormBuilderViewMap(formLayout);
    }

}
