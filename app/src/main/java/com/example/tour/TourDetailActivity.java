package com.example.tour;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tour.Adapter.ToursDestAdapter;
import com.example.tour.Model.MyTours;
import com.example.tour.Model.TourDest;
import com.example.tour.Model.Tours;
import com.example.tour.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TourDetailActivity extends AppCompatActivity {

    DatabaseHelper tourDB;
    ImageView tourImg;
    TextView tourName, tourDate, priceLabel, tourPrice, buyBtn;

    Tours tour;
    MyTours myTours;

    ArrayList<TourDest> tourDestList = new ArrayList<>();
    ToursDestAdapter toursDestAdapter;
    ListView tourDestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");

        SpannableString s = new SpannableString(getString(R.string.title_tours_detail));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        tourDB = new DatabaseHelper(this);

        if(ToursActivity.tour != null)
            tour = ToursActivity.tour;
        else{
            myTours = HomeActivity.myTours;
            tour = tourDB.getTourByID(myTours.getTourID());
        }

        tourDestList = tourDB.getTourDest(tour.getTourID());
        toursDestAdapter = new ToursDestAdapter(getApplicationContext(), tourDestList);
        tourDestView = findViewById(R.id.tourDestView);
        tourDestView.setAdapter(toursDestAdapter);


        tourImg = findViewById(R.id.tourImg);
        tourName = findViewById(R.id.tourName);
        tourDate = findViewById(R.id.tourDate);
        priceLabel = findViewById(R.id.priceLabel);
        tourPrice = findViewById(R.id.tourPrice);
        buyBtn = findViewById(R.id.buyBtn);

        priceLabel.setTypeface(robotoLight);

        String moneyFormat = NumberFormat.getNumberInstance(Locale.US).format(tour.getTourPrice());

        tourImg.setImageResource(tour.getTourImages());
        tourName.setText(tour.getTourName());
        tourDate.setText(tour.getTourDate());
        tourPrice.setText("Rp " + moneyFormat + ".00");

        if(ToursActivity.tour == null)
            buyBtn.setVisibility(View.GONE);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TourDetailActivity.this, PaymentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
