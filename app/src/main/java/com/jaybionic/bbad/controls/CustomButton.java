package com.jaybionic.bbad.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kisjason on 2/2/16.
 */
public class CustomButton extends Button {

    public boolean isActive = false;

    private ViewGroup p;
    private int idx;


    public void activate() {
        setBackgroundColor(Color.parseColor("#008577"));
        isActive = true;
    }
    public void deactivate() {
        setBackgroundColor(Color.parseColor("#eaeded"));
        isActive = false;
    }


    @OnClick
    public void onClick() {
        //Log.i("CustomButton", "onClick");
        p = (ViewGroup) getParent();

        for(int i=0; i<p.getChildCount(); i++) {
            ((CustomButton) p.getChildAt(i)).deactivate();
        }
        activate();
    }


    public CustomButton(Context context) {
        super(context);
    }
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        ButterKnife.bind(this);
    }
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
