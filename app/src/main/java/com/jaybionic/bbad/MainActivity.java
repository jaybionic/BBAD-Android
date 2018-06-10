package com.jaycorp.bbad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jaycorp.animation.BBAD;
import com.jaycorp.animation.Easing;
import com.jaycorp.bbad.controls.CustomButton;
import com.jaycorp.utils.Logs;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private String currFuncStr = "to";
    private Interpolator currEase = null;
    private String currEaseStr = "null";
    private ArrayList<Interpolator> easing = new ArrayList<>();
    private ArrayList<String> easing_names = new ArrayList<>();
    private String currProp = "alpha";
    private ArrayList<String> props = new ArrayList<>();

    @BindView(R.id.spnr_prop) Spinner spnr_prop;
    @BindView(R.id.spnr_easing) Spinner spnr_easing;
    @BindView(R.id.et_duration) EditText et_duration;
    @BindView(R.id.et_startvalue) EditText et_startvalue;
    @BindView(R.id.et_endvalue) EditText et_endvalue;
    @BindView(R.id.et_delay) EditText et_delay;
    @BindView(R.id.et_stagger) EditText et_stagger;
    @BindView(R.id.startvalue) View startvalue;
    @BindView(R.id.stagger) View stagger;
    @BindView(R.id.btn_reset) Button btn_reset;
    @BindView(R.id.btn_run) Button btn_run;
    @BindView(R.id.content) ViewGroup content;
    @BindView(R.id.single) View single;
    @BindView(R.id.multiple) ViewGroup multiple;
    @BindView(R.id.code) TextView code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.KEEP_SCREEN_ON
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/AmazonEmber_Rg.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        ButterKnife.bind(this);

        ((CustomButton) findViewById(R.id.btn_to)).activate();
        findViewById(R.id.btn_to).requestFocus();

        easing_names.add("linear");
        easing.add(null);

        populateProps();

        Field[] fields = Easing.class.getFields();
        for(Field field : fields) {
            if(field.getType() == Interpolator.class) {
                try {
                    Interpolator i = (Interpolator) field.get(this);
                    //Log.i("APP", field.get(this) + "  ::  " + field.getName());
                    easing_names.add(field.getName());
                    easing.add(i);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        ArrayAdapter<String> easing_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, easing_names);
        spnr_easing.setAdapter(easing_adapter);

        updateFields();

    }

    public void updateCode() {
        String prefix = "BBAD." + currFuncStr + "(<font color=#ae00da>view</font><font color=#ff8a00>,</font> ";
        String suffix = ").start();";


        String delay = et_delay.getText().toString().equals("0") ? "" : "<font color=#ff8a00>,</font> <font color=#39a1ea>" + et_delay.getText().toString() + "</font>";
        String ease = currEaseStr == "null" ? "" : "<font color=#ff8a00>,</font> Easing.<font color=#ae00da><i>" + currEaseStr + "</i></font>";

        String str = prefix +
                "<font color=#39a1ea>" + et_duration.getText().toString() + "</font>" +
                "<font color=#ff8a00>,</font> " +
                "<font color=#39a1ea>" + et_endvalue.getText().toString() + "</font>" +
                "<font color=#ff8a00>,</font> " +
                "<font color=#1cb403>" + "\"" + currProp + "\"</font>" +
                delay +
                ease + suffix;


        switch (currFuncStr) {
            case "to":
                //
                break;
            case "fromTo":
                str = prefix +
                        "<font color=#39a1ea>" + et_duration.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_startvalue.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_endvalue.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#1cb403>" + "\"" + currProp + "\"</font>" +
                        delay +
                        ease + suffix;
                break;
            case "allTo":
                str = "BBAD." + currFuncStr +
                        "(BBAD.<i>getList</i>(<font color=#ae00da>viewgroup</font>)" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_duration.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_endvalue.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#1cb403>" + "\"" + currProp + "\"</font>" +
                        delay +
                        ease + suffix;
                break;
            case "staggerTo":
                str = "BBAD." + currFuncStr +
                        "(BBAD.<i>getList</i>(<font color=#ae00da>viewgroup</font>)" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_duration.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_endvalue.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#1cb403>" + "\"" + currProp + "\"</font>" +
                        delay +
                        ", <font color=#39a1ea>" + et_stagger.getText().toString() + "</font>" +
                        ease + suffix;
                break;
            case "staggerFromTo":
                str = "BBAD." + currFuncStr +
                        "(BBAD.<i>getList</i>(<font color=#ae00da>viewgroup</font>)" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_duration.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_startvalue.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#39a1ea>" + et_endvalue.getText().toString() + "</font>" +
                        "<font color=#ff8a00>,</font> " +
                        "<font color=#1cb403>" + "\"" + currProp + "\"</font>" +
                        delay +
                        ", <font color=#39a1ea>" + et_stagger.getText().toString() + "</font>" +
                        ease +
                        suffix;
                break;
        }


        //BBAD.to(single, 300, 200, "alpha", Easing.easeInOutExpo).start();


        code.setText(Html.fromHtml(str));
    }

    public void updateFields() {
        switch (currFuncStr) {
            case "to":
                startvalue.setVisibility(View.GONE);
                stagger.setVisibility(View.GONE);
                break;
            case "fromTo":
                startvalue.setVisibility(View.VISIBLE);
                stagger.setVisibility(View.GONE);
                break;
            case "allTo":
                startvalue.setVisibility(View.GONE);
                stagger.setVisibility(View.GONE);
                break;
            case "staggerTo":
                startvalue.setVisibility(View.GONE);
                stagger.setVisibility(View.VISIBLE);
                break;
            case "staggerFromTo":
                startvalue.setVisibility(View.VISIBLE);
                stagger.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnTouch({R.id.btn_to, R.id.btn_fromto, R.id.btn_allto, R.id.btn_staggerto, R.id.btn_staggerfromto})
    public boolean onFuncChange(Button btn) {
        currFuncStr = btn.getText().toString();
        updateCode();
        updateFields();
        onResetClick();
        return false;
    }

    @OnTextChanged({R.id.et_duration, R.id.et_startvalue, R.id.et_endvalue, R.id.et_delay, R.id.et_stagger})
    public void onTextChange() {
        updateCode();
        updateFields();
        //onResetClick();
    }

    @OnItemSelected(R.id.spnr_prop)
    public void propSelected(int position) {
        Logs.i("#" + position + " " + props.get(position));
        currProp = props.get(position);
        updateCode();
    }

    @OnItemSelected(R.id.spnr_easing)
    public void easingSelected(int position) {
        Logs.i("#" + position + " " + easing_names.get(position));
        currEase = position > 0 ? easing.get(position) : null;
        currEaseStr = position > 0 ? easing_names.get(position) : "null";
        updateCode();
    }

    @OnClick(R.id.btn_run)
    public void onRunClick() {
        switch (currFuncStr) {
            case "to":
                Logs.i("RUN: " + currEase);
                BBAD.to(single,
                        Long.parseLong(et_duration.getText().toString()),
                        Float.parseFloat(et_endvalue.getText().toString()),
                        currProp,
                        Long.parseLong(et_delay.getText().toString()), currEase).start();
                break;
            case "fromTo":
                BBAD.fromTo(single,
                        Long.parseLong(et_duration.getText().toString()),
                        Float.parseFloat(et_startvalue.getText().toString()),
                        Float.parseFloat(et_endvalue.getText().toString()),
                        currProp,
                        Long.parseLong(et_delay.getText().toString()),
                        currEase).start();
                break;
            case "allTo":
                BBAD.allTo(BBAD.getList(multiple),
                        Long.parseLong(et_duration.getText().toString()),
                        Float.parseFloat(et_endvalue.getText().toString()),
                        currProp,
                        Long.parseLong(et_delay.getText().toString()),
                        currEase).start();
                break;
            case "staggerTo":
                BBAD.staggerTo(BBAD.getList(multiple),
                        Long.parseLong(et_duration.getText().toString()),
                        Float.parseFloat(et_endvalue.getText().toString()),
                        currProp,
                        Long.parseLong(et_delay.getText().toString()),
                        Long.parseLong(et_stagger.getText().toString()),
                        currEase).start();
                break;
            case "staggerFromTo":
                BBAD.staggerFromTo(BBAD.getList(multiple),
                        Long.parseLong(et_duration.getText().toString()),
                        Float.parseFloat(et_startvalue.getText().toString()),
                        Float.parseFloat(et_endvalue.getText().toString()),
                        currProp,
                        Long.parseLong(et_delay.getText().toString()),
                        Long.parseLong(et_stagger.getText().toString()),
                        currEase).start();
                break;
        }

        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @OnClick(R.id.btn_reset)
    public void onResetClick() {
        switch (currFuncStr) {
            case "to":
                single.setVisibility(View.VISIBLE);
                multiple.setVisibility(View.GONE);
                single.setAlpha(1);
                single.setTranslationX(40);
                single.setTranslationY(0);
                single.setScaleX(1);
                single.setScaleY(1);
                single.setRotation(0);
                single.setRotationX(0);
                single.setRotationY(0);
                break;
            case "fromTo":
                single.setVisibility(View.VISIBLE);
                multiple.setVisibility(View.GONE);
                single.setAlpha(1);
                single.setTranslationX(40);
                single.setTranslationY(0);
                single.setScaleX(1);
                single.setScaleY(1);
                single.setRotation(0);
                single.setRotationX(0);
                single.setRotationY(0);
                break;
            default:
                single.setVisibility(View.GONE);
                multiple.setVisibility(View.VISIBLE);
                for(int i=0; i<multiple.getChildCount(); i++) {
                    multiple.getChildAt(i).setAlpha(1);
                    multiple.getChildAt(i).setTranslationX(0);
                    multiple.getChildAt(i).setTranslationY(0);
                    multiple.getChildAt(i).setScaleX(1);
                    multiple.getChildAt(i).setScaleY(1);
                    multiple.getChildAt(i).setRotation(0);
                    multiple.getChildAt(i).setRotationX(0);
                    multiple.getChildAt(i).setRotationY(0);
                }
        }
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    private void populateProps() {
        props.add("alpha");
        props.add("translationX");
        props.add("translationY");
        props.add("scaleX");
        props.add("scaleY");
        props.add("rotation");
        props.add("rotationX");
        props.add("rotationY");

        ArrayAdapter<String> props_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, props);
        spnr_prop.setAdapter(props_adapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
