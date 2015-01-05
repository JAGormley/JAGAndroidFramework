package com.jag.positron;

import java.util.ArrayList;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;

import com.jag.framework.Graphics;
import com.jag.positron.Tooltips.Tip;

public class Magnet {
	public static Magnet theMagnet;
	int height;
	int lineNum;
	PosTimer speedTimer;
	ArrayList<Float> lines;
	Graphics g;
	int stretcher;
	private int stretchMax;
	private int light;

	public Magnet(){
		stretcher = 0;
		g = GameScreen.graph;
		lines = new ArrayList<Float>();
		height = 84;
		lineNum = 7;
		for (int i = 0; i < lineNum; i++) {
			lines.add((float)(height/lineNum)*i);
		}
	}	

	public void updateAndDraw(int spriteY){
		if (!(Tooltips.currentTip == Tip.MOVE))
		if (speedTimer == null || speedTimer.getTrigger()){
			speedTimer = new PosTimer(3000);
		}
		stretchMax = 900;
		int stretchFactor = stretchMax/18;
		if (spriteY < height && stretcher < stretchMax){		
			stretcher+=stretchFactor;
		}
		else if (stretcher > 1) stretcher -= stretchFactor*3;
		if (stretcher < 1) stretcher = 1; 

		for (int i = 0; i < lines.size(); i++) {
			float mover = .75f;
			lines.set(i, (lines.get(i)+mover));
			if (lines.get(i) > height){
				lines.set(i, 0f);
			}
		}		
		draw();

	}
	private void draw() {
		float h = 0f;
		float s = 0f;
		float b = 0f;
		
		// COLOR TRANSITIONS
		if (stretcher == 1 && !CougarLock.active){
			h = 180;
			s = ((float)light/205f);
			b = (.5f * ((float)light/205f)) +.5f;			
		}
		else if (CougarLock.active){
			h = 31;
			s = ((float)light/205f);
			b = (.5f * ((float)light/205f)) +.5f;
		}
		else {
			s = 1f * ((float)stretcher/(float)stretchMax);
			b = (.5f * ((float)stretcher/(float)stretchMax)) +.5f;			
		}
		int col = Color.HSVToColor(new float[]{h,s,b});

		for (int i = 0; i < lines.size(); i++) { 
			g.drawOval(-15-stretcher, lines.get(i), GameScreen.screenwidth+15+stretcher, -3, col, 150);
		}

	}

	public static Magnet getInstance(){
		if (theMagnet == null){
			theMagnet = new Magnet();
		}
		return theMagnet;
	}

	public void scoreLight(int alph) {
		light = alph;

	}

	public void reset() {
		theMagnet = new Magnet();		
	}
}
