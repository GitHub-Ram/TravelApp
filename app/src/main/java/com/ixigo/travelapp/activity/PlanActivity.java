package com.ixigo.travelapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ixigo.travelapp.R;
import com.ixigo.travelapp.utill.AutocompletingCity;
import com.ixigo.travelapp.utill.Constants;
import com.ixigo.travelapp.utill.HttpCalls;
import com.ixigo.travelapp.utill.OnTaskCompleted;
import com.ixigo.travelapp.utill.TripPlanStep1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity implements OnTaskCompleted {

    private CalendarView simpleCalendarView = null;
    private TextView goDate = null, returnDate = null;
    private TextView goDateText = null, returnDateText = null;
    private AppCompatAutoCompleteTextView source = null, destination = null, tripName = null;
    private RadioGroup radioGroup = null;
    private Button save = null;
    private LinearLayout returnDateLayout = null, calenderViewLayout = null;
    private boolean twoWay = true;
    private boolean clickedGo = true;
    private boolean textChangeInSource = true;
    List<AutocompletingCity> addressListSource = new ArrayList<AutocompletingCity>();
    List<AutocompletingCity> addressListDestination = new ArrayList<AutocompletingCity>();
    ArrayAdapter<String> adapterSource = null;
    ArrayAdapter<String> adapterDestination = null;
    public static TripPlanStep1 tripPlan = new TripPlanStep1();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        source = (AppCompatAutoCompleteTextView)findViewById(R.id.sourceTravel);
        destination = (AppCompatAutoCompleteTextView)findViewById(R.id.destinationTravel);
        tripName = (AppCompatAutoCompleteTextView)findViewById(R.id.tripName);
        goDate = (TextView)findViewById(R.id.goDate);
        goDateText = (TextView)findViewById(R.id.goDateText);
        returnDate = (TextView)findViewById(R.id.returnDate);
        returnDateText = (TextView)findViewById(R.id.returnDateText);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        returnDateLayout = (LinearLayout)findViewById(R.id.returnDateLayout);
        calenderViewLayout = (LinearLayout)findViewById(R.id.calenderLayout);
        save = (Button)findViewById(R.id.saveBtn);
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.RED); // set the red color for the dates of  focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the yellow color for the dates of an unfocused month
        simpleCalendarView.setSelectedWeekBackgroundColor(Color.RED); // red color for the selected week's background
        simpleCalendarView.setWeekSeparatorLineColor(Color.GREEN); // green color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                calenderViewLayout.setVisibility(View.GONE);
                if(clickedGo)
                    goDateText.setText(dayOfMonth + "/" + month + "/" + year);
                else
                    returnDateText.setText(dayOfMonth + "/" + month + "/" + year);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.onwayRadio) {
                    returnDate.setClickable(false);
                    returnDateLayout.setAlpha(0.5f);
                    twoWay = false;
                    returnDateText.setText("");
                }else if(checkedId == R.id.bothwayRadio){
                    returnDate.setClickable(true);
                    returnDateLayout.setAlpha(1.0f);
                    twoWay = true;
                }
            }
        });
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedGo = false;
                calenderViewLayout.setVisibility(View.VISIBLE);
            }
        });
        goDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedGo = true;
                calenderViewLayout.setVisibility(View.VISIBLE);
            }
        });
        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                textChangeInSource = true;
                if(!source.getText().toString().equals(""))
                    makeRequest(source.getText().toString());
            }
        });
        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                textChangeInSource = false;
                if(!destination.getText().toString().equals("")) {
                    makeRequest(destination.getText().toString());
                }
            }
        });
        String [] array = new String[1];
        array[0]="de";
        adapterSource = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, array);
        source.setAdapter(adapterSource);
        adapterDestination = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, array);
        destination.setAdapter(adapterDestination);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(source.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Source field can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }else
                if(destination.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Destination field can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }else
                if(goDateText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please select a date to Departure.",Toast.LENGTH_SHORT).show();
                    return;
                }else if(twoWay){
                    if(returnDateText.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Please select a date to return.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(tripName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please give name to Trip.",Toast.LENGTH_SHORT).show();
                    return;
                }
                AutocompletingCity sourceAddress = null;

                for(AutocompletingCity city : addressListSource)
                {
                    Log.i("SOutCity:", city.getText() + "id:"+city.get_id());
                    if(city.getText().equals(source.getText().toString())){
                        sourceAddress = city;
                        Log.i("SOutCityMAtch:", city.getText() + "id:"+city.get_id());
                        break;
                    }
                }
                if(sourceAddress.equals(null)){
                    Toast.makeText(getApplicationContext(),"Please type in correct Source name.",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    tripPlan.setSource(sourceAddress);
                }
                AutocompletingCity destinationAddress = null;
                for(AutocompletingCity city : addressListDestination)
                {
                    Log.i("DOutCity:", city.getText() + "id:"+city.get_id());
                    if(city.getText().equals(destination.getText().toString())){
                        destinationAddress = city;
                        Log.i("DOutCityMatch:", city.getText() + "id:"+city.get_id());
                        break;
                    }
                }
                if(destinationAddress.equals(null)){
                    Toast.makeText(getApplicationContext(),"Please type in correct Destination name.",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    tripPlan.setDestination(destinationAddress);
                }
                tripPlan.setFromDate(goDateText.getText().toString());
                tripPlan.setReturnDate(returnDateText.getText().toString());
                tripPlan.setTripName(tripName.getText().toString());
                Intent i = new Intent(getApplicationContext(),PlanActivityNext.class);
                i.putExtra("Destination",destination.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

    private void makeRequest(String city){
        HttpCalls lHttpCalls = new HttpCalls(this,this);
        lHttpCalls.getRequestForJsonArray(Constants.GET_METHOD, Constants.AUTO_COMPLETE_CITY+city, "City");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onTaskCompleted(JSONArray objectJ, String response, String lPurpose) {
        Log.i("response", response);
        Gson gson = new Gson();
        Type type = new TypeToken<List<AutocompletingCity>>(){}.getType();

        if(textChangeInSource){
            addressListSource = gson.fromJson(response, type);
            adapterSource.clear();
            for (AutocompletingCity map : addressListSource) {
                Log.i("SCity", map.getText() + "id:"+map.get_id());
                adapterSource.add(map.getText());
            }
        }else
        if(!textChangeInSource){
            addressListDestination = gson.fromJson(response, type);
            adapterDestination.clear();
            for (AutocompletingCity map : addressListDestination) {
                Log.i("DCity", map.getText() + "id:"+map.get_id());
                adapterDestination.add(map.getText());
            }
        }

    }
    @Override
    public void onTaskError(String response, String lPurpose) {
        Log.i("onTaskError1", response);
        if(textChangeInSource){
            adapterSource.clear();
        }else
        if(!textChangeInSource){
            adapterDestination.clear();
        }
        //Toast.makeText(this, "Error in server response", Toast.LENGTH_SHORT).show();
    }
}
