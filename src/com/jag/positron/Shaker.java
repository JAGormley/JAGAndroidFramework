package com.jag.positron;

import java.util.ArrayList;
import java.util.Collections;

import android.graphics.Color;
import android.graphics.Paint;

import com.jag.framework.Graphics;

public class Shaker {

	private Graphics g;
	private double x;
	private double y;
	public ArrayList<Integer> mover;
	PosTimer timer;
	private int moveMag;
	private int shifter;
	private boolean xShift;
	//	private boolean yShift;

	/*
	 * Time in millis
	 */
	public Shaker(int time){
		//		this.x = x;
		//		this.y = y;	
		mover = new ArrayList<Integer>();		
		timer = new PosTimer(time);
		moveMag = time/5;
	}

	public void update(){
		//		yShift = false;
		
		moveMag = (int) (timer.getRemainingMillis()/5);
		shifter = 0;

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
			//DOWN (for sprite no down)
		case 2: 
			shifter = 0;
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

		if (dir){	
			xShift = false;
		}
		else xShift = true;
		
//		System.out.println("x?: " + xShift);
//		System.out.println("moverList: "+mover.toString());

		mover.remove(0);	
		
	}

	public int getShifter() {
		return shifter;
	}

	public boolean getxShift() {
		return xShift;
	}

	//	public boolean getyShift() {
	//		return yShift;
	//	}

	public int shakeMillisLeft(){
		return (int) timer.getRemainingMillis();
	}

	public boolean shakerIsDead(){
		return shakeMillisLeft() <= 0;
	}




}
