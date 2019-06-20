package com.example.tour;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tour.R;

import java.util.Random;

public class RegistActivity extends AppCompatActivity {

    DatabaseHelper gameCenterDB;
    EditText nameIn, emaliIn, phoneIn, passIn, passConfIn;
    TextView registBtn;

    String userName, userEmail, userPassword, userPasswordConf, userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        gameCenterDB = new DatabaseHelper(this);

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");

        SpannableString s = new SpannableString(getString(R.string.title_register));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        nameIn = findViewById(R.id.nameIn);
        emaliIn = findViewById(R.id.emailIn);
        phoneIn = findViewById(R.id.phoneIn);
        passIn = findViewById(R.id.passIn);
        passConfIn = findViewById(R.id.passConfIn);
        registBtn = findViewById(R.id.registBtn);

        nameIn.setTypeface(robotoLight);
        emaliIn.setTypeface(robotoLight);
        phoneIn.setTypeface(robotoLight);
        passIn.setTypeface(robotoLight);
        passConfIn.setTypeface(robotoLight);

        registBtn.setTypeface(robotoLight);


        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = nameIn.getText().toString();
                userEmail = emaliIn.getText().toString();
                userPassword = passIn.getText().toString();
                userPasswordConf = passConfIn.getText().toString();
                userPhone = phoneIn.getText().toString();

                if(validation()){
                    gameCenterDB.addUser(genUserID(), userName, userEmail, userPassword, userPhone);
//                    Log.i("Users", json);

                    Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    private String genUserID(){
        Random rand = new Random();
        String num1 = String.valueOf((int)rand.nextInt(9));
        String num2 = String.valueOf((int)rand.nextInt(9));
        String num3 = String.valueOf((int)rand.nextInt(9));

        return "US" + num1 + num2 + num3;
    }

    private boolean validation(){
        if(userName.equals("") || userEmail.equals("") || userPhone.equals("") || userPassword.equals("") || userPasswordConf.equals("")){
            Toast.makeText(RegistActivity.this, "All component must be filled",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userName.length() < 3 || userName.length() > 25){
            Toast.makeText(RegistActivity.this, "Name must between 3 and 25 characters",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!userEmail.matches("[\\w\\d]+@\\w+(\\.[\\w]{2,4})+")){
            Toast.makeText(RegistActivity.this, "Email must be filled with a valid email format",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userPhone.length() < 10 || userPhone.length() > 12){
            Toast.makeText(RegistActivity.this, "Phone Number must between 10 and 12 characters",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (userPassword.length() <= 6){
            Toast.makeText(RegistActivity.this, "Password must be more than 6 characters",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!userPasswordConf.equals(userPassword)){
            Toast.makeText(RegistActivity.this, "Confirmation Password must be the same with Password",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
