package com.jag.positron;

import android.graphics.Color;
import android.graphics.Paint;

import com.jag.framework.Graphics;

public class Tooltips {
	private static Tooltips tTips;
	public boolean active;
	public Graphics g;
	private Message message;
	private int arrowY;
	private int arrowDeathY;
	private PosTimer arrowTimer;
	public static PosTimer pointDelay;
	private Paint painty;
	private boolean pointEnd;
	private PosTimer mFader; 
	public enum Tip {
		MOVE, POINT, LIGHTNING, COUGAR;
	}
	public static Tip currentTip;

	private Tooltips(){
		this.g = GameScreen.graph;
		pointEnd = false;
		painty = new Paint();
		painty.setTypeface(Assets.font);
		painty.setTextSize(Math.round(GameScreen.screenheight * .05));
		painty.setTextAlign(Paint.Align.CENTER);
		painty.setColor(Color.MAGENTA);
	}


	public void update(boolean fingerMove, int score){
		if (ControlPanel.enableTips && active){
			switch (currentTip){
			case MOVE:
				updateMove(fingerMove);
				break;
			case POINT:
				updatePoint(score);
				break;
			case LIGHTNING:
				updateLightning();
				break;
			case COUGAR:
				updateCougar();
				break;
			}
		}
	}


	public void draw(int fingerx, float fingery, Pieces p, boolean wayback){
		if (ControlPanel.enableTips && active){
			switch (currentTip){
			case MOVE:
				drawMove();
				break;
			case POINT:
				drawPoint(fingerx, (int)fingery, p);
				break;
			case LIGHTNING:
				drawLightning(fingerx, p.x, wayback);
				break;
			case COUGAR:
				drawCougar();	
				break;
			}
		}
	}

	//UPDATES
	private void updateMove(boolean fingerMove) {

		// touch activates arrow fade
		if (fingerMove) {	
			if (arrowTimer == null){
				arrowDeathY = arrowY;
				arrowTimer = new PosTimer(1000);
			}
		}

		if (arrowTimer != null){
			if (!arrowTimer.getTrigger()){
				arrowTimer.update();
			}
			// end MOVE session
			else {
				if (pointDelay == null){
					pointDelay = new PosTimer(1000);	
					currentTip = Tip.POINT;
				}
			}
		}
	}

	private void updatePoint(int score) {
		if (!pointDelay.trigger){
			pointDelay.update();
		}
		if (score > 9 && !pointEnd){
			System.out.println("here");
			pointEnd = true;
			mFader = new PosTimer(1000);
		}
		if (pointEnd){
			mFader.update();
			if (mFader.getTrigger()){
				currentTip = Tip.LIGHTNING;
			}
		}
	}
	private void updateLightning() {
		// TODO Auto-generated method stub

	}
	private void updateCougar() {
		// TODO Auto-generated method stub

	}

	//DRAWS
	public void drawMove() {
		if (arrowTimer == null){
			double bouncer = (double)Slider.sliderAlph/85.0;
			arrowY = (int)(900-(bouncer*(Slider.sliderAlph/2)*-1));
			g.drawImage(Assets.arrow, 
					GameScreen.screenwidth/2-(Assets.arrow.getWidth()/2), 
					arrowY, 255);
		}
		else {
			g.drawImage(Assets.arrow, 
					GameScreen.screenwidth/2-(Assets.arrow.getWidth()/2), 
					arrowDeathY, ControlPanel.alphize(arrowTimer.getRemainingMillis(), 
							arrowTimer.getTotalMillis()));
			//			arrowTimer = null;
		}

	}
	private void drawPoint(int fingerx, int fingery, Pieces p) {
		arrowTimer = null;
		if (mFader != null)
			painty.setAlpha(ControlPanel.alphize(mFader.getRemainingMillis(), mFader.getTotalMillis()));
		else painty.setAlpha(255);

		if (!p.type){
			if (p.x < fingerx){							
				g.drawString("nope", p.x, p.y+(int)Math.round(GameScreen.screenheight *.1), painty);
			}
			if (p.x >= fingerx){							
				g.drawString("yep", p.x, p.y+(int)Math.round(GameScreen.screenheight *.1), painty);
			}
		}
		else {
			if (p.x >= fingerx){					
				g.drawString("nope", p.x, p.y+(int)Math.round(GameScreen.screenheight *.1), painty);
			}
			if (p.x < fingerx){							
				g.drawString("yep", p.x, p.y+(int)Math.round(GameScreen.screenheight *.1), painty);
			}
		}

	}
	public void drawLightning(int fingerx, int x, boolean wayback) {
		if (wayback && !Collider.lazer){
			double bouncer = (double)Slider.sliderAlph/85.0;
			arrowY = (int)(fingerx-(bouncer*(Slider.sliderAlph/2)*-1));
			boolean dir = false;
			if (fingerx < x)
				dir = true;
			else dir = false;
			g.drawArrow(dir, arrowY-70, 500, 100, 40, Color.CYAN, 255);
		}
		//		if (wayback) {
		//			g.drawArrow(fingerx-50, 500, 100, 40, Color.CYAN, 255);
		//		}
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

	public void reset(){
		tTips = new Tooltips();
		pointDelay = null;
	}
	public static boolean pointStart(){
		return (pointDelay != null && currentTip == Tip.POINT && !pointDelay.getTrigger());
	}

	public static Tooltips getInstance(){
		if (tTips == null){
			tTips = new Tooltips();
		}
		return tTips;
	}
}




