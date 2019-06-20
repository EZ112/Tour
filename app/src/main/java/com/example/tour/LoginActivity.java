package com.example.tour;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tour.R;


public class LoginActivity extends AppCompatActivity {

    final int MY_PERMISSION_REQUEST_SEND_SMS = 1;
    DatabaseHelper gameCenterDB;
    TextView appName, emailIn, passIn, loginBtn, regText, regLink;

    static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LoginTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gameCenterDB = new DatabaseHelper(this);

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");



        emailIn = findViewById(R.id.emailIn);
        passIn = findViewById(R.id.passIn);
        loginBtn = findViewById(R.id.loginBtn);
        regText = findViewById(R.id.regText);
        regLink = findViewById(R.id.regLink);

        SpannableString content = new SpannableString(regLink.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, regLink.getText().toString().length(), 0);
        regLink.setText(content);

        emailIn.setTypeface(robotoLight);
        passIn.setTypeface(robotoLight);
        loginBtn.setTypeface(robotoLight);

        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SEND_SMS);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty())
                    Toast.makeText(LoginActivity.this, "Username and Password Cannot be Empty", Toast.LENGTH_LONG).show();
                else{
                    String emailInStr = emailIn.getText().toString();
                    String passInStr = passIn.getText().toString();
                    userID = gameCenterDB.loginUser(emailInStr, passInStr);
                    if(!userID.equals("")){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        regLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private boolean isEmpty(){
        if(emailIn.getText().toString().equals("") || passIn.getText().toString().equals(""))
            return true;
        else
            return false;
    }
}
