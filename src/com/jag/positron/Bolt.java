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
	private PosTimer fadeTimer;

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
		
		for (int i = 0; points.size() < parts.size()*3; i++){
			ArrayList<Integer> point = new ArrayList<Integer>();
			ArrayList<Integer> mPoint = new ArrayList<Integer>();
			ArrayList<Integer> ePoint = new ArrayList<Integer>();
			this.parts = this.collider.getParticles();
			int partX = (int) parts.get(i).getX();
			int partY = (int) parts.get(i).getY();
//			System.out.println("partX: " +partX);
//			System.out.println("partY: " +partY);

			point.add(partX);
			point.add(partY);
			points.add(point);

			//			if (x < Math.abs((int) parts.get(i).getX()){
			//			System.out.println("Abs X: "+Math.abs(partX - x));

			for (int j = 0 ; j < 2 ; j++){
				mPoint = new ArrayList<Integer>();
				int absMathX = Math.abs(partX - spriteX);
				int absMathY = Math.abs((partY-20) - (spriteY)); 

				if (spriteX < partX)
					mPoint.add(spriteX + (randstrom.nextInt(absMathX)+1));				
				else 
					mPoint.add(partX + (randstrom.nextInt(absMathX+1)));
				mPoint.add(spriteY + (randstrom.nextInt(absMathY+1)));	
				points.add(mPoint);
			}

			ePoint.add(spriteX);
			ePoint.add(spriteY);
			points.add(ePoint);
		}
		
		int fadeAlph = (int) fadeTimer.getRemainingMillis();
		if (fadeAlph < 0) fadeAlph = 0;
//		g.drawBoltPath(points, 4, fadeAlph/2);
		g.drawBoltPath(points, 4, 255);
		g.drawCircOut(spriteX, spriteY, (float) ((2000-fadeAlph*4)*1.5), Color.BLUE, fadeAlph/50, (fadeAlph-250)/5);
		g.drawCircOut(spriteX, spriteY, (float) ((2000-fadeAlph*4)*1.55), Color.CYAN, fadeAlph/50, (fadeAlph-250)/5);
		g.drawCircOut(spriteX, spriteY, (float) ((2000-fadeAlph*4)*1.6), Color.MAGENTA, fadeAlph/50, (fadeAlph-250)/5);
//		System.out.println("points: "+points.toString());

	}
}

