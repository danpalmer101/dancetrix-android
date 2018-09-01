package uk.co.dancetrix.activity;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Notification;

public class UniformActivity extends AbstractFormActivity {

    private static final String NO = "No";
    private static final String YES = "Yes";

    private List<UniformGroup> groups;

    @Override
    protected int getMainId() {
        return R.id.activity_uniform;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform);

        final Activity current = this;

        ServiceLocator.UNIFORM_SERVICE.getUniformOrderItems(this, new Callback<List<UniformGroup>, Exception>() {
            @Override
            public void onSuccess(List<UniformGroup> response) {
                groups = response;

                current.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        buildForm();
                    }
                });
            }

            @Override
            public void onError(Exception exception) {
                Notification.showNotification(
                        current,
                        getMainId(),
                        R.string.uniform_load_error,
                        Notification.WARNING_BG_COLOR,
                        Notification.WARNING_TXT_COLOR);
            }
        });
    }

    private void buildForm() {
        LinearLayout formLayout = findViewById(R.id.paymentFormContainer);
        this.formBuilder = new FormBuilder(this, formLayout);

        final Activity current = this;

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

        for (UniformGroup group : this.groups) {
            formObjects.add(new FormHeader().setTitle(group.getName()));
            for (UniformItem item : group.getItems()) {
                if (item.getSizes() == null || item.getSizes().isEmpty()) {
                    FormElement element = new SingleSelectFormElement();
                    formObjects.add(element
                            .setTag(item.getKey())
                            .setHint(item.getName())
                            .setType(FormElement.Type.SELECTION)
                            .setOptions(Arrays.asList(NO, YES))
                    );
                } else {
                    List<String> sizes = new ArrayList<>();
                    sizes.add(NO);
                    sizes.addAll(item.getSizes());

                    FormElement element = new SingleSelectFormElement();
                    formObjects.add(element
                            .setTag(item.getKey())
                            .setHint(item.getName())
                            .setType(FormElement.Type.SELECTION)
                            .setOptions(sizes)
                    );
                }
            }
        }

        formObjects.add(new FormHeader().setTitle("Payment"));

        FormElement paymentMade = new SingleSelectFormElement();
        formObjects.add(paymentMade
                .setTag("payment_made")
                .setHint("Payment made?")
                .setType(FormElement.Type.SELECTION)
                .setOptions(Arrays.asList(NO, YES))
                .setFormValidation(new RequiredSelectFormValidation(paymentMade))
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

        formObjects.add(new FormHeader().setTitle("Anything else we should know?"));
        formObjects.add(new FormElement()
                .setTag("additional")
                .setType(FormElement.Type.TEXTVIEW)
        );

        formObjects.add(new FormButton()
                .setTitle(getString(R.string.uniform_submit))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTextColor(Color.WHITE)
                .setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        clearAllErrors();

                        boolean isValid = formBuilder.validate();

                        if (isValid) {
                            Map<UniformItem, String> orderItems = new LinkedHashMap<>();

                            for (UniformGroup group : groups) {
                                for (UniformItem item : group.getItems()) {
                                    if (formBuilder.formMap.containsKey(item.getKey())) {
                                        String value = formBuilder.formMap.get(item.getKey()).getValue();
                                        if (value != null && !value.equals(NO)) {
                                            orderItems.put(item, value);
                                        }
                                    }
                                }
                            }

                            ServiceLocator.UNIFORM_SERVICE.orderUniform(
                                    formBuilder.formMap.get("name").getValue(),
                                    formBuilder.formMap.get("student_name").getValue(),
                                    formBuilder.formMap.get("email").getValue(),
                                    null,
                                    YES.equals(formBuilder.formMap.get("payment_made").getValue()),
                                    formBuilder.formMap.get("method").getValue(),
                                    formBuilder.formMap.get("additional").getValue(),
                                    orderItems,
                                    new Callback<Boolean, Exception>() {
                                        @Override
                                        public void onSuccess(Boolean response) {
                                            current.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(current, HomeActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    Notification.setNotificationInIntent(
                                                            intent,
                                                            R.string.uniform_submit_success,
                                                            Notification.SUCCESS_BG_COLOR,
                                                            Notification.SUCCESS_TXT_COLOR);
                                                    current.startActivity(intent);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(Exception exception) {
                                            Log.w("Uniform", "Error submitting the uniform order", exception);

                                            Notification.showNotification(current,
                                                    R.id.activity_uniform,
                                                    R.string.uniform_submit_error,
                                                    Notification.ERROR_BG_COLOR,
                                                    Notification.ERROR_TXT_COLOR);
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
