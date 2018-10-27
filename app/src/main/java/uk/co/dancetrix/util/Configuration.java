package uk.co.dancetrix.util;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Arrays;

import uk.co.dancetrix.BuildConfig;
import uk.co.dancetrix.R;

public class Configuration {

    // Set up

    static {
        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build();
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings);
        FirebaseRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults);
        FirebaseRemoteConfig.getInstance().fetch().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseRemoteConfig.getInstance().activateFetched();
            }
        });
    }

    // Remote config - website

    public static String getWebsiteUrl() {
        return getRemoteConfig("dancetrix_website");
    }

    // Remote config - uniform catalog

    public static String getUniformCatalog() {
        return getRemoteConfig("dancetrix_uniform_catalog");
    }

    // Remote config - features

    public static boolean bookClassEnabled() {
        return isTrue(getRemoteConfig("feature_book"));
    }

    public static boolean calendarEnabled() {
        return isTrue(getRemoteConfig("feature_calendar"));
    }

    public static boolean paymentEnabled() {
        return isTrue(getRemoteConfig("feature_payment"));
    }

    public static boolean uniformEnabled() {
        return isTrue(getRemoteConfig("feature_uniform"));
    }

    public static boolean aboutEnabled() {
        return isTrue(getRemoteConfig("feature_about"));
    }

    // Remote config - emails

    // Remote config - Emails

   public static String fromRegistrationEmailAddress() {
        return getRemoteConfig("email_address_registration_from");
    }

    public static String fromPaymentEmailAddress() {
        return getRemoteConfig("email_address_payment_from");
    }

    public static String fromBookingEmailAddress() {
        return getRemoteConfig("email_address_booking_from");
    }

    public static String fromUniformOrderEmailAddress() {
        return getRemoteConfig("email_address_uniform_from");
    }

    public static String toEmailAddress() {
        //return getRemoteConfig("email_address_to");
        return "d.palmer101@googlemail.com";
    }

    public static String mailgunDomain() {
        return getRemoteConfig("mailgun_domain");
    }

    public static String mailgunApiKey() {
        return getRemoteConfig("mailgun_api_key");
    }

    // Tools

    private static boolean isTrue(String value) {
        return value != null && Arrays.asList("yes", "true", "1").contains(value.toLowerCase());
    }

    private static String getRemoteConfig(String key) {
        return FirebaseRemoteConfig.getInstance().getString(key);
    }

}
