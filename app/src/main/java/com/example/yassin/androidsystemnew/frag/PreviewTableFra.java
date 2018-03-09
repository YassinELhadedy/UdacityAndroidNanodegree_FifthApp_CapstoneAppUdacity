package com.example.yassin.androidsystemnew.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yassin.androidsystemnew.R;

import static com.example.yassin.androidsystemnew.WeekListActivity.SUBJECT_NAME;


public class PreviewTableFra extends Fragment {
    public static final String Activity ="WeekList";

    public PreviewTableFra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_scan_list_details, container, false);
        if(getArguments().getString(Activity).equals("WeekList")){
                    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        }else {

        }

        return view; }


}
