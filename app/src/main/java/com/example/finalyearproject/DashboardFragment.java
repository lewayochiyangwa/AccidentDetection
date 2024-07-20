package com.example.finalyearproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView contributeCard;

    private CardView accidentDetectCard;
    public DashboardFragment() {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
    */
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

     // Find the CardView
     contributeCard = view.findViewById(R.id.contributeCard);
     accidentDetectCard = view.findViewById(R.id.accidentDetectCard);

     // Set the OnClickListener
     contributeCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             System.out.println("Call card clicked");
             Intent callIntent = new Intent(Intent.ACTION_CALL);
             callIntent.setData(Uri.parse("tel:+263783065525"));

             if (ActivityCompat.checkSelfPermission(requireContext(),
                     android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                 return;
             }
             startActivity(callIntent);
         }
     });

     accidentDetectCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             System.out.println("Accident card clicked");

             Intent intent = new Intent(getContext(), AccidentDetectionActivity.class);

             // Start the Activity
             startActivity(intent);
         }
     });

     return view;
 }


}