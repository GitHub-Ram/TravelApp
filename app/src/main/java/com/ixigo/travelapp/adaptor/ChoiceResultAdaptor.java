package com.ixigo.travelapp.adaptor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ixigo.travelapp.R;
import com.ixigo.travelapp.utill.Hotels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by F49558B on 4/9/2017.
 */

public class ChoiceResultAdaptor extends RecyclerView.Adapter<ChoiceResultAdaptor.ViewHolder>  {

    private List<Hotels> mDataset;
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public ChoiceResultAdaptor(List<Hotels> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ChoiceResultAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("SizeREC3",mDataset.size()+"");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_result_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChoiceResultAdaptor.ViewHolder holder, int position) {
        Log.i("SizeREC1",mDataset.size()+"");
        final Hotels hotel = mDataset.get(position);
        holder.txtHeader.setText(hotel.getName());
//        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(hotel.getName());
//            }
//        });
        holder.txtFooter.setText( hotel.getAddress());
    }

    @Override
    public int getItemCount() {
        Log.i("SizeREC",mDataset.size()+"");
        return mDataset.size();
    }
}
