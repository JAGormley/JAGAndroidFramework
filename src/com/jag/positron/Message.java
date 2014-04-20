package com.jag.positron;

import android.Manifest.permission;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.DisplayMetrics;

import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.implementation.AndroidGame;

public class Message {
	TextFader f;
	Graphics g;
	Paint p = new Paint();
	String text;
	int mX;
	int mY;
	int i = 1;
	DisplayMetrics metrics;
	private int colour;
	private boolean alive;
	float startTime;
	float elapsedTime;
	private float fadeTime;
	private float textSize;
	private float nonFadeTime;
	private float origTSize;
	private boolean firstUpdate;
/**
 * 
 * @param x
 * @param y
 * @param nonFadeTime: in secs
 * @param fadeTime: in secs
 * @param text
 * @param textSize: in pix
 * @param g: main Graphics object 
 * @param colour
 */
	public Message (int x, int y, float nonFadeTime, float fadeTime, String text, float textSize, Graphics g, int colour){
		this.g = g;
		this.text = text;
		this.fadeTime = fadeTime;
		this.nonFadeTime = nonFadeTime;
		this.nonFadeTime= nonFadeTime; 
		firstUpdate = true;
		mX = x;
		mY = y;
		this.colour = colour;
		this.textSize = Math.round(GameScreen.screenheight * textSize);
		this.origTSize = Math.round(GameScreen.screenheight * textSize);
		p.setTypeface(Assets.font);
		p.setTextSize(Math.round(GameScreen.screenheight * textSize));
		p.setTextAlign(Paint.Align.CENTER);
//		p.setAntiAlias(true);	
		p.setColor(colour);
		this.f = new TextFader (fadeTime, x, p.measureText(text)*AndroidGame.actualLoadWidth/800);
		alive = true;
	}
	
	public void drawMessage(){
		if (firstUpdate){
			startTime = SystemClock.elapsedRealtime();
			firstUpdate = false;
		}
		elapsedTime = (SystemClock.elapsedRealtime() - startTime)/1000;
		if (elapsedTime < nonFadeTime){
//			System.out.println("elTime: "+elapsedTime);
			g.drawString(text, mX, mY, p);
		}
		else if (f.isAlive()){
		p.setShader(new LinearGradient(f.getStart(), mY, f.getEnd(), mY, colour, Color.alpha(0), android.graphics.Shader.TileMode.CLAMP));
		g.drawString(text, mX, mY, p);
		f.update();		
	}
		else alive = false;
	}
	
	/** grow the word by this percent of its original size
	 **/
	public void growM(float totalGrowthPercent){
		float tempDiv = (elapsedTime/(nonFadeTime+fadeTime));
		textSize = origTSize+((totalGrowthPercent*origTSize)*tempDiv);
		
		p.setTextSize(textSize);
	}
	
	public void setX(int x){
		mX = x;
	}
	public void setY(int y){
		mY = y;
	}
	public boolean isAlive(){
		return alive;
	}
	public void triggerFade(){
		nonFadeTime = 0;
	}
	public void setMAlpha(int alpha){
		p.setAlpha(alpha);
	}
	public String getText(){
		return text;
	}
}
