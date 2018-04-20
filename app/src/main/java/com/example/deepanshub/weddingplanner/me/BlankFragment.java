package com.example.deepanshub.weddingplanner.me;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.deepanshub.weddingplanner.R;
import com.mvc.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    ProgressBar progressBar;
    ImageButton selectImage;
    ImageView coupleImage;
    public static final String CACHED_IMG_KEY = "img_key";

    public static final int SECOND_PIC_REQ = 1313;
    public static final int GALLERY_ONLY_REQ = 1212;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        selectImage = v.findViewById(R.id.selectimage);
        coupleImage = v.findViewById(R.id.coupleImage);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(BlankFragment.this, "Select your Image");

            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String path = prefs.getString(CACHED_IMG_KEY, "");
        File cached = new File(path);
        if (cached.exists()) {
            Picasso.with(getActivity()).load(cached).into(coupleImage);
        }
        coupleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.pickImage(BlankFragment.this, SECOND_PIC_REQ);
            }
        });


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SECOND_PIC_REQ:
                String imagePathFromResult = ImagePicker.getImagePathFromResult(getActivity(),
                        requestCode, resultCode, data);
                if (imagePathFromResult != null) {
                    String path = "file:///" + imagePathFromResult;
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    prefs.edit().putString(CACHED_IMG_KEY, imagePathFromResult).apply();
                    Picasso.with(getActivity()).load(path).into(coupleImage);
                }
                break;
            default:
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(),
                        requestCode, resultCode, data);
                if (bitmap != null) {
                    selectImage.setImageBitmap(bitmap);
                }
        }
        InputStream is = ImagePicker.getInputStreamFromResult(getActivity(), requestCode, resultCode, data);
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("", "onActivityResult: inputstream failed");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
