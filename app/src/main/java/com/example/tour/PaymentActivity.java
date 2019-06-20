package com.example.tour;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tour.Model.Tours;
import com.example.tour.Model.Users;
import com.example.tour.R;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    DatabaseHelper tourCenterDB;
    ImageView tourImg;
    TextView paymentHead, tourName, tourDate, tourPrice, payBtn;
    EditText moneyIn;
    Users user;
    Tours tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        user = HomeActivity.user;
        tour = ToursActivity.tour;

        tourCenterDB = new DatabaseHelper(this);

        paymentHead = findViewById(R.id.paymentHead);

        paymentHead.setTypeface(robotoThin);

        tourImg = findViewById(R.id.tourImg);
        tourName = findViewById(R.id.tourName);
        tourDate = findViewById(R.id.tourDate);
        tourPrice = findViewById(R.id.tourPrice);
        payBtn = findViewById(R.id.payBtn);

        String moneyFormat = NumberFormat.getNumberInstance(Locale.US).format(tour.getTourPrice());

        tourImg.setImageResource(tour.getTourImages());
        tourName.setText(tour.getTourName());
        tourDate.setText(tour.getTourDate());
        tourPrice.setText("Rp " + moneyFormat + ".00");

        moneyIn = findViewById(R.id.moneyIn);

        moneyIn.setText("Rp ");
        Selection.setSelection(moneyIn.getText(), moneyIn.getText().length());

        moneyIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().startsWith("Rp ")){
                    moneyIn.setText("Rp ");
                    Selection.setSelection(moneyIn.getText(), moneyIn.getText().length());
                }
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = moneyIn.getText().toString();
                if(!num.equals("Rp ")){
                    long change;
                    long in = Long.parseLong(num.substring(3, num.length()));
                    long price = tour.getTourPrice();
                    change = in - price;

                    if(change >= 0){
                        tourCenterDB.addMytour(tour.getTourID(), user.getUserID());
                        sendMsg(user.getUserPhone(), "Your booking for \n "+tour.getTourName()+" "+tour.getTourDate()+" \n has been Confirmed \n Your change is Rp "+String.valueOf(change)+" \n Thank you for your purchased");
//                        Toast.makeText(PaymentActivity.this, "Your change is Rp "+String.valueOf(change),Toast.LENGTH_LONG).show();
                        ToursActivity.tour = null;
                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(PaymentActivity.this, "Insufficient Money", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(PaymentActivity.this, "You must input the money",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendMsg(String phoneNumText, String msgText){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumText, null, msgText, null, null);
    }
}
