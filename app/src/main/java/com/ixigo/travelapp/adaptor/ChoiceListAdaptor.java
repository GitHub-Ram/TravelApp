package com.ixigo.travelapp.adaptor;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.ixigo.travelapp.R;
import com.ixigo.travelapp.utill.ChoiceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by F49558B on 4/9/2017.
 */

public class ChoiceListAdaptor extends BaseAdapter implements View.OnClickListener {
    /*********** Declare Used Variables *********/
    private AppCompatActivity activity;
    private List<ChoiceModel> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ChoiceModel tempValues=null;
    int i=0;

    public ChoiceListAdaptor(AppCompatActivity parent, List<ChoiceModel> dataList,Resources resL) {
        activity = parent;
        data=dataList;
        res = resL;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onClick(View v) {
        Log.v("Adapter", "Checkbox clicked");
    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.card_choice_list, null);
            holder = new ViewHolder();
            holder.item = (CheckBox) vi.findViewById(R.id.choice_item);
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();
        if(data.size()<=0)
        {
            holder.item.setText("No Data");
        }
        else
        {
            tempValues=null;
            tempValues = ( ChoiceModel ) data.get( position );
            holder.item.setText( tempValues.getItem() );
            if(tempValues.isSelected()){
                holder.item.setChecked(true);
            }else{
                holder.item.setChecked(false);
            }
            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;
        OnItemClickListener(int position){
            mPosition = position;
        }
        @Override
        public void onClick(View arg0) {
            if(data.get( mPosition ).isSelected())
                data.get( mPosition ).setSelected(false);
            else
                data.get( mPosition ).setSelected(true);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder{

        public CheckBox item;

    }
}
