package com.jag.positron;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Color;
import android.graphics.Point;
import android.os.SystemClock;

import com.jag.framework.Graphics;

public class CougarLock {
	public PosTimer startTimer;
	public Graphics g;
	public int startX;
	public int startY;
	private Animation coug;
	public static boolean starting;
	public static boolean running;
	private boolean active;
	private static final double ENDPOINTX = GameScreen.screenwidth/2;
	private static final double ENDPOINTY = GameScreen.screenheight * .92;
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
	
	


	public CougarLock (Graphics g, Animation cougar){
		this.g = g;
		startX = 0;
		startY = 0;
		coug = cougar;
		starting = false;
		running = false;
		cougShaker = new Shaker(300);
		growInc = 0;
		sw = GameScreen.screenwidth;
		sh = GameScreen.screenheight;
	}

	public void update(){
		if (startTimer == null)
			startTimer = new PosTimer(2000);
		if (getStartup()){
			startUpdate();
		}
	}

	public void draw(){
		if (getStartup())	
			startDraw();
		else if (startTimer != null) {
			if (startTimer.getTrigger()){
				
				// COUGDRAW				
				if (!cougShaker.shakerIsDead()){
					cougShaker.update();					
					if (cougShaker.getxShift())
						g.drawColImage(coug.getImage().getBitmap(), (int)ENDPOINTX-(coug.getImage().getWidth()/2+cougShaker.getShifter()), 
								(int)ENDPOINTY-(coug.getImage().getHeight()/2), 255, Color.rgb(255, 132, 0), Color.rgb(255, 0, 0));					
					else 
						g.drawColImage(coug.getImage().getBitmap(), (int)ENDPOINTX-(coug.getImage().getWidth()/2),
								(int)ENDPOINTY-(coug.getImage().getHeight()/2)+cougShaker.getShifter(), 255,  
								Color.rgb(255, 132, 0), Color.rgb(255, 0, 0));		
					
					if (!colored){
						growInc += 10;
						colored = true;
					}
					
				}
				else {
					colored = false;
					coug.update(20);
					point = false;
					cougShaker = new Shaker(300);
//					g.drawImage(coug.getImage(), (int)ENDPOINTX-(coug.getImage().getWidth()/2),
//							(int)ENDPOINTY-(coug.getImage().getHeight()/2));
					g.drawImage(coug.getImage(), (int)ENDPOINTX-(coug.getImage().getWidth()/2),
							(int)ENDPOINTY-(coug.getImage().getHeight()/2));
				}
				
			}
		}
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

	private void startUpdate(){
		startTimer.update();
		double progMult = startTimer.getElapsedMillis()/startTimer.getTotalMillis();
		headPosY = progMult*distanceY + startY;
		headPosX = progMult*distanceX + startX;

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

	public void setStartPos(int tcx, int tcy) {
		startX = tcx;
		startY = tcy;
		distanceX = (int) (ENDPOINTX - startX);
		distanceY = (int) (ENDPOINTY - startY);	
	}

	public void setActive(boolean b) {
		active = b;		
	}

	public boolean getActive() {
		return active;	
	}

	public void setPointT(){
		point = true;
	}

}
