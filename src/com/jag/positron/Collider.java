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
	private Graphics g;
	private boolean chargeSwitch;
	private int line;
	ArrayList<Integer> lines;
	private PosTimer timey;

	private Collider(){
		lines = new ArrayList<Integer>();
		g = GameScreen.thisGame.getGraphics();
		set = false;
		charged = false;
		lazer = false;
		parts = new ArrayList<Particle>();
		Random rand = new Random();
		for (int i = 0; i < 100 ; i++){
			float speed = rand.nextFloat()/10+.1f;
			parts.add(new Particle(GameScreen.thisGame.getGraphics(), 25, speed, rand.nextFloat()*4	+.8f, rand.nextFloat()*7+4));
		}
		bolt = new Bolt(g, this);
		chargeSwitch = false;
	}


	public void update(){
		for (Particle p: parts){
			p.move();
			p.draw();
		}

		if (chargeSwitch != charged){
			pinkFlash();
			chargeSwitch = charged;
		}

		if (charged && !lazer)
			chargeLine(line);

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
		this.line = line;
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

	private void chargeLine(int line) {
		if (timey == null)
			timey = new PosTimer(200);
		int spacer = 12;
		
		g.drawLine(line, -2, line, GameScreen.screenheight+5, Color.CYAN, 255, 6);
		for (int i = 0 ; i < 3 ; i++){
			lines.add(line);
		}
		
		for (int i = 0 ; i < 3 ; i++){
			if (line < lines.get(i)-spacer-(i*spacer)){
				lines.set(i, line+spacer+(i*spacer));
			}
			if (line > lines.get(i)+spacer+(i*spacer)){
				lines.set(i, line-spacer-(i*spacer));
			}
			int alpher = 150-(spacer*(i+1)*2);
			
			if (alpher < 0) alpher = 0;
			
			g.drawLine(lines.get(i), -2, lines.get(i), GameScreen.screenheight+5, 
					Color.CYAN, alpher, 6-i);		
		}
	}

	public void pinkFlash() {
		g.drawRect(0, 0, GameScreen.screenwidth+5, 
				GameScreen.screenheight+5, Color.MAGENTA, 175);
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
		bolt.strike(x, y);
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
