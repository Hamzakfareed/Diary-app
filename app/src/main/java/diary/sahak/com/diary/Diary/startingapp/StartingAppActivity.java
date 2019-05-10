package diary.sahak.com.diary.Diary.startingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import diary.sahak.com.diary.Diary.DiaryActivity;
import diary.sahak.com.diary.Diary.R;


public class StartingAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_app);

        ImageView image = findViewById(R.id.roller);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        image.startAnimation(animation);

        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);

                    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                    boolean first_visit = preferences.getBoolean("READ", false);
                    if(first_visit) {
                    Intent intent = new Intent(getApplicationContext(),DiaryActivity.class);
                    startActivity(intent);
                    finish();}else if(!first_visit) {
                        Intent intent = new Intent(getApplicationContext(),OnBoardingScreenActivity.class);
                        preferences.edit().putBoolean("READ",true).commit();
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        thread.start();

    }

}
