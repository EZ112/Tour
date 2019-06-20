package com.example.tour.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tour.Model.TourDest;
import com.example.tour.R;

import java.util.List;

public class ToursDestAdapter extends BaseAdapter {
    private Context ctx;
    private List<TourDest> tourDestList;

    public ToursDestAdapter(Context ctx, List<TourDest> tourDestList) {
        this.ctx = ctx;
        this.tourDestList = tourDestList;
    }

    @Override
    public int getCount() {
        return tourDestList.size();
    }

    @Override
    public Object getItem(int i) {
        return tourDestList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(ctx, R.layout.tours_dest_list, null);
        TextView tourDest = v.findViewById(R.id.tourDest);

        tourDest.setText("Day "+(i+1)+"\n"+tourDestList.get(i).getTourDest());
        return v;
    }
}
