package uk.co.dancetrix.activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormButton;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormHeader;
import com.dariopellegrini.formbuilder.FormObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.dancetrix.R;

public class PaymentFormActivity extends AbstractFormActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);

        LinearLayout formLayout = findViewById(R.id.paymentFormContainer);

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
                .setDateFormat("dd-MM-yyyy")
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
                .setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        clearAllErrors();

                        boolean isValid = formBuilder.validate();

                        // TODO submit
                    }
                })
        );

        this.formBuilder.build(formObjects);

        fixFormBuilderViewMap(formLayout);
    }

}
