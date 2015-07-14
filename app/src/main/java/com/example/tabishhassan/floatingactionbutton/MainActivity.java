package com.example.tabishhassan.floatingactionbutton;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.zip.Inflater;


public class MainActivity extends Activity {
    LinearLayout menu_layout, back_layout;
    ObjectAnimator animy;
    static int IS_DOWN = 0,IS_HALF_DOWN = 0;
    ImageButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        LayoutInflater minInflater = getLayoutInflater();
        View ActionBarCustomView = minInflater.inflate(R.layout.custom_action_bar,null);
        actionBar.setCustomView(ActionBarCustomView);
        actionBar.setElevation(10);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        add_button = (ImageButton)findViewById(R.id.add_button);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        final ImageView image = (ImageView)findViewById(R.id.imageView);
        menu_layout = (LinearLayout)findViewById(R.id.layout_menu);
        menu_layout.setVisibility(View.INVISIBLE);
        menu_layout.setLayoutParams(new LinearLayout.LayoutParams(8 * width / 10, height / 2));
        back_layout = (LinearLayout)findViewById(R.id.background_layout);
        back_layout.setGravity(Gravity.CENTER_HORIZONTAL);
        add_button.setOutlineProvider(new ViewOutlineProvider() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getOutline(View view, Outline outline) {
                int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
                outline.setOval(0, 0, diameter, diameter);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,image,"cardImage");
                Intent mIntent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(mIntent, opt.toBundle());
            }
        });
        add_button.setClipToOutline(true);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_DOWN == 0) {
                    animy = ObjectAnimator.ofFloat(menu_layout, "y", -900f, -500f);
                    animy.setDuration(1200);
                    animy.start();
                    menu_layout.setVisibility(View.VISIBLE);
                    IS_DOWN=1;
                    IS_HALF_DOWN=1;
                    back_layout.setAlpha(1f);
                }
                else {

                }
            }
        });
        menu_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animy = ObjectAnimator.ofFloat(menu_layout, "y", 0f);
                animy.setDuration(1200);
                animy.start();
                IS_HALF_DOWN=0;
            }
        });
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IS_DOWN==1&&IS_HALF_DOWN==1) {
                    animy = ObjectAnimator.ofFloat(menu_layout, "y", -500f, -900f);
                    animy.setDuration(1200);
                    animy.start();
                    IS_DOWN=0;
                    IS_HALF_DOWN=0;
                }
                else if (IS_DOWN==1)
                {
                    animy = ObjectAnimator.ofFloat(menu_layout, "y", -0f, -1500f);
                    animy.setDuration(1200);
                    animy.start();
                    IS_DOWN=0;
                    IS_HALF_DOWN=0;
                }
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
