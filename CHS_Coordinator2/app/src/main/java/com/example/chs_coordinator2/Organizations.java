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
 * Use the {@link Organizations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Organizations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ECsAdapter eCsAdapter;
    private List<Extracurricular> ecs;

    public Organizations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Organizations.
     */
    // TODO: Rename and change types and number of parameters
    public static Organizations newInstance(String param1, String param2) {
        Organizations fragment = new Organizations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ecs = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizations, container, false);
        extractData();
        recyclerView = view.findViewById(R.id.recyclerViewECs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eCsAdapter = new ECsAdapter(getActivity(),ecs);
        recyclerView.setAdapter(eCsAdapter);
        return view;
    }

    private void extractData() {
        String json;

        try{
            InputStream inputStream = getActivity().getAssets().open("ECs.json");
            int size = inputStream.available();
            byte [] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Extracurricular ec = new Extracurricular();
                ec.setTitle(object.getString("Name of Organization"));
                ec.setSponsor(object.getString("Sponsor"));
                ec.setEmail(object.getString("Sponsor Email"));
                ec.setImageURL(object.getString("ImageURL"));
                ec.setTwitterURL(object.getString("Twitter"));
                ec.setIgURl(object.getString("Instagram"));
                ecs.add(ec);


            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}