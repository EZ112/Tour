package com.example.tour.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tour.Model.Tours;
import com.example.tour.R;

import java.util.List;

public class ToursAdapter extends BaseAdapter {
    private Context ctx;
    public List<Tours> toursList;

    public ToursAdapter(Context ctx, List<Tours> toursList) {
        this.ctx = ctx;
        this.toursList = toursList;
    }

    @Override
    public int getCount() {
        return toursList.size();
    }

    @Override
    public Object getItem(int i) {
        return toursList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(ctx, R.layout.tours_list, null);
        ImageView countryImg = v.findViewById(R.id.countryImg);
        TextView countryName = v.findViewById(R.id.countryName);

        Typeface robotoLight = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto/Roboto-Light.ttf");
        countryName.setTypeface(robotoLight);

        countryName.setText(toursList.get(i).getTourName()+"\n"+toursList.get(i).getTourDate());
        countryImg.setImageResource(toursList.get(i).getTourImages());

        return v;
    }
}
