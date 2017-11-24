package uk.co.dancetrix.activity;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormButton;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormHeader;
import com.dariopellegrini.formbuilder.FormObject;
import com.dariopellegrini.formbuilder.FormValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.dancetrix.R;

public class PaymentFormActivity extends AppCompatActivity {

    private LinearLayout formLayout;
    private FormBuilder formBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);

        this.formLayout = findViewById(R.id.paymentFormContainer);
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
                        // Clear all errors
                        for (View v : formBuilder.viewMap.values()) {
                            if (v instanceof EditText) {
                                ((EditText)v).setError(null);
                            }
                        }
                        boolean isValid = formBuilder.validate();
                    }
                })
        );

        formBuilder.build(formObjects);

        // HACK : Make sure the correct views are set in the viewMap
        // https://github.com/dariopellegrini/FormBuilder/issues/3
        for (int i = 0; i < formLayout.getChildCount(); i++) {
            View view = formLayout.getChildAt(i);
            if (view instanceof TextInputLayout) {
                TextInputLayout inputLayout = (TextInputLayout)view;

                for (FormElement e : formBuilder.formMap.values()) {
                    if (e.getHint() != null
                            && inputLayout.getHint() != null
                            && e.getHint().equals(inputLayout.getHint())) {
                        formBuilder.viewMap.put(e.getTagOrToString(), inputLayout.getEditText());
                        break;
                    }
                }
            }
        }
    }

    /**
     * HACK : Override the form element to prevent multiple options being selected on a single select form element
     * https://github.com/dariopellegrini/FormBuilder/issues/1
     */
    class SingleSelectFormElement extends FormElement {
        @Override
        public FormElement setOptionsSelected(List<String> optionsSelected) {
            List<String> options = new ArrayList<String>() {
                @Override
                public String toString() {
                    return TextUtils.join(",", super.toArray());
                }
            };
            if (optionsSelected.size() > 0) {
                options.add(optionsSelected.get(optionsSelected.size() - 1));
            }
            return super.setOptionsSelected(options);
        }
    }

    /**
     * HACK : Custom validation for SELECTION FormElements
     * https://github.com/dariopellegrini/FormBuilder/issues/2
     */
    class RequiredSelectFormValidation extends FormValidation {

        private FormElement formElement;

        public RequiredSelectFormValidation(FormElement formElement) {
            this.formElement = formElement;
        }

        @Override
        public boolean validate() {
            return formElement.getOptionsSelected() != null
                    && formElement.getOptionsSelected().size() > 0;
        }

    }
}
