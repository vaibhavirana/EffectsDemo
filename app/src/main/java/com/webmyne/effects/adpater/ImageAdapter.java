package com.webmyne.effects.adpater;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.webmyne.effects.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
  //private Bitmap[] mBitmapArray;
  private ArrayList<Integer> mBitmapArray;
  private Context context;
  private int itemBackground;

  public ImageAdapter(Context c, ArrayList<Integer> bitmapArray) {
    context = c;
    mBitmapArray = bitmapArray;
    TypedArray a = context.obtainStyledAttributes(R.styleable.MyGallery);
   // itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
    a.recycle();
  }

  public int getCount() {
    return mBitmapArray.size();
  }

  public Object getItem(int position) {
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView = new ImageView(context);
    imageView.setImageResource(mBitmapArray.get(position));
    imageView.setLayoutParams(new Gallery.LayoutParams(150, 150));
    imageView.setPadding(4,4,4,4);
    imageView.setBackgroundResource(itemBackground);
    return imageView;
  }
}