package com.example.finalyearproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.models.AccidentsLocationList;

import java.util.List;

//public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

public class CustomAdapter extends BaseAdapter {
    Context context;
    String countryList[];
    int flags[];
    LayoutInflater inflter;
    List<AccidentsList> accidentsLists;
    List<AccidentsLocationList> accidentsLists2;

    // public CustomAdapter(Context applicationContext, String[] countryList) {
          public CustomAdapter(Context applicationContext, String[] countryList) {
        this.context = context;
        this.countryList = countryList;
        this.flags = flags;
        inflter = (LayoutInflater.from(applicationContext));
    }

 /*   public CustomAdapter(Context applicationContext, List<AccidentsList> accidentsLists) {
        this.context = context;
        this.accidentsLists = accidentsLists;
        inflter = (LayoutInflater.from(applicationContext));
    }
*/
    public CustomAdapter(Context applicationContext, List<AccidentsLocationList> accidentsLists2) {
        this.context = context;
        this.accidentsLists2 = accidentsLists2;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return accidentsLists2.size(); //countryList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.accidents_listview, null);
        TextView country = (TextView) view.findViewById(R.id.textView);
        country.setText(accidentsLists2.get(i).getUser_id());
        return view;
    }
}