package com.myapp.adminside.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import com.myapp.adminside.R;
import com.myapp.adminside.helper.Functions;


public class TfTextView extends android.support.v7.widget.AppCompatTextView {

    private int fTypeValue = 0;
    private boolean isUnderline = false;
    private boolean isItalic = false;
    private boolean isStrikethrough = false;

    public TfTextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            init();
        }
    }

    public TfTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TfTextView, 0, 0);
            try {
                isUnderline = a.getBoolean(R.styleable.TfTextView_isUnderline, false);
                isItalic = a.getBoolean(R.styleable.TfTextView_isItalic, false);
                isStrikethrough = a.getBoolean(R.styleable.TfTextView_isStrikeThrough, false);
                if (a.hasValue(R.styleable.TfTextView_ftype)) {
                    fTypeValue = a.getInt(R.styleable.TfTextView_ftype, 0);
                }

            } finally {
                a.recycle();
            }
            init();
        }
    }

    private void init() {
        try {
            setTypeface(Functions.getFontType(getContext(), fTypeValue));
            if (isUnderline) {
                setText(Html.fromHtml("<u>" + getText().toString().trim() + "</u>"));
            }
            if (isItalic) {
                setTypeface(getTypeface(), Typeface.ITALIC);
            }
            if (isStrikethrough) {
                setPaintFlags(getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
