package com.example.tour;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tour.Adapter.ToursAdapter;
import com.example.tour.Model.Tours;
import com.example.tour.Model.MyTours;
import com.example.tour.Model.Users;
import com.example.tour.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ToursActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseHelper tourDB;
    ListView toursView;
    ToursAdapter toursAdapter;
    ArrayList<Tours> toursList = new ArrayList<>();
    ArrayList<Integer> countryImg = new ArrayList<>();
    Intent intent;

    Users user;
    static Tours tour;
    ArrayList<MyTours> myToursList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        SpannableString s = new SpannableString(getString(R.string.title_tours_list));
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

        user = HomeActivity.user;
        myToursList = HomeActivity.myToursList;

        View headerView = navigationView.getHeaderView(0);
        TextView navUser = headerView.findViewById(R.id.navUser);
        TextView navEmail = headerView.findViewById(R.id.navEmail);

        navUser.setText(user.getUserName());
        navEmail.setText(user.getUserEmail());

        tourDB = new DatabaseHelper(this);
        toursList = tourDB.viewtour();
        toursView = findViewById(R.id.toursView);

        if(toursList.isEmpty()){
            countryImg.add(R.drawable.canada);
            countryImg.add(R.drawable.japan);
            countryImg.add(R.drawable.korea);
            countryImg.add(R.drawable.taiwan);
            countryImg.add(R.drawable.austria);
            countryImg.add(R.drawable.singapore);
            countryImg.add(R.drawable.hongkong);
            countryImg.add(R.drawable.malaysia);
            countryImg.add(R.drawable.france);
            countryImg.add(R.drawable.greek);

            RequestQueue reqQue = Volley.newRequestQueue(this);
            final String jsonUrl = "https://api.myjson.com/bins/jmwrx";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String country = jsonObject.getString("country");
                            String date = jsonObject.getString("date");
                            long price = jsonObject.getLong("price");
                            JSONArray destJson = jsonObject.getJSONArray("dest");
                            Log.i("Tour ID : ", id);
                            for (int j = 0; j < destJson.length(); j++){
                                tourDB.addTourDest(destJson.get(j).toString(), id);
                                Log.i("Dest :",destJson.get(j).toString());
                            }
                            tourDB.addtour(id, country, date, price, countryImg.get(i));
                            toursList = tourDB.viewtour();
                            toursAdapter = new ToursAdapter(getApplicationContext(), toursList);
                            toursView.setAdapter(toursAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            reqQue.add(jsonArrayRequest);

        }
        else{
            toursAdapter = new ToursAdapter(getApplicationContext(), toursList);
            toursView.setAdapter(toursAdapter);
        }

        toursView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean isPurchased = false;

                for(MyTours mytour : myToursList){
                    if(mytour.getTourID().equals(toursList.get(i).getTourID())){
                        isPurchased = true;
                    }
                }
                if(isPurchased){
                    Toast.makeText(getApplicationContext(), "You already book this tour", Toast.LENGTH_LONG).show();
                }
                else{
                    tour = toursList.get(i);
                    Intent intent = new Intent(ToursActivity.this, TourDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

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
            intent = new Intent(ToursActivity.this, AboutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.navHome:
                tour = null;
                intent = new Intent(ToursActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.navTours:
                break;
            case R.id.navLogout:
                intent = new Intent(ToursActivity.this, LoginActivity.class);
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
