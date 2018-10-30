package uk.co.dancetrix.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.service.Callback;
import uk.co.dancetrix.service.ServiceLocator;
import uk.co.dancetrix.util.Display;
import uk.co.dancetrix.util.Notification;

public class ClassMenuActivity extends BaseActivity {

    public static final String INTENT_KEY_CLASS_MENU = "class_menu";

    @Override
    protected int getMainId() {
        return R.id.activity_class_menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_menu);

        ClassMenu classMenu = (ClassMenu)getIntent().getSerializableExtra(INTENT_KEY_CLASS_MENU);

        if (classMenu == null) {
            loadClassMenu();
        } else {
            displayClassMenu(classMenu);
        }
    }

    private void loadClassMenu() {
        final Activity current = this;

        ServiceLocator.CLASS_SERVICE.getClassMenu(this, new Callback<ClassMenu, Exception>() {
            @Override
            public void onSuccess(final ClassMenu response) {
                displayClassMenu(response);
            }

            @Override
            public void onError(Exception exception) {
                Log.w("Classes", "Error loading class menu", exception);

                Notification.showNotification(
                        current,
                        getMainId(),
                        R.string.booking_class_menu_error,
                        Notification.WARNING_BG_COLOR,
                        Notification.WARNING_TXT_COLOR);
            }
        });
    }

    private void displayClassMenu(ClassMenu classMenu) {
        ((Toolbar)findViewById(R.id.toolbar)).setTitle(classMenu.getName());

        for (ClassMenu subMenu : classMenu.getChildren()) {
            Button button;
            if (subMenu.getClassDetails() == null) {
                button = createClassMenuButton(subMenu);
            } else {
                button = createClassDetailsButton(subMenu);
            }

            ViewGroup linearLayout = findViewById(R.id.classMenuListLayout);
            linearLayout.addView(button);
        }
    }

    private Button createClassMenuButton(final ClassMenu subMenu) {
        final Activity current = this;

        Button button = createDefaultButton();
        button.setText(subMenu.getName());
        button.setOnClickListener(view -> {
            Intent intent = new Intent(current, ClassMenuActivity.class);
            intent.putExtra(INTENT_KEY_CLASS_MENU, subMenu);
            current.startActivity(intent);
        });

        return button;
    }

    private Button createClassDetailsButton(final ClassMenu classMenu) {
        final Activity current = this;

        Button button = createDefaultButton();
        button.setText(classMenu.getName());
        button.setOnClickListener(view -> {
            Intent intent = new Intent(current, ClassDetailsActivity.class);
            intent.putExtra(ClassDetailsActivity.INTENT_KEY_CLASS_DETAILS, classMenu.getClassDetails());
            current.startActivity(intent);
        });

        return button;
    }

    private Button createDefaultButton() {
        Button button = new Button(this);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        button.setAllCaps(false);
        button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_chevron_right, 0);

        int padding = Display.convertDpToPixels(this, 10);
        button.setPadding(padding, 0, padding, 0);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Display.convertDpToPixels(this, 75)));

        return button;
    }

}
