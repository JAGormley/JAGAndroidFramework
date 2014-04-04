package com.jag.positron;

import java.util.Random;

import com.jag.framework.Graphics;
import com.jag.framework.Screen;

import android.graphics.Color;
import android.os.SystemClock;

public class Particle {

	float x;
	float y;
	float startx;
	float starty;
	Graphics g;
	float speed;
	boolean clockwise;
	private int size;
	private float speedCounter;
	private boolean negSin;
	private float heightMult;
	private float widthMult;
	private boolean charged;
	private int lane;
	private float speedAdder;
	public static boolean dead;

	/**
	 * 
	 * @param g: Graphics object
	 * @param size: size of the curve, range =~ width/10 --> width/3
	 * @param speedCounter: range =~ .01 --> 1
	 */
	public Particle(Graphics g, int size, float speedCounter, float heightMult, float widthMult) {
		startx = 1f;
		starty = 1f;
		charged = false;
		dead = false;
		this.g = g;
		clockwise = true;
		this.speedCounter = speedCounter;
		this.size = size;
		this.heightMult = heightMult;
		this.widthMult = widthMult;
		speedAdder = 13f;
	}	

	public void move(){
		float sin = 0;
		float cos = 0;
		float offset = 2*size*widthMult;
		
		if (!charged){
			sin = (float)Math.sin(speed)*size;
			cos = (float)Math.cos(speed)*size*widthMult;
		}
		else {
			sin = (float)Math.sin(speed*2)*size/3;
			cos = (float)Math.cos(speed*2)*size*widthMult/3	; 
			offset = 0;
		}

		speed += speedCounter*speedAdder;
		if (speedAdder > 1)
		speedAdder *= .85;

		// switch left/right
		if (sin < 0) {
			negSin = true;
		}
		if (negSin && sin > 0){
			clockwise = !clockwise;
			negSin = false;
		}

		// right side of lemniscate:
		if (clockwise){
			if (sin > 0){
				x = startx*cos;
			}
			else{
				x = startx*-cos - offset;
			}
		}
		// left side of lemniscate:
		else {
			if (sin > 0){
				x = -startx*cos - offset;		
			}
			else{
				x = startx*cos ;
			}
		}
		y = starty*sin*heightMult;
		
		if (dead){
			Random randy = new Random();
			x += randy.nextInt(1200)-300;
			y -= randy.nextInt(1200)-300;				
		}

		if (!charged){
			x += (g.getWidth()/4)*3;
			y += (g.getHeight()/12)*11;
			x -= 197.5 - size*widthMult;
		}
		else {
			x += lane;
			y += (g.getHeight()/12)*11;
			//			x -= 197.5 - size*widthMult;
		}


	}

	public void setLane(int lane){
		this.lane = lane;
	}
	public void setCharge(boolean x){
		charged = x;
	}
	public boolean isDead(){
		return dead;
	}
	public void setDead(){
		dead = true;
	}

	public void draw(){

		g.drawCircFill(x, y, 7, Color.BLUE, 255);
		if (charged){
			g.drawCircFill(x, y, 4, Color.MAGENTA, 255);
		}
		//		g.drawLine((int)x-6, (int)y, (int)x+6, (int)y, Color.BLUE, 255, 4);
		//		g.drawLine((int)x, (int)y-6, (int)x, (int)y+6, Color.BLUE, 255, 4);
		//		g.drawLine(g.getWidth()/2, 0, g.getWidth()/2, 1200, Color.GREEN, 255, 8);
	}

}

