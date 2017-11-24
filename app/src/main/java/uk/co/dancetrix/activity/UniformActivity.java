package uk.co.dancetrix.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormButton;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormHeader;
import com.dariopellegrini.formbuilder.FormObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.co.dancetrix.R;
import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;

public class UniformActivity extends AbstractFormActivity {

    private List<UniformGroup> groups = Arrays.asList(
            new UniformGroup("A", Arrays.asList(
                    new UniformItem("a1", "1", Collections.<String>emptyList()),
                    new UniformItem("a2", "2", Collections.<String>emptyList()),
                    new UniformItem("a3", "3", Arrays.asList("X", "Y", "Z"))
            )),
            new UniformGroup("B", Arrays.asList(
                    new UniformItem("b1", "1", Collections.<String>emptyList()),
                    new UniformItem("b2", "2", Arrays.asList("X", "Y", "Z")),
                    new UniformItem("b3", "3", Collections.<String>emptyList())
            ))
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uniform);

        LinearLayout formLayout = findViewById(R.id.paymentFormContainer);

        this.formBuilder = new FormBuilder(this, formLayout);

        List<FormObject> formObjects = new ArrayList<>();

        formObjects.add(new FormHeader().setTitle("Your details"));

        formObjects.add(new FormElement()
                .setTag("name")
                .setHint("Your name")
                .setType(FormElement.Type.TEXT)
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("student_name")
                .setHint("Student's name")
                .setType(FormElement.Type.TEXT)
                .setRequired(true)
                .setErrorMessage("Required")
        );
        formObjects.add(new FormElement()
                .setTag("email")
                .setHint("Email address")
                .setType(FormElement.Type.EMAIL)
                .setRequired(true)
                .setErrorMessage("Required")
        );

        for (UniformGroup group : groups) {
            formObjects.add(new FormHeader().setTitle(group.getName()));
            for (UniformItem item : group.getItems()) {
                if (item.getSizes() == null || item.getSizes().isEmpty()) {
                    FormElement element = new SingleSelectFormElement();
                    formObjects.add(element
                            .setTag(item.getKey())
                            .setHint(item.getName())
                            .setType(FormElement.Type.SELECTION)
                            .setOptions(Arrays.asList("No", "Yes"))
                    );
                } else {
                    List<String> sizes = new ArrayList<>();
                    sizes.add("No");
                    sizes.addAll(item.getSizes());

                    FormElement element = new SingleSelectFormElement();
                    formObjects.add(element
                            .setTag(item.getKey())
                            .setHint(item.getName())
                            .setType(FormElement.Type.SELECTION)
                            .setOptions(sizes)
                    );
                }
            }
        }

        formObjects.add(new FormHeader().setTitle("Anything else we should know?"));
        formObjects.add(new FormElement()
                .setTag("additional")
                .setType(FormElement.Type.TEXTVIEW)
        );

        formObjects.add(new FormButton()
                .setTitle(getString(R.string.uniform_submit))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setTextColor(Color.WHITE)
                .setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        clearAllErrors();

                        boolean isValid = formBuilder.validate();

                        // TODO submit
                    }
                })
        );

        this.formBuilder.build(formObjects);

        fixFormBuilderViewMap(formLayout);
    }

}
