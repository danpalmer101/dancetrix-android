package uk.co.dancetrix.util;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import uk.co.dancetrix.BuildConfig;
import uk.co.dancetrix.R;

public class Configuration {

    static {
        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build();
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings);

        FirebaseRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults);
    }

    public static String getWebsiteUrl() {
        return getRemoteConfig("dancetrix_website");
    }

    private static String getRemoteConfig(String key) {
        return FirebaseRemoteConfig.getInstance().getString(key);
    }

}
