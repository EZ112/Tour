package com.example.tour;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.LruCache;

public class TypefaceSpan extends MetricAffectingSpan {
    private  static LruCache<String, Typeface> sTypefaceChace = new LruCache<>(12);
    private Typeface mTypeface;

    public TypefaceSpan(Context context, String typeFaceName){
        mTypeface = sTypefaceChace.get(typeFaceName);

        if(mTypeface == null){
            mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto/"+typeFaceName+".ttf");
            sTypefaceChace.put(typeFaceName, mTypeface);
        }
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        textPaint.setTypeface(mTypeface);
        textPaint.setFlags(textPaint.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setTypeface(mTypeface);
        textPaint.setFlags(textPaint.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}
