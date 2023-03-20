package com.example.chs_coordinator2;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoSharing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoSharing extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private Uri imageUri;
    private StorageTask uploadTask;
    private FirebaseUser user;
    private Button btnChooseFile, btnShare;
    private FloatingActionButton btnBack;
    private StorageReference storageRef;
    private boolean isImageChosen;

    public PhotoSharing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoSharing.
     */
    // TODO: Rename and change types and number of parameters

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK) {
                imageUri = result.getData().getData();
                Picasso.get().load(imageUri).into(imageView);
                isImageChosen = true;
            }

        }
    });
    public static PhotoSharing newInstance(String param1, String param2) {
        PhotoSharing fragment = new PhotoSharing();
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
        View view = inflater.inflate(R.layout.fragment_photo_sharing, container, false);
        isImageChosen = false;
        imageView = view.findViewById(R.id.imgViewSchedule);
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference().child("Uploads");
        btnChooseFile = view.findViewById(R.id.btnChooseFile);
        btnShare = view.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileExplorer();

            }
        });
        return view;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void shareImage() {
        if (isImageChosen) {
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                // Create a reference to the chosen image in Firebase Storage
                StorageReference fileRef = storageRef.child(user.getUid()).child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                // Upload the image to Firebase Storage
                uploadTask = fileRef.putFile(imageUri);

                // Once the upload is complete, create a share intent and launch it
                uploadTask.addOnCompleteListener((OnCompleteListener<UploadTask.TaskSnapshot>) task -> {
                    if (task.isSuccessful()) {
                        // Get the download URL of the uploaded image
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Create a share intent to allow the user to share the image via different apps
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("image/*");
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(Intent.createChooser(shareIntent, "Share image via"));
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "Please choose an image first", Toast.LENGTH_SHORT).show();
        }


    }

    private void openFileExplorer() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startForResult.launch(intent);
    }
}