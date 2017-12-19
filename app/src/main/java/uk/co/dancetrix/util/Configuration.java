package uk.co.dancetrix.util;

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

    // Tools

    private static boolean isTrue(String value) {
        if (value == null) {
            return false;
        } else {
            return Arrays.asList("yes", "true", "1").contains(value.toLowerCase());
        }
    }

    private static String getRemoteConfig(String key) {
        return FirebaseRemoteConfig.getInstance().getString(key);
    }

}
