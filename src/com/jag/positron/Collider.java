package com.jag.positron;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;

import com.jag.framework.Graphics;

public class Collider {
	public ArrayList<Particle> parts;	
	private static Collider theCollider;
	public boolean charged;
	public int backSpriteLoc;
	private boolean set;
	private boolean initialSide;
	private PosTimer pt;
	private boolean lazer;
	private Bolt bolt;


	private Collider(){
		set = false;
		charged = false;
		lazer = false;
		parts = new ArrayList<Particle>();
		Random rand = new Random();
		for (int i = 0; i < 100 ; i++){
			float speed = rand.nextFloat()/10+.1f;
			parts.add(new Particle(GameScreen.thisGame.getGraphics(), 25, speed, rand.nextFloat()*4	+.8f, rand.nextFloat()*7+4));
		}
		bolt = new Bolt(GameScreen.thisGame.getGraphics(), this);
	}


	public void update(){
		for (Particle p: parts){
			p.move();
			p.draw();
		}
	}	

	public void charge(){
		if (!charged){
			Random randomGenerator = new Random();
			int randInt;
			int thisLane;
			for (Particle p: parts){
				p.setCharge(true);
				randInt = randomGenerator.nextInt(7);
				thisLane = (randInt + 1) * GameScreen.lane;
				p.setLane(thisLane);
			}
		}
		charged = true;
	}
	
	public void checkCharged(int spriteX, int spriteY, int line){
		Graphics g = GameScreen.thisGame.getGraphics();
		if (!set){
			set = true;
			if (line < spriteX)
				// line is left of sprite 
				initialSide = false;
			else
				// line is right of sprite
				initialSide = true;
		}
		if (!initialSide && line >= spriteX){
			charge();
			
		}
		if (initialSide && line < spriteX){
			charge();
			
		}

		if (charged){
			if (!initialSide && line < spriteX){
				lazer = true;
				lazer(spriteX, spriteY);
			}
			if (initialSide && line >= spriteX){
				lazer = true;
				lazer(spriteX, spriteY);
			}
		}
	}

	public void death(){
		if (pt == null)
			pt = new PosTimer(4000);
		if (!pt.getTrigger()){
			for (Particle p: parts){
				p.setDead();
			}
			pt.update();
		}		
		
	}
	
	public void lazer(int x, int y){
		for (Particle p: parts){
			p.setLazer(x, y);
		}
		bolt.strike();
	}

	public boolean isLazer() {
		return lazer;
	}

	public void setLazer(boolean lazer) {
		this.lazer = lazer;
	}	
	
	public boolean isSet(){
		return set;
	}
	
	public ArrayList<Particle> getParticles(){
		return parts;
	}

	public void reset(){
		theCollider = new Collider();
	}

	public static Collider getInstance(){
		if (theCollider == null){
			theCollider = new Collider();
		}
		return theCollider;
	}
}
