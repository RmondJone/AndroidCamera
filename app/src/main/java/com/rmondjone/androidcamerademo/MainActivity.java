package com.rmondjone.androidcamerademo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rmondjone.camera.CameraActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_IMAGE_PATH = "imagePath";
    private ImageView resultImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultImg = findViewById(R.id.result);
    }

    /**
     * 注释：跳转拍照
     * 时间：2019/3/21 0021 9:13
     * 作者：郭翰林
     */
    public void gotoCamare(View view) {
        CameraActivity.startMe(this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            resultImg.setImageURI(Uri.fromFile(new File(data.getStringExtra(KEY_IMAGE_PATH))));
        }
    }
}
