package com.example.finalyearproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private CardView logoutCard;
    private CardView mapsCard;
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
     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
     StrictMode.setThreadPolicy(policy);





     View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

     // Find the CardView
     contributeCard = view.findViewById(R.id.contributeCard);
     accidentDetectCard = view.findViewById(R.id.accidentDetectCard);
     logoutCard = view.findViewById(R.id.logoutCard);
     mapsCard = view.findViewById(R.id.mapsCard);

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


     mapsCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             System.out.println("Accident card clicked");

             if (getArguments() != null) {
                 String username = getArguments().getString("username");
                 int id = getArguments().getInt("id");
                 String role = getArguments().getString("role");

                 if(role.toLowerCase().equals("admin") ||
                         role.toLowerCase().equals("police") || role.toLowerCase().equals("ambulance")) {
                     Intent intent = new Intent(getContext(), MapsActivity.class);
                     startActivity(intent);

                 }else{
                     AlertDialog alertDialog3 = new AlertDialog.Builder(requireContext()).create();
                     alertDialog3.dismiss();
                     AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                     alertDialog.setTitle("Alert");
                     alertDialog.setMessage("No Roles To View The MAp");
                     alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                 }
                             });
                     alertDialog.show();
                 }
             }

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

     logoutCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(requireContext(), LoginActivity.class);
             startActivity(intent);
         }
     });

     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext());
     String usernamew = sharedPref.getString("username", "");
     int idw = sharedPref.getInt("id", -1);
     String rolew = sharedPref.getString("role", "");

     TextView usernameTextView = view.findViewById(R.id.textView2);
     usernameTextView.setText(usernamew);

     TextView roleTextView = view.findViewById(R.id.roleTextView);
     roleTextView.setText(rolew);
   /*  if (getArguments() != null) {
         String username = getArguments().getString("username");
         int id = getArguments().getInt("id");
         String role = getArguments().getString("role");

         TextView usernameTextView = view.findViewById(R.id.textView2);
         usernameTextView.setText(username);

         TextView roleTextView = view.findViewById(R.id.roleTextView);
         roleTextView.setText(role);


         // Use the values as needed
     }*/
     return view;
 }


}