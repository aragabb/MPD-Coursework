//Ahmed Ragab Badawy- S1804193
package com.example.infoquakes.ui.filter;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.infoquakes.R;
import com.example.infoquakes.helpers.RssFeedModel;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.infoquakes.ui.timeline.TimelineFragment.mFeedModelList;

//In this class, we filter the result and show nearest to mauritius,largest and deepest earthquake.

public class FilterFragment extends Fragment implements Filterable {

    private FilterViewModel mViewModel;
    private TextView fromMauritiusTheNearestTxt;
    private TextView largestMagnitudeEarthquakeTxt;
    private TextView deepestEarthquakeTxt;
    private Button chooseByDateBtn;
    private String startdateString,enddateString;
    private final LatLng mauritiusLatLng = new LatLng(-20.2005136,56.5541215);

    List<String> alldates;

    public List<RssFeedModel> mRssFeedModels;
    private List<RssFeedModel> datafilteredlist;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);

        View root = inflater.inflate(R.layout.fragment_filter, container, false);

        fromMauritiusTheNearestTxt = root.findViewById(R.id.from_mauritius_the_nearest_txt);
        largestMagnitudeEarthquakeTxt = root.findViewById(R.id.largest_magnitude_earthquake_txt);
        deepestEarthquakeTxt = root.findViewById(R.id.deepest_earthquake_txt);
        chooseByDateBtn = root.findViewById(R.id.choose_by_data_btn);
        alldates=new ArrayList<>();

        mRssFeedModels = mFeedModelList;
        setNearestMagnitudeDeepest();

        chooseByDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mydialog1=new AlertDialog.Builder(getContext());
                LayoutInflater inflater1=LayoutInflater.from(getContext());
                View myview1=inflater1.inflate(R.layout.custom_date_range_filter,null);
                mydialog1.setView(myview1);
                final AlertDialog dialog1=mydialog1.create();
                dialog1.show();
                final TextView startdatetxt=myview1.findViewById(R.id.start_date_txt);
                final TextView enddatetxt=myview1.findViewById(R.id.end_date_txt);


                startdatetxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        final int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                                        c.set(year,monthOfYear,dayOfMonth);
                                        startdateString = format.format(c.getTime());
                                        startdatetxt.setText(startdateString);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                enddatetxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        final int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                                        c.set(year,monthOfYear,dayOfMonth);
                                        enddateString = format.format(c.getTime());
                                        enddatetxt.setText(enddateString);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();


                    }
                });

                Button datefilterbtn=myview1.findViewById(R.id.filterbtn);
                datefilterbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(startdateString))
                        {
                            startdatetxt.setError("select date");
                        }
                        if (TextUtils.isEmpty(enddateString))
                        {
                            enddatetxt.setError("select ");
                        }
                        else {

                            //setNearestMagnitudeDeepest();
                            alldates = getDates(startdateString, enddateString);
                            for(String date:alldates)
                            {
                                System.out.println(date);
                            }
                            getFilter().filter(startdateString);
                            dialog1.dismiss();
                        }
                    }
                });
            }
        });
        return root;
    }

    // mathod used to set
    //from Mauritius The Nearest in Textbox
    //largest Magnitude Earthquake in Textbox
    //deepest Earthquake in Textbox
    public void setNearestMagnitudeDeepest(){

        String fromMauritiusTheNearest = "";
        String largestMagnitudeEarthquake = "";
        String largestMagnitudeEarthquakeLocName = "";
        String deepestEarthquakeLocName = "";

        for (int i = 0 ; i < mRssFeedModels.size() ; i++) {

            if(Double.parseDouble(mRssFeedModels.get(i).latitude) == findNearestDoubleInList()){

                fromMauritiusTheNearest = mRssFeedModels.get(i).getLocation();
                System.out.println(mRssFeedModels.get(i).latitude+"------------- "+findNearestDoubleInList()+"  "+mRssFeedModels.get(i).getLocation());
            }
        }

        double maxMagnitude = Double.MIN_VALUE;
        for(int i=0; i<mRssFeedModels.size(); i++){
            if(Double.parseDouble(mRssFeedModels.get(i).getMagnitude()) > maxMagnitude){
                maxMagnitude = Double.parseDouble(mRssFeedModels.get(i).getMagnitude());
                largestMagnitudeEarthquakeLocName = mRssFeedModels.get(i).getLocation();
            }
        }

        String maxDepthStr = null;
        int maxDepth = Integer.MIN_VALUE;
        for(int i=0; i<mRssFeedModels.size(); i++){
            if(Integer.parseInt(mRssFeedModels.get(i).getDepth().replaceAll("[^0-9]", "")) > maxDepth){
                maxDepth = Integer.parseInt(mRssFeedModels.get(i).getDepth().replaceAll("[^0-9]", ""));
                maxDepthStr = mRssFeedModels.get(i).getDepth();
                deepestEarthquakeLocName = mRssFeedModels.get(i).getLocation();
            }
        }

        largestMagnitudeEarthquake = String.valueOf(maxMagnitude);

        fromMauritiusTheNearestTxt.setText(fromMauritiusTheNearest);
        largestMagnitudeEarthquakeTxt.setText(largestMagnitudeEarthquake+" in "+largestMagnitudeEarthquakeLocName);
        deepestEarthquakeTxt.setText(maxDepthStr+" in "+deepestEarthquakeLocName);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
        // TODO: Use the ViewModel
    }

    //method use to find nearest location from mauritius
    private Double findNearestDoubleInList() {
        Double answer = Double.parseDouble(mRssFeedModels.get(0).latitude);
        Double current = Double.MAX_VALUE;
        for (int i = 0 ; i < mRssFeedModels.size() ; i++) {
            if (Math.abs(Double.parseDouble(mRssFeedModels.get(i).latitude) - mauritiusLatLng.latitude) < current) {
                answer = Double.parseDouble(mRssFeedModels.get(i).latitude);
                current = Math.abs(answer - mauritiusLatLng.latitude);
            }
        }
        return answer;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    datafilteredlist = mRssFeedModels;
                } else {
                    List<RssFeedModel> filteredList = new ArrayList<>();
                    for (int i=0;i<alldates.size();i++)
                    {
                        charString=alldates.get(i);
                        for (RssFeedModel row : mRssFeedModels) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getDescription().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                                System.out.println("matched");
                            }
                        }

                    }
                    datafilteredlist = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = datafilteredlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                mRssFeedModels = (ArrayList<RssFeedModel>) results.values;
                setNearestMagnitudeDeepest();
                if(fromMauritiusTheNearestTxt.getText().toString().isEmpty()){
                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                    builder.setMessage("No record found on this date")
                            .setCancelable(false)
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    mRssFeedModels = mFeedModelList;
                                    setNearestMagnitudeDeepest();
                                }
                            });
                    final android.app.AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        };
    }

    private static List<String> getDates(String dateString1, String dateString2)
    {
        ArrayList<String> dates = new ArrayList<String>();
        SimpleDateFormat df1 = new SimpleDateFormat("dd MMM yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            //Date date=cal1.getTime();
            //dates.add(cal1.getTime());
            dates.add(df1.format(cal1.getTime()));
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
