package uk.co.dancetrix.activity.registration;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.BaseActivity;
import uk.co.dancetrix.util.Configuration;
import uk.co.dancetrix.util.NetworkUtil;

public class RegisterMenuActivity extends BaseActivity {

    @Override
    protected int getMainId() {
        return R.id.activity_register_menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);
    }

    public void selectAdult(View view) {
        // TODO
    }

    public void selectChild(View view) {
        // TODO
    }

}
