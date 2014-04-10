package com.jag.positron;

import java.util.ArrayList;
import java.util.Random;

import com.jag.framework.Graphics;

public class Bolt {
	private Graphics g;
	private Collider collider;
	private ArrayList<Particle> parts;	
	private ArrayList<ArrayList<Integer>> points;
	
	public Bolt(Graphics g, Collider collider){
		this.g = g;		
		this.collider = collider;
		this.parts = this.collider.getParticles();
		points = new ArrayList<ArrayList<Integer>>();
	}
	
	public void strike(){				
		Random randstrom = new Random();	
		
		for (int i = 0; points.size() < 20; i++){
			ArrayList<Integer> point = new ArrayList<Integer>();
			point.add(randstrom.nextInt(800));
			point.add(randstrom.nextInt(1000));
			points.add(point);
		}
		g.drawPath(points);
	}
	
	
	private ArrayList<Float> getLocs(){
		ArrayList<Float> xs;
		return null;
	}
	

}

