package com.jag.positron;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.SystemClock;

import com.jag.framework.Graphics;
import com.jag.framework.implementation.AndroidGame;

public class CougarLock {
	public PosTimer startTimer;
	public PosTimer runTimer;
	public Graphics g;
	public int startX;
	public int startY;
	private Animation coug;
	public static boolean starting;
	public static boolean running;
	public static boolean active;
	private static final double ENDPOINTX = GameScreen.screenwidth/2;
	private static final double ENDPOINTY = GameScreen.screenheight * .81;
	private int distanceY;
	private int distanceX;
	private double headPosY;
	private double headPosX;
	private Shaker cougShaker;
	private boolean point;
	private int growInc;
	private boolean colored;
	private int sw;
	private int sh;
	private boolean dying;
	private PosTimer dieTimer;
	private int dieDistX;
	private int dieDistY;
	private boolean laugh;
	private PosTimer laughTimer;
	private Paint textPaint;
	private TextFader txtfd;
	private Message countdown;


	public CougarLock (Graphics g, Animation cougar){
		this.g = g;
		startX = 0;
		startY = 0;
		coug = cougar;
		starting = false;
		running = false;
		//		cougShaker = new Shaker(300);
		growInc = 0;
		sw = GameScreen.screenwidth;
		sh = GameScreen.screenheight;
		textPaint = new Paint();
		textPaint.setTypeface(Assets.font);
		textPaint.setTextSize(Math.round(sh * .5));
		textPaint.setTextAlign(Paint.Align.CENTER);
		//		paint4.setAntiAlias(true); 
		textPaint.setColor(Color.GRAY);

	}

	public void update(){
		if (startTimer == null)
			startTimer = new PosTimer(2000);
		if (getStartup()){
			startUpdate();
		}

		if (running) {
			if (runTimer == null) {
				runTimer = new PosTimer(5000);
			}	
			runTimer.update();
			if (runTimer.getTrigger()){
				running = false;
				dying = true;
			}
		}

		if (dying){
			if (dieTimer == null) {
				dieTimer = new PosTimer(1000);
				setDiePos();
			}
			dyingUpdate();
		}

		if (laugh) {
			if (laughTimer == null) {
				laughTimer = new PosTimer(1000);
				setDiePos();
			}
			laughUpdate();
		}
	}

	public void draw(){
		if (getStartup())	
			startDraw();
		else if (dying)
			dieDraw();
		else if (laugh)
			laughDraw();
		
		// regular draw
		else if (startTimer != null) {
			if (startTimer.getTrigger()){
				if (cougShaker == null)
					cougShaker = new Shaker((int) runTimer.getTotalMillis(), true);
				colored = false;
				coug.update(20);

				if (countdown != null && countdown.isAlive()){
					countdown.growM(.2f);
					countdown.drawMessage();
				}
				else countdown = null;
				if (countdown == null){
					countdown = new Message(sw/2, sh/2, .5f, .5f, String.valueOf((int)(runTimer.getRemainingMillis()/1000+.999)), 
							.5f, g, Color.GRAY);
					countdown.setMAlpha(70);
				}

				double progMult = runTimer.getRemainingMillis()/runTimer.getTotalMillis();
				double gCol = 132*progMult-60;
				gCol = (gCol < 0) ? 0 : gCol;

				if (runTimer.getRemainingMillis() < runTimer.getTotalMillis()/2)
					cougShaker.update();					
				if (cougShaker.getxShift())
					g.drawColImage(coug.getImage().getBitmap(), (int)ENDPOINTX-(coug.getImage().getWidth()/2+cougShaker.getShifter()), 
							(int)ENDPOINTY-(coug.getImage().getHeight()/2), 255, Color.rgb(255, 132, 0), Color.rgb(255, (int)gCol, 0));					
				else 
					g.drawColImage(coug.getImage().getBitmap(), (int)ENDPOINTX-(coug.getImage().getWidth()/2),
							(int)ENDPOINTY-(coug.getImage().getHeight()/2)+cougShaker.getShifter(), 255,  
							Color.rgb(255, 132, 0), Color.rgb(255, (int)gCol, 0));
			}
		}
	}


	/////////// START /////////////

