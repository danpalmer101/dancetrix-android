package uk.co.dancetrix.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import uk.co.dancetrix.R;
import uk.co.dancetrix.activity.BaseActivity;

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
        startActivity(new Intent(this, RegisterAdultActivity.class));
    }

    public void selectChild(View view) {
        startActivity(new Intent(this, RegisterChildActivity.class));
    }

}
