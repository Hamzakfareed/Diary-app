package diary.sahak.com.diary.Diary.startingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import diary.sahak.com.diary.Diary.DiaryActivity;
import diary.sahak.com.diary.Diary.R;


/**
 * Created by Aladdin on 12-Jan-18.
 */

public class OnBoardingScreenActivity extends AppCompatActivity {

    private ViewPager pager;
    private LinearLayout dotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dots;
    private Button backButton;
    private Button nextButton;
    private Button finishButton;
    private int currentPage;


    ViewPager.OnPageChangeListener  listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;


            addDotsIndicator(position);

            if (currentPage == 0) {
                nextButton.setEnabled(true);
                backButton.setEnabled(false);
                finishButton.setEnabled(false);
                finishButton.setVisibility(View.INVISIBLE);
                backButton.setVisibility(View.INVISIBLE);
                nextButton.setText("Next");
                backButton.setText("");

            }else if(currentPage ==dots.length-1) {
                backButton.setEnabled(true);
                nextButton.setEnabled(false);
                finishButton.setEnabled(true);
                finishButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.INVISIBLE);

                backButton.setVisibility(View.VISIBLE);
                finishButton.setText("Finish");
                backButton.setText("Back");
            }else {
                nextButton.setEnabled(true);
                backButton.setEnabled(true);
                backButton.setVisibility(View.VISIBLE);
                finishButton.setEnabled(false);
                finishButton.setVisibility(View.INVISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                nextButton.setText("Next");
                backButton.setText("Back");

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boarding_screen);
        sliderAdapter = new SliderAdapter(this);
        pager = (ViewPager) findViewById(R.id.slide_view_pager);
        pager.setAdapter(sliderAdapter);
        dotLayout =(LinearLayout)findViewById(R.id.dot_layout);
        addDotsIndicator(currentPage);

        backButton = (Button)findViewById(R.id.btnPrevious);
        nextButton = (Button)findViewById(R.id.btnNext);
        finishButton = (Button)findViewById(R.id.btnFinish);
        
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(currentPage-1);

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(currentPage+1);
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OnBoardingScreenActivity.this,DiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pager.addOnPageChangeListener(listener);
    }

    public void addDotsIndicator(int position) {
        dots = new TextView[1];
        currentPage = position;

        dotLayout.removeAllViews();

        for(int i =0 ;i < dots.length;i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorTransparent));
            dotLayout.addView(dots[i]);
        }

        if(dots.length >0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

}
