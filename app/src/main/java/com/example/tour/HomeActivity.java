package com.example.tour;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tour.Adapter.MyToursAdapter;
import com.example.tour.Model.MyTours;
import com.example.tour.Model.Tours;
import com.example.tour.Model.Users;
import com.example.tour.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper tourCenterDB;
    TextView profileHead, username, email, phone, mytoursHead, mytoursEmpty;
    MyToursAdapter myToursAdapter;
    ListView mytoursView;
    Intent intent;
    String userID;

    static Users user;
    static ArrayList<MyTours> myToursList = new ArrayList<>();
    static MyTours myTours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Light.ttf");
        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto/Roboto-Thin.ttf");

        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this,"Roboto-Thin"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(s);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        intent = getIntent();

        userID = LoginActivity.userID;

        tourCenterDB = new DatabaseHelper(this);
        user = tourCenterDB.getUser(userID);
        myToursList = tourCenterDB.viewMytour(userID);


        View headerView = navigationView.getHeaderView(0);
        TextView navUser = headerView.findViewById(R.id.navUser);
        TextView navEmail = headerView.findViewById(R.id.navEmail);

        navUser.setText(user.getUserName());
        navEmail.setText(user.getUserEmail());

        profileHead = findViewById(R.id.profileHead);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        mytoursHead = findViewById(R.id.myToursHead);
        mytoursEmpty = findViewById(R.id.myToursEmpty);
        mytoursView = findViewById(R.id.myToursView);

        profileHead.setTypeface(robotoThin);
        email.setTypeface(robotoLight);
        phone.setTypeface(robotoLight);


        username.setText(user.getUserName());
        email.setText(user.getUserEmail());
        phone.setText(user.getUserPhone());

        mytoursHead.setTypeface(robotoThin);

        if(!myToursList.isEmpty()){
            mytoursEmpty.setVisibility(View.GONE);
            mytoursView.setVisibility(View.VISIBLE);

            myToursAdapter = new MyToursAdapter(getApplicationContext(), myToursList);

            mytoursView = findViewById(R.id.myToursView);
            mytoursView.setAdapter(myToursAdapter);

            mytoursView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    myTours = myToursList.get(i);
                    Intent intent = new Intent(HomeActivity.this, TourDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_us) {
            intent = new Intent(HomeActivity.this, AboutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("userID", user.getUserID());
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.navHome:
                break;
            case R.id.navTours:
                intent = new Intent(HomeActivity.this, ToursActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userID", user.getUserID());
                startActivity(intent);
                finish();
                break;
            case R.id.navLogout:
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
