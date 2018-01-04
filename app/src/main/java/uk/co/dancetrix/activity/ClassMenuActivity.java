package uk.co.dancetrix.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;
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
            Notification.showNotification(
                    this,
                    getMainId(),
                    R.string.booking_class_menu_error,
                    Notification.WARNING_BG_COLOR,
                    Notification.WARNING_TXT_COLOR);
        } else {
            for (ClassMenu subMenu : classMenu.getChildren()) {
                Button button;
                if (subMenu.getClassDetails() == null) {
                    button = createClassMenuButton(subMenu);
                } else {
                    button = createClassDetailsButton(subMenu.getClassDetails());
                }

                ViewGroup linearLayout = findViewById(R.id.classMenuListLayout);
                linearLayout.addView(button);
            }
        }
    }

    private Button createClassMenuButton(final ClassMenu subMenu) {
        final Activity current = this;

        Button button = createDefaultButton();
        button.setText(subMenu.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(current, ClassMenuActivity.class);
                intent.putExtra(INTENT_KEY_CLASS_MENU, subMenu);
                current.startActivity(intent);
            }
        });

        return button;
    }

    private Button createClassDetailsButton(final ClassDetails classDetails) {
        final Activity current = this;

        Button button = createDefaultButton();
        button.setText(classDetails.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(current, ClassDetailsActivity.class);
                intent.putExtra(ClassDetailsActivity.INTENT_KEY_CLASS_DETAILS, classDetails);
                current.startActivity(intent);
            }
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
