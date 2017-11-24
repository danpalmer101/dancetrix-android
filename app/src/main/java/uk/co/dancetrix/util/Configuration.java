package uk.co.dancetrix.util;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

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

    // Tools

    private static String getRemoteConfig(String key) {
        return FirebaseRemoteConfig.getInstance().getString(key);
    }

}
