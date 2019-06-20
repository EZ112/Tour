package com.example.tour.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tour.DatabaseHelper;
import com.example.tour.Model.MyTours;
import com.example.tour.R;

import java.util.ArrayList;
import java.util.Random;

public class MyToursAdapter extends BaseAdapter {
    private Context ctx;
    public ArrayList<MyTours> mytoursList;
    public DatabaseHelper tourDB;

    public MyToursAdapter(Context ctx, ArrayList<MyTours> mytoursList) {
        this.ctx = ctx;
        this.mytoursList = mytoursList;
        this.tourDB = new DatabaseHelper(ctx);
    }

    @Override
    public int getCount() {
        return mytoursList.size();
    }

    @Override
    public Object getItem(int i) {
        return mytoursList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(ctx, R.layout.mytour_list, null);
        ImageView countryImg = v.findViewById(R.id.countryImg);
        TextView countryName = v.findViewById(R.id.countryName);

        Typeface robotoLight = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Light.ttf");

        countryName.setTypeface(robotoLight);

        countryName.setText(mytoursList.get(i).getTourName()+"\n"+mytoursList.get(i).getTourDate());
        countryImg.setImageResource(mytoursList.get(i).getTourImages());

        return v;
    }
}
