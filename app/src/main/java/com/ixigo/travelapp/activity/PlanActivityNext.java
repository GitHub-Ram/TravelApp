package com.ixigo.travelapp.activity;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ixigo.travelapp.R;
import com.ixigo.travelapp.adaptor.ChoiceListAdaptor;
import com.ixigo.travelapp.adaptor.ChoiceResultAdaptor;
import com.ixigo.travelapp.utill.AutocompletingCity;
import com.ixigo.travelapp.utill.ChoiceModel;
import com.ixigo.travelapp.utill.Constants;
import com.ixigo.travelapp.utill.Hotels;
import com.ixigo.travelapp.utill.HttpCalls;
import com.ixigo.travelapp.utill.OnTaskCompleted;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class PlanActivityNext extends AppCompatActivity implements OnTaskCompleted {

    private ProgressDialog progressDialog = null;
    private ListView choiceList = null;
    private RecyclerView resultChoiceList = null;
    private Button searchList = null;
    List<Hotels> hList = new ArrayList<Hotels>();
    List<ChoiceModel> listChoice = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_next);
        setTitle("Find Stuffs at "+(PlanActivity.tripPlan.getDestination().getText().toUpperCase()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        dropChoiceList();
        progressDialog = new ProgressDialog(this);
        searchList = (Button) findViewById(R.id.searchChoiceBtn);
        choiceList = (ListView) findViewById(R.id.choiceList);
        Resources res =getResources();
        ChoiceListAdaptor adapter = new ChoiceListAdaptor( this, listChoice,res );
        choiceList.setAdapter( adapter );
        searchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SHotelList2",hList.size()+"");
                //mAdapter.notifyDataSetChanged();
                String id = PlanActivity.tripPlan.getDestination().get_id();
                int limit = 4;
                int skip = 1;
                String items = "";
                for(ChoiceModel cm : listChoice){
                    if(!items.equals("")&&cm.isSelected())
                        items = items +",";
                    if(cm.isSelected()){
                        items = items + cm.getItem();
                    }
                }
                if(items.equals("")){
                    Toast.makeText(getApplicationContext(),"Please select few of your choice.",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    makeRequest(id,limit,skip,items);
                }
            }
        });
        resultChoiceList = (RecyclerView) findViewById(R.id.choiceResultList);
        resultChoiceList.setHasFixedSize(true);

    }
    private void makeRequest(String id,int limit,int skip, String item){
        HttpCalls lHttpCalls = new HttpCalls(this,this);
        lHttpCalls.getRequestForJsonObject(Constants.GET_METHOD, Constants.CITY_RESOURCE(id,item,skip,limit), "Attraction");
    }
    private void dropChoiceList(){
        ChoiceModel cm = new ChoiceModel();
        cm.setItem("Hotel");
        cm.setSelected(true);
        listChoice.add(cm);
        cm = new ChoiceModel();
        cm.setItem("Bar");
        cm.setSelected(false);
        listChoice.add(cm);
        cm = new ChoiceModel();
        cm.setItem("Disco");
        cm.setSelected(false);
        listChoice.add(cm);
        cm = new ChoiceModel();
        cm.setItem("Resort");
        cm.setSelected(false);
        listChoice.add(cm);
        cm = new ChoiceModel();
        cm.setItem("Movie");
        cm.setSelected(false);
        listChoice.add(cm);
    }
    @Override
    public void onTaskCompleted(JSONArray objecjJ, String response, String lPurpose) {
        JSONObject lJSONObject = null;
        try {
            lJSONObject = new JSONObject(response.toString());
            String hotels = lJSONObject.getString("data");
            Log.i("HOTELSOBJ:",hotels);
            lJSONObject = new JSONObject(hotels);
            String list = lJSONObject.getString("Hotel");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Hotels>>(){}.getType();
            hList = gson.fromJson(list, type);
            for (Hotels map : hList) {
                Log.i("SHotel", map.getName() + "id:"+map.getAddress());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    resultChoiceList.setLayoutManager(mLayoutManager);
                    mAdapter = new ChoiceResultAdaptor(hList);
                    resultChoiceList.setAdapter(mAdapter);
                    Log.i("SHotelList1",hList.size()+"");
                }
            });

        } catch (JSONException e) {
            Log.e("Hotel Error", e.toString());
        }
    }

    @Override
    public void onTaskError(String response, String lPurpose) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
