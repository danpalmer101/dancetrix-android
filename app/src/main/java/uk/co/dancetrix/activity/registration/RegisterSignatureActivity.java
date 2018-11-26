package uk.co.dancetrix.activity.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.noties.markwon.Markwon;
import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.BaseActivity;
import uk.co.dancetrix.activity.HomeActivity;
import uk.co.dancetrix.domain.RegistrationBase;
import uk.co.dancetrix.domain.RegistrationChild;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.service.impl.FirebaseStorageService;
import uk.co.dancetrix.util.Notification;

public class RegisterSignatureActivity extends BaseActivity {

    private RegistrationBase registration;

    @Override
    protected int getMainId() {
        return R.id.activity_register_signature;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_signature);

        this.registration = getIntent().getParcelableExtra("registration");

        FirebaseStorageService markdownStorageService = new FirebaseStorageService() {
            @Override
            protected String getTag() {
                return "Register Signature";
            }
        };

        String signatureTextFile = "text-signature-adult.md";
        if (registration instanceof RegistrationChild) {
            signatureTextFile = "text-signature-child.md";
        }

        markdownStorageService.loadFile(signatureTextFile,
                signatureText -> {
                    TextView tv = findViewById(R.id.signatureInfoView);
                    Markwon.setMarkdown(tv, signatureText);
                    tv.setMovementMethod(new ScrollingMovementMethod());
                },
                e -> Notification.showNotification(this,
                            getMainId(),
                            R.string.unexpected_error,
                            Notification.ERROR_BG_COLOR,
                            Notification.ERROR_TXT_COLOR)
                );
    }

    public void submit(View view) {
        // TODO - get signature

        // TODO - submit

        Activity current = this;

        ServiceLocator.REGISTRATION_SERVICE.register(this,
                registration,
                new Callback<Boolean, Exception>() {
                    @Override
                    public void onSuccess(Boolean response) {
                        current.runOnUiThread(() -> {
                            Intent intent = new Intent(current, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Notification.setNotificationInIntent(
                                    intent,
                                    R.string.register_submit_success,
                                    Notification.SUCCESS_BG_COLOR,
                                    Notification.SUCCESS_TXT_COLOR);
                            current.startActivity(intent);
                        });
                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.w("Register", "Error submitting the registration details", exception);

                        Notification.showNotification(current,
                                getMainId(),
                                R.string.register_submit_error,
                                Notification.ERROR_BG_COLOR,
                                Notification.ERROR_TXT_COLOR);
                    }
                });
    }

}
