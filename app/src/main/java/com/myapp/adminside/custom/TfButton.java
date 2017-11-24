package com.myapp.adminside.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.myapp.adminside.helper.FontType;
import com.myapp.adminside.helper.Functions;


/**
 * Created by dhruvil on 27-07-2016.
 */

public class TfButton extends AppCompatButton {

    private Context _ctx;

    public TfButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    public TfButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this._ctx = context;
            init();
        }
    }

    private void init() {
        try {
            // setTypeface(Functions.getLatoFont(_ctx));
            setTypeface(Functions.getFontType(_ctx, FontType.Bold.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
