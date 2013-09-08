package com.jag.framework.implementation;

import android.graphics.Bitmap;

import com.jag.framework.Image;
import com.jag.framework.Graphics.ImageFormat;

public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;
    
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }
    
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
