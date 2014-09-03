package com.jag.framework;


import java.util.ArrayList;
import java.util.HashMap;

import com.jag.positron.Coil;
import com.jag.positron.TextFader;
import com.jag.positron.PosTriangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.opengl.ETC1;

public interface Graphics {
	public static enum ImageFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Image newImage(String fileName, ImageFormat format);

	public void clearScreen(int color);

	public void drawLine(int x, int y, int x2, int y2, int color, int alph, int stroke);
	public void drawLine(int x, int y, int x2, int y2, int color, int alph, int stroke, Paint p);
	public void drawOval(float left, float top, float right, float bottom, int color, int alpha);
	
	public void drawPath(ArrayList<ArrayList<Integer>> coords, int points);

	public void drawRect2(int x, int y, int width, int height, int color, Paint paint);
	
	public void drawRect(int x, int y, int width, int height, int color);

	public void drawImage(Image image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight);
	
	public void drawScaledImagewPaint(Image Image, int x, int y, int width, int height,
			int srcX, int srcY, int srcWidth, int srcHeight, Paint p);

	public void drawImage(Image Image, int x, int y);

	public void drawColImage(Bitmap bit, int x, int y, int alph, int origCol, int replCol);

	void drawString(String text, int x, int y, Paint paint);
	
	void drawString(String text, int x, int y, Paint p, TextFader f);

	public int getWidth();

	public int getHeight();

	public void drawARGB(int i, int j, int k, int l);

	public void drawTransRect(int x, int y, int width, int height);

	public void saveCanvas();

	public void restoreCanvas();

	public Canvas getCanvas();

	public void drawCircFill(float cx,float cy,float radius,int color, int alpha);
	
	public void drawCircOut(float cx,float cy,float radius,int color, int stroke);
	
	public void drawCircBlue(float cx,float cy,float radius,int color, int alpha);
	
	public void drawCropped(Image base2);

	public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight);

	public void drawCircFill(float cx,float cy,float radius,int color, int alpha, Paint p);

	public void drawCircOut(float cx,float cy,float radius,int color, int stroke, int alpha);

	public void drawImage(Image Image, int x, int y, int alpha);
	
	public void drawImage(Image Image, int x, int y, Paint p);

	public void drawPosTri(PosTriangle pt, int alph);
	

	void drawRect(int x, int y, int width, int height, int color, int alpha);

	public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight, int alpha);

	public void changeCol(Bitmap b, int color);

	void drawImage(Bitmap bit, int x, int y, int alph);

	public void drawBoltPath(ArrayList<ArrayList<Integer>> points, int i, int alpha);

	public void drawPointBoltPath(ArrayList<Coil.Point> points, int ptNum, int alpha, int color, int strokeWidth, boolean charged, boolean strike);

	public void drawScaledImage(Image image, int i, int j, int xPander,
			int ySpander, int k, int l, int width, int height, int alph, int rgb, int rgb2);
	

}
