package uk.co.dancetrix.activity;

import android.os.Bundle;

import uk.co.dancetrix.R;

public class ClassDetailsActivity extends BaseActivity {

    public static final String INTENT_KEY_CLASS_DETAILS = "class_details";

    @Override
    protected int getMainId() {
        return R.id.activity_class_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
    }

}
