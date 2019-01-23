package com.corelib.viewcust;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import com.corelib.R;

import androidx.core.content.ContextCompat;


/**
 * @author Created by Abhijit on 23-04-2017.
 */

public class EditTextWithClear extends androidx.appcompat.widget.AppCompatEditText {

    //The image we are going to use for the Clear button

    private Drawable imgCloseButton /*= getResources().getDrawable(android.R.drawable.btn_plus, null)*/;
    private final Context context;
    private AttributeSet attributeSet;

    public EditTextWithClear(Context context) {
        super(context);
        this.context = context;
        initEditTextWithClear();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attributeSet = attrs;
//        setCustomFont(this, context, attrs, "");
        initEditTextWithClear();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attributeSet = attrs;
//        setCustomFont(this, context, attrs, "");
        initEditTextWithClear();
    }

    private void initEditTextWithClear() {

        imgCloseButton = ContextCompat.getDrawable(context, R.mipmap.ic_delete);
        // Set bounds of the Clear button so it will look ok
        if (imgCloseButton != null) {
            imgCloseButton.setBounds(0, 0, 60, 60/*imgCloseButton.getIntrinsicWidth(), imgCloseButton.getIntrinsicHeight()*/);
        }

        // There may be initial text in the field, so we may need to display the  button
        handleClearButton();


        //if the Close image is displayed and the user remove his finger from the button, clear it. Otherwise do nothing
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                EditTextWithClear et = EditTextWithClear.this;

                if (et.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > et.getWidth() - et.getPaddingRight() - imgCloseButton.getIntrinsicWidth()) {
                    et.setText("");
                    EditTextWithClear.this.handleClearButton();
                }
                return false;
            }
        });

        //if text changes, take care of the button
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                EditTextWithClear.this.handleClearButton();
                if (before < count) {

                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });


    }

    protected void setCustomImage(final Drawable customDrawable, final int width, final int height) {
        imgCloseButton = customDrawable;
        imgCloseButton.setBounds(0, 0, width, height);
    }


    private void handleClearButton() {
        if (this.getText().toString().equals("")) {
            // add the clear button
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
        } else {
            //remove clear button
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgCloseButton, this.getCompoundDrawables()[3]);
        }
    }
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new EditTextWithClearKeyEvent(super.onCreateInputConnection(outAttrs), true);
    }

    /*Following class is for sending the backspace event in case user want it, if we do not send it backspace event is is not recognise*/

    private class EditTextWithClearKeyEvent extends InputConnectionWrapper {

        EditTextWithClearKeyEvent(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN
//                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
//
////                EditTextWithClear.this.setNewText();
//                // Un-comment if you wish to cancel the backspace:
//                // return false;
//            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {

            if (beforeLength == 1 && afterLength == 0) {

                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }
}