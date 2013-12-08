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
	Fader f;
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
	private float nonFadeTime;

	public Message (int x, int y, float nonFadeTime, float fadeTime, String text, Graphics g, int colour){
		this.g = g;
		this.text = text;
		startTime = SystemClock.elapsedRealtime();
		this.nonFadeTime= nonFadeTime; 
		mX = x;
		mY = y;
		this.colour = colour;
		p.setTypeface(Assets.font);
		p.setTextSize(Math.round(GameScreen.screenheight * .1));
		p.setTextAlign(Paint.Align.CENTER);
		p.setAntiAlias(true);	
		p.setColor(colour);
		this.f = new Fader (fadeTime, x, p.measureText(text)*AndroidGame.actualLoadWidth/800);
		alive = true;
	}
	
	public void drawMessage(){
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
	
	public void setX(int x){
		mX = x;
	}
	public void setY(int y){
		mY = y;
	}
	public boolean isAlive(){
		return alive;
	}
}
