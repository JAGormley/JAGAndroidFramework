package com.jag.positron;

import android.graphics.LinearGradient;
import android.os.SystemClock;

public class Fader {
	float fadeTime;
	float startTime;
	int textStart;
	float elapsedTime;
	float textRadius;
	private boolean alive;
	private boolean firstUpdate;
	
	public Fader (float fadeTime, int textStart, double diameter){
		this.fadeTime = fadeTime;
		this.textStart = textStart;
		textRadius = (float) (diameter/2);
		alive = true;
		firstUpdate = true;
	}

	public void update() {
		if (firstUpdate){
			startTime = SystemClock.elapsedRealtime();
			firstUpdate = false;
		}
		if (alive)
		elapsedTime =  (SystemClock.elapsedRealtime() - startTime)/1000;
//		System.out.println("elTime "+elapsedTime);
	}

	public float getStart() {
//		System.out.println("start "+ (textStart-textRadius));
		return textStart+textRadius - elapsedTime*textRadius*4;
	}

	public float getEnd() {
		if (elapsedTime < fadeTime){
//			System.out.println("end "+(textStart+textRadius - elapsedTime*textRadius*2/fadeTime	));
			return textStart+textRadius*3 - elapsedTime*textRadius*4/fadeTime;			
		}
		else{			
			alive = false;
			return getStart()+1;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
	

	
}
