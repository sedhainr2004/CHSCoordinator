package com.example.chs_coordinator2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Extracurriculars#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Extracurriculars extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnOrganizations, btnSports;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Extracurriculars() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Extracurriculars.
     */
    // TODO: Rename and change types and number of parameters
    public static Extracurriculars newInstance(String param1, String param2) {
        Extracurriculars fragment = new Extracurriculars();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_extracurriculars, container, false);
        btnOrganizations = view.findViewById(R.id.btnClubs);
        btnOrganizations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Organizations organizations = new Organizations();

                // Replace current fragment with Absences fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, organizations);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();;
            }
        });
        btnSports = view.findViewById(R.id.btnSports);
        btnSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sports sports = new Sports();
                // Replace current fragment with Absences fragment
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, sports);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();;

            }
        });
        return view;
    }
}