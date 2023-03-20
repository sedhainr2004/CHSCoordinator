package com.example.chs_coordinator2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sports extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SportsAdapter sportsAdapter;
    private List<Sport> sports;

    public Sports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sports.
     */
    // TODO: Rename and change types and number of parameters
    public static Sports newInstance(String param1, String param2) {
        Sports fragment = new Sports();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sports = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports, container, false);
        extractData();
        recyclerView = view.findViewById(R.id.recyclerViewSports);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        sportsAdapter = new SportsAdapter(getActivity(),sports);
        recyclerView.setAdapter(sportsAdapter);

        return view;
    }

    private void extractData(){
        String json;

        try{
            InputStream inputStream = getActivity().getAssets().open("sports.json");
            int size = inputStream.available();
            byte [] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Sport sport = new Sport();
                sport.setTitle(object.getString("Title"));
                sport.setCoach(object.getString("Coach"));
                sport.setEmail(object.getString("Email"));
                sport.setImageURL(object.getString("ImageURL"));
                sport.setTwitterURL(object.getString("Twitter"));
                sport.setMaxPrepsURL(object.getString("MaxPreps"));
                sports.add(sport);

            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}