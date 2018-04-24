package com.example.deepanshub.weddingplanner.me;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexfu.countdownview.CountDownView;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.deepanshub.weddingplanner.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    HorizontalStepView horizontalStepView;
    private static final int PICK_IMAGE = 200;
    private ImageView selectImage;
    private ImageView coupleImage;
    Uri imageURI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        CountDownView countDownView = v.findViewById(R.id.count_down);
        countDownView.start();
        horizontalStepView = v.findViewById(R.id.horizontalstepview);

        List<StepBean> source = new ArrayList<>();
        StepBean stepBean0 = new StepBean("App setup", 1);
        StepBean stepBean1 = new StepBean("profile", 1);
        StepBean stepBean2 = new StepBean("ToDOs", 1);
        StepBean stepBean3 = new StepBean("vendors", 1);
        StepBean stepBean4 = new StepBean("Guests", 0);
        StepBean stepBean5 = new StepBean("Married", -1);

        source.add(stepBean0);
        source.add(stepBean1);
        source.add(stepBean2);
        source.add(stepBean3);
        source.add(stepBean4);
        source.add(stepBean5);

        horizontalStepView
                .setStepViewTexts(source)
                .setStepViewComplectedTextColor(Color.parseColor("#FFF344"))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_color))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFF343"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon));
        selectImage = v.findViewById(R.id.selectimage);
        coupleImage = v.findViewById(R.id.coupleImage);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }
        });


        return v;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageURI = data.getData();
            coupleImage.setImageURI(imageURI);
            String imagePath = imageURI.getPath();
            Log.e("---------------", "onActivityResult: " + imageURI.toString());
            Log.e("---------------", "onActivityResult: ");
            saveToInternalStorage(imageURI);
        }

    }

    void saveToInternalStorage(Uri imageURI) {
        String sourceFile = imageURI.getPath().toString();
        String destinationFile = android.os.Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "couple.jpg";

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFile));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while (bis.read(buf) != 1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    //
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case SECOND_PIC_REQ:
//                String imagePathFromResult = ImagePicker.getImagePathFromResult(getActivity(),
//                        requestCode, resultCode, data);
//                if (imagePathFromResult != null) {
//                    String path = "file:///" + imagePathFromResult;
//                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                    prefs.edit().putString(CACHED_IMG_KEY, imagePathFromResult).apply();
//                    Picasso.with(getActivity()).load(path).into(coupleImage);
//                }
//                break;
//            default:
//                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(),
//                        requestCode, resultCode, data);
//                if (bitmap != null) {
//                    selectImage.setImageBitmap(bitmap);
//                }
//        }
//        InputStream is = ImagePicker.getInputStreamFromResult(getActivity(), requestCode, resultCode, data);
//        if (is != null) {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.e("", "onActivityResult: inputstream failed");
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
