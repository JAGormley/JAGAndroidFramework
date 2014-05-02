package com.jag.positron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;

import com.jag.framework.Graphics;

public class ShakeString {

	private Graphics g;
	private String text;
	private double x;
	private double y;
	public ArrayList<Integer> mover;
	Paint textPaint;
	PosTimer timer;

	public ShakeString(Graphics g, String text, int x, int y){
		this.g = g;
		this.text = text;
		this.x = x;
		this.y = y;
		mover = new ArrayList<Integer>();
		timer = new PosTimer(510);

		textPaint = new Paint();
		textPaint.setTypeface(Assets.font);
		textPaint.setTextSize(Math.round(g.getHeight() * .1));
		textPaint.setTextAlign(Paint.Align.CENTER);
//		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.MAGENTA);
	}
	
	public ShakeString(Graphics g, String text, int x, int y, Paint txtPnt){
		this.g = g;
		this.text = text;
		this.x = x;
		this.y = y;
		mover = new ArrayList<Integer>();
		timer = new PosTimer(510);
		
		
//		txtPnt.setShadowLayer(radius, dx, dy, color)
		textPaint = txtPnt;
		textPaint.setColor(Color.CYAN);
		
	}

	public void drawAndUpdate(){

		int moveMag = 5;
		int shifter = 0;
		//dir is move direction, f is horizontal, t is vertical
		boolean dir = false;
		int curInt = 0;
		if (mover.size() == 0){
			for (int i = 1; i < 5 ; i++){
				mover.add(i);
			}
		}
		Collections.shuffle(mover);
		curInt = mover.get(0);

		switch (curInt){
		//UP
		case 1: 
			shifter = (-1 * moveMag);
			dir = true;
			break;
			//DOWN
		case 2: 
			shifter = ( 1 * moveMag);
			dir = true;
			break;	
			//LEFT
		case 3:
			shifter = (-1 * moveMag);
			dir = false;
			break;
			//RIGHT
		case 4:
			shifter = ( 1 * moveMag);
			dir = false;
			break;
		}
		timer.update();
		int timeAlp = ((int) timer.getRemainingMillis()/2);
		if (timeAlp < 0)
			timeAlp = 0;		
		textPaint.setAlpha(timeAlp);

		if (dir)
			g.drawString(this.text, (int)this.x, (int)this.y + shifter, textPaint);
		else g.drawString(this.text, (int)this.x + shifter, (int)this.y, textPaint);

		mover.remove(0);

	}

	public int shakeMillisLeft(){
		return (int) timer.getRemainingMillis();
	}

	public boolean shakerIsDead(){
		return shakeMillisLeft() <= 0;
	}
	
	public int getAlph(){
		return textPaint.getAlpha();
	}


}
