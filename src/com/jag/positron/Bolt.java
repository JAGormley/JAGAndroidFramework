package com.jag.positron;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;

import com.jag.framework.Graphics;

public class Bolt {
	private Graphics g;
	private Collider collider;
	private ArrayList<Particle> parts;	
	private ArrayList<ArrayList<Integer>> points;
	public static PosTimer fadeTimer;

	public Bolt(Graphics g, Collider collider){
		this.g = g;
		this.collider = collider;
		this.parts = this.collider.getParticles();
		points = new ArrayList<ArrayList<Integer>>();
		fadeTimer = new PosTimer(500);
	}

	public void strike(int spriteX, int spriteY){	
		
		Random randstrom = new Random();	

		if (!fadeTimer.getTrigger())
			fadeTimer.update();

		if (points.size() == parts.size()*3)
			points.clear();
		
		int fadeAlph = (int) fadeTimer.getRemainingMillis();
		if (fadeAlph < 0) fadeAlph = 0;

	}
	
	public static void resetTimer(){
		fadeTimer.reset();
	}
}

