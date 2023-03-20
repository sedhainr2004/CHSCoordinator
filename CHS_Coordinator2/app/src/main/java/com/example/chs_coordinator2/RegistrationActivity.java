package com.example.chs_coordinator2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    public static String userTypeFinal;
    private final String[] USER_TYPES =  {"Parent", "Teacher", "Student"};
    ProgressDialog pd;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private Button btnRegister;
    private FloatingActionButton btnBack;

    private EditText firstName, lastName, password, email;
    private DatabaseReference mRootRef;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();

        firstName = findViewById(R.id.edTxtFirstName);
        lastName = findViewById(R.id.edTxtLastName);
        password = findViewById(R.id.edTxtPassword);
        email = findViewById(R.id.edtTextEmail);
        btnBack = findViewById(R.id.floatingActionBtnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

        btnRegister = findViewById(R.id.registrationButton);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_firstName = firstName.getText().toString();
                String txt_lastName = lastName.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                //making sure that all the credentials are filled out and the email is in the @kellerisd domain and making sure the password is strong
                if (TextUtils.isEmpty(txt_firstName) || TextUtils.isEmpty(txt_lastName) ||
                        TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)
                ) {
                    Toast.makeText(RegistrationActivity.this, "Make sure all the fields are filled out!", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length() < 6)
                {
                    Toast.makeText(RegistrationActivity.this,"Choose a new password its weak:(", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(userTypeFinal,txt_firstName,txt_lastName,txt_email,txt_password);
                }
            }
        });


        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item, USER_TYPES);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userTypeFinal = parent.getItemAtPosition(position).toString();
            }
        });
    }

    private void registerUser(String userType, String firstName, String lastName, String email, String password) {
        pd.setMessage("Please wait!");
        pd.show();

        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                User user = new User(userType, firstName, lastName, email, password, "default", auth.getCurrentUser().getUid());
                mRootRef.child("Users").child(auth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            userTypeFinal = userType;
                            pd.dismiss();
                            Toast.makeText(RegistrationActivity.this, "User is registered!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else {Toast.makeText(RegistrationActivity.this,task.getResult().toString(), Toast.LENGTH_SHORT);}
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}