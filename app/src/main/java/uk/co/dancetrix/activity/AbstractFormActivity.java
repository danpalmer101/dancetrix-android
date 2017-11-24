package uk.co.dancetrix.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormValidation;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractFormActivity extends AppCompatActivity {

    protected FormBuilder formBuilder;

    /**
     * HACK : Make sure the correct views are set in the viewMap
     * https://github.com/dariopellegrini/FormBuilder/issues/3
     */
    protected void fixFormBuilderViewMap(LinearLayout formLayout) {
        for (int i = 0; i < formLayout.getChildCount(); i++) {
            View view = formLayout.getChildAt(i);
            if (view instanceof TextInputLayout) {
                TextInputLayout inputLayout = (TextInputLayout)view;

                for (FormElement e : this.formBuilder.formMap.values()) {
                    if (e.getHint() != null
                            && inputLayout.getHint() != null
                            && e.getHint().equals(inputLayout.getHint())) {
                        this.formBuilder.viewMap.put(e.getTagOrToString(), inputLayout.getEditText());
                        break;
                    }
                }
            }
        }
    }

    /**
     * HACK : Errors on some fields are not cleared when changing their value, so clear errors manually
     */
    protected void clearAllErrors() {
        for (View v : this.formBuilder.viewMap.values()) {
            if (v instanceof EditText) {
                ((EditText)v).setError(null);
            }
        }
    }

    /**
     * HACK : Override the form element to prevent multiple options being selected on a single select form element
     * https://github.com/dariopellegrini/FormBuilder/issues/1
     */
    class SingleSelectFormElement extends FormElement {
        @Override
        public FormElement setOptionsSelected(List<String> optionsSelected) {
            List<String> options = new ArrayList<String>() {
                @Override
                public String toString() {
                    return TextUtils.join(",", super.toArray());
                }
            };
            if (optionsSelected.size() > 0) {
                options.add(optionsSelected.get(optionsSelected.size() - 1));
            }
            return super.setOptionsSelected(options);
        }
    }

    /**
     * HACK : Custom validation for SELECTION FormElements
     * https://github.com/dariopellegrini/FormBuilder/issues/2
     */
    class RequiredSelectFormValidation extends FormValidation {

        private FormElement formElement;

        RequiredSelectFormValidation(FormElement formElement) {
            this.formElement = formElement;
        }

        @Override
        public boolean validate() {
            return this.formElement.getOptionsSelected() != null
                    && this.formElement.getOptionsSelected().size() > 0;
        }

    }

}
