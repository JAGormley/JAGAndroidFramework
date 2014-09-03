package com.jag.positron;

import android.os.SystemClock;
import java.lang.Math;

/**
 * 
 * @author jandrewgormley
 * Timer counts down from initial provided value
 */

public class PosTimer {
	long startTime = 0;
	long elapsedTime = 0;
	double length;
	boolean trigger;
	private long currentTime = 0;
	private long breakTime = 0;
	private long endTime = 0;

	/**  length of timer in millis */
	public PosTimer(double d){
		this.length = d;
		trigger = false;
	}

	public void update(){
		long gapTime = 0;
		if (startTime == 0)
			startTime = SystemClock.elapsedRealtime();
		else {
			currentTime = SystemClock.elapsedRealtime();
			gapTime = currentTime - endTime;
			breakTime += Math.round(gapTime/100)*100;
		}
			elapsedTime = (currentTime - startTime - breakTime);
			if (elapsedTime < 0)
				elapsedTime = 0;
		
		if (elapsedTime > length){
			trigger = true;
		}
		endTime = SystemClock.elapsedRealtime();
	}

	public void reset(){
		startTime = 0;
		elapsedTime = 0;
		endTime = 0;
		currentTime = 0;
		breakTime = 0;
		trigger = false;
	}

	public double getRemainingMillis(){
		return (length - elapsedTime);
	}
	
	public double getElapsedMillis(){
		return elapsedTime;
	}

	public double getTotalMillis() {
		return length;
	}
	
	public boolean getTrigger(){
		return trigger;
	}
}
