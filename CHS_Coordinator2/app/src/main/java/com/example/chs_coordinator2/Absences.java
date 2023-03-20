package com.example.chs_coordinator2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Absences#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Absences extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnSend;
    private EditText edtSubject, edtMessage;

    public Absences() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Absences.
     */
    // TODO: Rename and change types and number of parameters
    public static Absences newInstance(String param1, String param2) {
        Absences fragment = new Absences();
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
        View view = inflater.inflate(R.layout.fragment_absences, container, false);
        btnSend = view.findViewById(R.id.btnSendEmail);
        edtMessage = view.findViewById(R.id.edtTxtMessage);
        edtSubject = view.findViewById(R.id.edtTxtSubject);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtMessage.getText().toString();
                String subject = edtSubject.getText().toString();
                if(TextUtils.isEmpty(message) || TextUtils.isEmpty(subject))
                    Toast.makeText(getActivity(),"Please fill out all the forms", Toast.LENGTH_SHORT).show();
                else {
                    sendEmail(message, subject);
                }
            }

        });

        return view;
    }

    private void sendEmail(String message, String subject)
    {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto: melissa.olson@kellerisd.net")); //chs attendance email
         //adding extra info
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT,message);
        try {
            startActivity(Intent.createChooser(i, "Choose an Email Service"));

        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show(); //making sure no errors occur
        }

    }
}