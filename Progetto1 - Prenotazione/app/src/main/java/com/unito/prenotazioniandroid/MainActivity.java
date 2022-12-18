package com.unito.prenotazioniandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.unito.prenotazioniandroid.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.carousel1, R.drawable.carousel2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        if (carouselView.getCurrentItem() == 0)
            ((TextView) findViewById(R.id.carouselText)).setText(R.string.carousel_string_1);
        else if (carouselView.getCurrentItem() == 1)
            ((TextView) findViewById(R.id.carouselText)).setText(R.string.carousel_string_2);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
                    case 0:
                        goToLogin(carouselView.getChildAt(0));
                        break;
                    case 1:
                        goToBooking(carouselView.getChildAt(1));
                        break;
                    default:
                        break;
                }

            }
        });
        carouselView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0 && positionOffset <= 0.31)
                    ((TextView) findViewById(R.id.carouselText)).setText(R.string.carousel_string_1);
                else if (position == 1 || (position == 0 && positionOffset >= 0.69))
                    ((TextView) findViewById(R.id.carouselText)).setText(R.string.carousel_string_2);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        if (id == R.id.action_settings) {//Settings c'era nel costruttore, per ora lo lascio, poi si potrà eliminare
            return true;
        } else if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);//Non so se è proprio il modo giusto
            startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void goToBooking(View view) {
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
