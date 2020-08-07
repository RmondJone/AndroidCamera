package com.rmondjone.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭翰林
 * @date 2019/3/1 0001 9:21
 * 注释:对焦框
 */
public class OverCameraView extends AppCompatImageView {
    private Context context;
    //焦点附近设置矩形区域作为对焦区域
    private Rect touchFocusRect;
    private Paint touchFocusPaint;
    //是否正在对焦
    private boolean isFoucuing;

    public OverCameraView(Context context) {
        this(context, null, 0);
    }

    public OverCameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        //画笔设置
        touchFocusPaint = new Paint();
        touchFocusPaint.setColor(Color.GREEN);
        touchFocusPaint.setStyle(Paint.Style.STROKE);
        touchFocusPaint.setStrokeWidth(3);
    }

    public boolean isFoucuing() {
        return isFoucuing;
    }

    public void setFoucuing(boolean foucuing) {
        isFoucuing = foucuing;
    }

    /**
     * 注释：对焦并绘制对焦矩形框
     * 时间：2019/3/1 0001 9:28
     * 作者：郭翰林
     *
     * @param camera
     * @param autoFocusCallback
     * @param x
     * @param y
     */
    public void setTouchFoucusRect(Camera camera, Camera.AutoFocusCallback autoFocusCallback, float x, float y) {
        //以焦点为中心，宽度为200的矩形框
        touchFocusRect = new Rect((int) (x - 100), (int) (y - 100), (int) (x + 100), (int) (y + 100));

        //对焦光感区域
        int left = touchFocusRect.left * 2000 / getWindowWidth(context) - 1000;
        int top = touchFocusRect.top * 2000 / getWindowHeight(context) - 1000;
        int right = touchFocusRect.right * 2000 / getWindowWidth(context) - 1000;
        int bottom = touchFocusRect.bottom * 2000 / getWindowHeight(context) - 1000;
        // 如果超出了(-1000,1000)到(1000, 1000)的范围，则会导致相机崩溃
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        final Rect targetFocusRect = new Rect(left, top, right, bottom);

        //对焦
        doTouchFocus(camera, autoFocusCallback, targetFocusRect);
        //刷新界面，调用onDraw(Canvas canvas)函数绘制矩形框
        postInvalidate();
    }

    /**
     * 注释：设置camera参数，并完成对焦
     * 时间：2019/3/1 0001 9:27
     * 作者：郭翰林
     *
     * @param camera
     * @param autoFocusCallback
     * @param tfocusRect
     */
    public void doTouchFocus(Camera camera, Camera.AutoFocusCallback autoFocusCallback, final Rect tfocusRect) {
        if (camera == null || isFoucuing) {
            return;
        }
        try {
            final List<Camera.Area> focusList = new ArrayList<>();
            Camera.Area focusArea = new Camera.Area(tfocusRect, 1000);
            focusList.add(focusArea);

            Camera.Parameters para = camera.getParameters();
            para.setFocusAreas(focusList);
            para.setMeteringAreas(focusList);
            para.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.cancelAutoFocus();
            camera.setParameters(para);
            camera.autoFocus(autoFocusCallback);
            isFoucuing = true;
        } catch (Exception e) {
            Log.e("设置相机参数异常", e.getMessage());
        }
    }

    /**
     * 注释：对焦完成后，清除对焦矩形框
     * 时间：2019/3/1 0001 9:28
     * 作者：郭翰林
     */
    public void disDrawTouchFocusRect() {
        //将对焦区域设置为null，刷新界面后对焦框消失
        touchFocusRect = null;
        //刷新界面，调用onDraw(Canvas canvas)函数
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //在画布上绘图，postInvalidate()后自动调用
        drawTouchFocusRect(canvas);
        super.onDraw(canvas);
    }

    /**
     * 获取屏幕高度
     */
    @SuppressWarnings("deprecation")
    public static int getWindowHeight(Context cxt) {
        WindowManager wm = (WindowManager) cxt
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();

    }

    /**
     * 获取屏幕宽度
     */
    @SuppressWarnings("deprecation")
    public static int getWindowWidth(Context cxt) {
        WindowManager wm = (WindowManager) cxt
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();

    }

    private void drawTouchFocusRect(Canvas canvas) {
        if (null != touchFocusRect) {
            //根据对焦区域targetFocusRect，绘制自己想要的对焦框样式，本文在矩形四个角取L形状
            //左下角
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.bottom, touchFocusRect.left + 20, touchFocusRect.bottom + 2, touchFocusPaint);
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.bottom - 20, touchFocusRect.left, touchFocusRect.bottom, touchFocusPaint);
            //左上角
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.top - 2, touchFocusRect.left + 20, touchFocusRect.top, touchFocusPaint);
            canvas.drawRect(touchFocusRect.left - 2, touchFocusRect.top, touchFocusRect.left, touchFocusRect.top + 20, touchFocusPaint);
            //右上角
            canvas.drawRect(touchFocusRect.right - 20, touchFocusRect.top - 2, touchFocusRect.right + 2, touchFocusRect.top, touchFocusPaint);
            canvas.drawRect(touchFocusRect.right, touchFocusRect.top, touchFocusRect.right + 2, touchFocusRect.top + 20, touchFocusPaint);
            //右下角
            canvas.drawRect(touchFocusRect.right - 20, touchFocusRect.bottom, touchFocusRect.right + 2, touchFocusRect.bottom + 2, touchFocusPaint);
            canvas.drawRect(touchFocusRect.right, touchFocusRect.bottom - 20, touchFocusRect.right + 2, touchFocusRect.bottom, touchFocusPaint);
        }
    }
}
