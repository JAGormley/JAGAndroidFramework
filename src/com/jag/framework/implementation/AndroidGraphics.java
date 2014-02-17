package com.jag.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.jag.framework.Graphics;
import com.jag.framework.Image;
import com.jag.framework.Graphics.ImageFormat;
import com.jag.positron.Assets;
import com.jag.positron.TextFader;
import com.jag.positron.PosTriangle;

public class AndroidGraphics implements Graphics {
	AssetManager assets;
	Bitmap frameBuffer;
	Bitmap bitmap;
	Canvas canvas;
	Paint paint, paint2, paint3, paint4, paint5;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();


	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
		this.assets = assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
		this.paint2 = new Paint();
		this.paint3 = new Paint();
		this.paint4 = new Paint();
		this.paint5 = new Paint();

	}

	@Override
	public Image newImage(String fileName, ImageFormat format) {
		Config config = null;
		if (format == ImageFormat.RGB565)
			config = Config.RGB_565;
		else if (format == ImageFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;

		Options options = new Options();
		options.inPreferredConfig = config;
		options.inDither = false;
		//		if (fileName.contains("instr")){
		//			options.inSampleSize = 8;
		//		}

		InputStream in = null;
		Bitmap bitmap = null;

		try {
			in = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			if (bitmap == null)
				throw new RuntimeException("Couldn't load bitmap from asset '"
						+ fileName + "'");
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap from asset '"
					+ fileName + "'");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}	

		if (bitmap.getConfig() == Config.RGB_565)
			format = ImageFormat.RGB565;
		else if (bitmap.getConfig() == Config.ARGB_4444)
			format = ImageFormat.ARGB4444;
		else
			format = ImageFormat.ARGB8888;

		return new AndroidImage(bitmap, format);
	}

	@Override
	public void clearScreen(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color, int alph, int stroke) {
		paint.setColor(color);
		paint.setAlpha(alph);
		paint.setStrokeWidth(stroke);

		canvas.drawLine(x, y, x2, y2, paint);
	}

	public void changeCol(Bitmap b, int color){
		int width = b.getWidth();
		int height = b.getHeight();
		int[] pixels = new int[width * height];
		for (int i = 0; i < pixels.length; i++){
			pixels[i] = color;
		}		
		b.setPixels(pixels, 0, width, 0, 0, width, height);
	} 


	@Override
	public void drawPosTri(PosTriangle pt, int alph) {

		paint5.setColor(Color.MAGENTA);
		paint5.setStyle(Style.FILL);
		paint5.setAlpha(alph);

		canvas.drawPath(pt.getPath(), paint5);
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}

	@Override
	public void drawRect(int x, int y, int width, int height, int color, int alpha) {

		paint.setColor(color);
		paint.setStyle(Style.FILL);
		paint.setAlpha(alpha);
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}

	@Override
	public void drawRect2(int x, int y, int width, int height, int color, Paint paint) {
		canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
	}

	public void drawCircFill(float cx,float cy,float radius,int color, int alpha) {
		paint3.setColor(color);
		paint3.setStyle(Style.FILL);
		paint3.setAntiAlias(true);
		paint3.setAlpha(alpha);
		//paint3.setMaskFilter(new BlurMaskFilter(20, Blur.NORMAL));
		canvas.drawCircle(cx, cy, radius, paint3);

	}

	public void drawCircFill(float cx,float cy,float radius,int color, int alpha, Paint p) {
		p.setColor(color);
		p.setStyle(Style.FILL);
		p.setAntiAlias(true);
		p.setAlpha(alpha);
		//paint3.setMaskFilter(new BlurMaskFilter(20, Blur.NORMAL));
		canvas.drawCircle(cx, cy, radius, p);

	}

	public void drawCircBlue(float cx,float cy,float radius,int color, int alpha) {
		paint4.setColor(color);
		paint4.setStyle(Style.FILL);
		paint4.setAntiAlias(true);
		paint4.setStyle(Style.STROKE);
		paint4.setStrokeWidth(alpha);
		//paint4.setMaskFilter(new BlurMaskFilter(50, Blur.OUTER));
		canvas.drawCircle(cx, cy, radius, paint4);

	}

	public void drawCircOut(float cx,float cy,float radius,int color, int stroke) {
		paint.setColor(color);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(stroke);
		paint.setAntiAlias(true);
		canvas.drawCircle(cx, cy, radius, paint);		
	}

	public void drawCircOut(float cx,float cy,float radius,int color, int stroke, int alpha) {
		paint.setColor(color);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(stroke);
		paint.setAntiAlias(true);
		paint.setAlpha(alpha);
		canvas.drawCircle(cx, cy, radius, paint);		
	}

	public void drawTransRect(int x, int y, int width, int height) {  
		canvas.clipRect(x,y,width,height);

	}


	public void saveCanvas() {  
		canvas.save();       
	}

	public void restoreCanvas() {  
		canvas.restore();       
	}

	public Canvas getCanvas(){
		return canvas;

	}

	public Canvas clearCanvas(){
		return canvas;

	}


	@Override
	public void drawARGB(int a, int r, int g, int b) {
		paint.setStyle(Style.FILL);
		canvas.drawARGB(a, r, g, b);

	}

	@Override
	public void drawString(String text, int x, int y, Paint paint){
		canvas.drawText(text, x, y, paint);
	}

	@Override
	public void drawString(String text, int x, int y, Paint p, TextFader f) {
		canvas.drawText(text, x, y, p);

	}


	public void drawImage(Image Image, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + srcWidth;
		dstRect.bottom = y + srcHeight;

		canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect,
				null);
	}

	@SuppressLint("NewApi")
	@Override
	public void drawImage(Image Image, int x, int y, Paint p) {
		canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, p);
	}

	@SuppressLint("NewApi")
	@Override
	public void drawImage(Image Image, int x, int y, int alpha) {
		paint.setAlpha(alpha);
		canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, paint);
	}



	public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight){
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + width;
		dstRect.bottom = y + height;  

		canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, null);       
	}

	public void drawScaledImage(Image Image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight, int alpha){
		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + width;
		dstRect.bottom = y + height;  

		paint = new Paint();
		paint.setAlpha(alpha);
		canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, paint);       
	}

	public void drawScaledImagewPaint(Image Image, int x, int y, int width, int height,
			int srcX, int srcY, int srcWidth, int srcHeight, Paint p){


		srcRect.left = srcX;
		srcRect.top = srcY;
		srcRect.right = srcX + srcWidth;
		srcRect.bottom = srcY + srcHeight;

		dstRect.left = x;
		dstRect.top = y;
		dstRect.right = x + width;
		dstRect.bottom = y + height;  

		canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect, p);       
	}

	public void drawCropped(Image image){
		Bitmap croppedBmp = Bitmap.createBitmap(((AndroidImage) image).bitmap, 0, 0, 800, image.getHeight()-40);
		canvas.drawBitmap(croppedBmp, 0, 900, null);
		croppedBmp = null;
	}

	@Override
	public int getWidth() {
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		return frameBuffer.getHeight();
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color, int alph,	int stroke, Paint p) {
		p.setColor(color);
		p.setAlpha(alph);
		p.setStrokeWidth(stroke);

		canvas.drawLine(x, y, x2, y2, p);	
	}

	@SuppressLint("NewApi")
	@Override
	public void drawImage(Image Image, int x, int y) {
		canvas.drawBitmap(((AndroidImage)Image).bitmap, x, y, null);
	}

	@SuppressLint("NewApi")
	@Override
	public void drawImage(Bitmap bit, int x, int y, int alph) {
		Paint p = null;
		ColorFilter filter = null;
		p = new Paint(Color.BLUE); 
		filter = new LightingColorFilter(Color.BLUE, 255); 		
		p.setColorFilter(filter);	
		p.setAlpha(alph);
		canvas.drawBitmap(bit, x, y, p);

	}

}
