package com.webmyne.effects.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.webmyne.effects.R;

import java.util.ArrayList;

/**
 * Created by vaibhavirana on 16-09-2016.
 */
public class FilterAdapter extends
        RecyclerView.Adapter<FilterAdapter.RecyclerViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<String> arrayList;
    private ArrayList<Integer> imgarrayList;
    private Context context;

    public FilterAdapter(Context context,
                                ArrayList<String> arrayList,ArrayList<Integer> imgarrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.imgarrayList = imgarrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder

        // setting title
        mainHolder.title.setText(arrayList.get(position));

        mainHolder.imageview.setImageResource(imgarrayList.get(position));


    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_filter, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        // View holder for gridview recycler view as we used in listview
        public TextView title;
        public ImageView imageview;


        public RecyclerViewHolder(View view) {
            super(view);
            // Find all views ids

            this.title = (TextView) view
                    .findViewById(R.id.title);
            this.imageview = (ImageView) view
                    .findViewById(R.id.image);


        }


    }
}
