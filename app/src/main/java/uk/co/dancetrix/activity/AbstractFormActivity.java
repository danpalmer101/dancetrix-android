package uk.co.dancetrix.activity;

import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dariopellegrini.formbuilder.FormBuilder;
import com.dariopellegrini.formbuilder.FormElement;
import com.dariopellegrini.formbuilder.FormValidation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFormActivity extends BaseActivity {

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
                            && e.getHint().contentEquals(inputLayout.getHint())) {
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

    protected void formatMultiLineTextView(View view) {
        formatMultiLineTextView(view, 0);
    }

    protected void formatMultiLineTextView(View view, int lines) {
        if (view != null && view instanceof EditText) {
            EditText editText = (EditText)view;
            editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            editText.setSingleLine(false);
            editText.setTextColor(0xFFEEEEEE);
            if (lines > 0) {
                editText.setLines(lines);
            }
        }
    }

    protected void formatDecimalTextView(View view) {
        if (view != null && view instanceof EditText) {
            EditText editText = (EditText)view;
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
    }

    /**
     * HACK : Override the form element to prevent multiple options being selected on a single select form element
     * https://github.com/dariopellegrini/FormBuilder/issues/1
     */
    protected class SingleSelectFormElement extends FormElement {

        public SingleSelectFormElement() {
            super();
        }

        @Override
        public FormElement setOptions(List<String> options) {
            super.setOptions(new ArrayList<>());
            super.getOptions().addAll(options);
            return this;
        }

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

        @Override
        public String getValue() {
            if (!getOptionsSelected().isEmpty()) {
                return getOptionsSelected().get(0);
            } else {
                return super.getValue();
            }
        }
    }

    /**
     * HACK : Custom validation for SELECTION FormElements
     * https://github.com/dariopellegrini/FormBuilder/issues/2
     */
    protected class RequiredSelectFormValidation extends FormValidation {

        private FormElement formElement;

        public RequiredSelectFormValidation(FormElement formElement) {
            this.formElement = formElement;
        }

        @Override
        public boolean validate() {
            return this.formElement.getOptionsSelected() != null
                    && this.formElement.getOptionsSelected().size() > 0;
        }

    }

}
