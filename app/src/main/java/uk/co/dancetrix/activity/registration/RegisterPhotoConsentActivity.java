package uk.co.dancetrix.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import ru.noties.markwon.Markwon;
import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.BaseActivity;
import uk.co.dancetrix.domain.RegistrationChild;
import uk.co.dancetrix.service.impl.FirebaseStorageService;
import uk.co.dancetrix.util.Notification;

public class RegisterPhotoConsentActivity extends BaseActivity {

    private RegistrationChild registration;

    @Override
    protected int getMainId() {
        return R.id.activity_register_photo_consent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter_photo_consent);

        this.registration = getIntent().getParcelableExtra("registration");

        FirebaseStorageService markdownStorageService = new FirebaseStorageService() {
            @Override
            protected String getTag() {
                return "Register Photo";
            }
        };

        markdownStorageService.loadFile("text-photos.md",
                photoText -> {
                    TextView tv = findViewById(R.id.photoConsentInfoView);
                    Markwon.setMarkdown(tv, photoText);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                },
                e -> Notification.showNotification(this,
                            getMainId(),
                            R.string.unexpected_error,
                            Notification.ERROR_BG_COLOR,
                            Notification.ERROR_TXT_COLOR)
                );
    }

    public void submitConsentYes(View view) {
        submitConsent("Yes");
    }

    public void submitConsentNo(View view) {
        submitConsent("No");
    }

    public void submitConsentUnidentifiable(View view) {
        submitConsent("Yes, non-identifiable");
    }

    private void submitConsent(String value) {
        registration.setPhotoConsent(value);

        Intent intent = new Intent(this, RegisterSignatureActivity.class);
        intent.putExtra("registration", registration);
        startActivity(intent);
    }

}