	public void setStartPos(int tcx, int tcy) {
		startX = tcx;
		startY = tcy;
		distanceX = (int) (ENDPOINTX - startX);
		distanceY = (int) (ENDPOINTY - startY);	
	}	
	private void startUpdate(){
		startTimer.update();
		double progMult = startTimer.getElapsedMillis()/startTimer.getTotalMillis();
		headPosX = progMult*distanceX + startX;
		headPosY = progMult*distanceY + startY;	

		if (startTimer.getTrigger()){
			starting = false;
			running = true;
		}
	}
	private void startDraw() {
		//		g.drawImage(coug.getImage(), (int) (headPosX-coug.getImage().getWidth()/2), (int) (headPosY-coug.getImage().getHeight()/2), -1);
		int sizeAdder = 0;
		int xPander = 0;
		int ySpander = 0;
		if (startTimer.getRemainingMillis() > startTimer.getTotalMillis()/2)
			sizeAdder = (int)startTimer.getElapsedMillis()/5;
		else sizeAdder = (int)startTimer.getRemainingMillis()/5;
		xPander = coug.getImage().getWidth()+sizeAdder;
		ySpander = coug.getImage().getHeight()+sizeAdder;

		g.drawScaledImage(coug.getImage(), (int)headPosX-(xPander/2), (int)headPosY-(ySpander/2), xPander, 
				ySpander, 0, 0, coug.getImage().getWidth(), coug.getImage().getHeight());
	}



	/////////// DIE /////////////

	public void setDiePos(){
		dieDistX = (int) (ENDPOINTX - sw/2);
		dieDistY = (int) (ENDPOINTY - sh/2);				
	}	
	private void dyingUpdate() {
		dieTimer.update();
		double progMult = dieTimer.getElapsedMillis()/dieTimer.getTotalMillis();
		headPosX = progMult*dieDistX + ENDPOINTX;
		headPosY = ENDPOINTY - progMult*dieDistY;
		if (dieTimer.getTrigger()){
			starting = false;
			running = false;
			dying = false;
			active = false;
		}
	}
	private void dieDraw() {
		int sizeAdder = 0;
		int xPander = 0;
		int ySpander = 0;
		//		if (dieTimer.getRemainingMillis() > dieTimer.getTotalMillis()/2)
		sizeAdder = (int)dieTimer.getElapsedMillis();
		//		else sizeAdder = (int)dieTimer.getRemainingMillis()/5;
		double progMult = dieTimer.getRemainingMillis()/dieTimer.getTotalMillis();
		int alpha = (int) (progMult*255);
		xPander = coug.getImage().getWidth()+sizeAdder;
		ySpander = coug.getImage().getHeight()+sizeAdder;

		g.drawScaledImage(coug.getImage(), (int)headPosX-(xPander/2), (int)headPosY-(ySpander/2), xPander, 
				ySpander, 0, 0, coug.getImage().getWidth(), coug.getImage().getHeight(), alpha, Color.rgb(255, 132, 0), Color.rgb(255, 0, 0));

	}

	/////////// LAUGH /////////////

	private void laughUpdate() {
		laughTimer.update();
		double progMult = laughTimer.getElapsedMillis()/laughTimer.getTotalMillis();
		headPosX = progMult*dieDistX + ENDPOINTX;
		headPosY = ENDPOINTY - progMult*dieDistY;
		if (laughTimer.getTrigger()){
			starting = false;
			running = false;
			dying = false;
			laugh = false;
			active = false;
		}		
	}

	private void laughDraw() {
		int sizeAdder = 0;
		int xPander = 0;
		int ySpander = 0;
		if (laughTimer == null) {
			laughTimer = new PosTimer(1000);
			setDiePos();
		}
		sizeAdder = (int)laughTimer.getElapsedMillis();
		double progMult = laughTimer.getRemainingMillis()/laughTimer.getTotalMillis();
		int alpha = (int) (progMult*255);
		xPander = coug.getImage().getWidth()+sizeAdder;
		ySpander = coug.getImage().getHeight()+sizeAdder;

		g.drawScaledImage(coug.getImage(), (int)headPosX-(xPander/2), (int)headPosY-(ySpander/2), xPander, 
				ySpander, 0, 0, coug.getImage().getWidth(), coug.getImage().getHeight(), alpha, Color.rgb(255, 132, 0), Color.rgb(255, 0, 0));	
	}

	public void setActive(boolean b) {
		active = b;		
	}

	public boolean getActive() {
		return active;	
	}

	public boolean getStartup(){
		return starting;
	}

	public void setStartup(boolean setter){
		starting = setter;
	}
	public void setRunning(boolean setter){
		running = setter;
	}
	public void setDying(boolean setter){
		dying = setter;
	}
	public boolean getDying(){
		return dying;
	}
	public boolean getLaugh(){
		return laugh;
	}

	public void setPointT(){
		point = true;
	}

	public void setLaugh() {
		laugh = true;
		starting = false;
		running = false;
		dying = false;		
	}

}
