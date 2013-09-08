package com.jag.framework;

import android.graphics.Bitmap;

import com.jag.framework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
	public Bitmap getBitmap();
}
