package com.rmondjone.androidcamerademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rmondjone.camera.CameraActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 注释：跳转拍照
     * 时间：2019/3/21 0021 9:13
     * 作者：郭翰林
     */
    public void gotoCamare(View view) {
        CameraActivity.startMe(this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);
    }

}
