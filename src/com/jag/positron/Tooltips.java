package com.jag.positron;

import android.graphics.Color;

import com.jag.framework.Graphics;

public class Tooltips {
	private static Tooltips tTips;
	public boolean active;
	public Graphics g;
	private Message message;
	public enum Tip {
		MOVE, POINT, LIGHTNING, COUGAR;
	}
	public static Tip currentTip;

	private Tooltips(){
		this.g = GameScreen.graph;
	}


	public void update(){
		if (ControlPanel.enableTips && active){
			switch (currentTip){
			case MOVE:
				updateMove();
			case POINT:
				updatePoint();
			case LIGHTNING:
				updateLightning();
			case COUGAR:
				updateCougar();			
			}
		}
	}


	public void draw(){
		if (ControlPanel.enableTips && active){
			switch (currentTip){
			case MOVE:
				drawMove();
			case POINT:
				drawPoint();
			case LIGHTNING:
				drawLightning();
			case COUGAR:
				drawCougar();			
			}
		}
	}

	//UPDATES
	private void updateMove() {

	}
	private void updatePoint() {
		// TODO Auto-generated method stub

	}
	private void updateLightning() {
		// TODO Auto-generated method stub

	}
	private void updateCougar() {
		// TODO Auto-generated method stub

	}

	//DRAWS
	private void drawMove() {
		try {
			//			g.drawArrow(200, 400, 10, Color.BLUE, 255, "up");
//			g.drawImage(Assets.arrow, 
//					GameScreen.screenwidth/2-(Assets.arrow.getWidth()/2), 
//					925-Slider.sliderAlph/2, 255);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private void drawPoint() {
		// TODO Auto-generated method stub

	}
	private void drawLightning() {
		// TODO Auto-generated method stub

	}
	private void drawCougar() {
		// TODO Auto-generated method stub

	}

	public void activate(Tip t){
		active = true;
		currentTip = t;
	}

	public boolean isActive(){
		return active;
	}

	public static Tooltips getInstance(){
		if (tTips == null){
			tTips = new Tooltips();
		}
		return tTips;
	}
}




