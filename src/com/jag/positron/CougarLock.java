package com.jag.positron;

import android.graphics.Color;

import com.jag.framework.Graphics;

public class CougarLock {
	public PosTimer startTimer;
	public Graphics g;
	public int startX;
	public int startY;
	private Animation coug;
	private boolean starting;

	public CougarLock (Graphics g, Animation cougar){
		this.g = g;
		startX = 0;
		startY = 0;
		coug = cougar;
		starting = false;

	}

	public void update(){
		if (startTimer == null)
			startTimer = new PosTimer(3000);
		if (getStartup())	
			startUpdate();

//		draw();
	}

	public void draw(){
		if (getStartup())	
			startDraw();
	}



	public boolean getStartup(){
		return starting;
	}

	public void setStartup(boolean setter){
		starting = setter;
	}

	private void startUpdate(){
		startTimer.update();
		
		if (startTimer.getTrigger())
			starting = false;

	}

	private void startDraw() {
//		
		g.drawImage(coug.getImage(), startX-coug.getImage().getWidth()/2, startY-coug.getImage().getHeight()/2, -1);
		//				cougar.update(10);

//		g.drawRect(100, 100, 100, 100, Color.BLUE);

	}

	public void setStartPos(int tcx, int tcy) {
		startX = tcx;
		startY = tcy;
	}


	//LOCK (w/ SHAKER FOR POINTS)
	//COUGARHEAD OVER SCORE
	//

}
