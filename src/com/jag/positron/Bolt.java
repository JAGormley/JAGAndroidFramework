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

	public void strike(int x, int y){		
		Random randstrom = new Random();
		

		for (int i = 0; points.size() < parts.size()*3; i++){
			ArrayList<Integer> point = new ArrayList<Integer>();
			ArrayList<Integer> mPoint = new ArrayList<Integer>();
			ArrayList<Integer> ePoint = new ArrayList<Integer>();
			int partX = (int) parts.get(i).getX();
			int partY = (int) parts.get(i).getY();


			point.add(partX);
			point.add(partY);			
			points.add(point);

			//			if (x < Math.abs((int) parts.get(i).getX()){
			//			System.out.println("Abs X: "+Math.abs(partX - x));

			for (int j = 0 ; j < 1 ; j++){
				mPoint = new ArrayList<Integer>();
				int absMathX = Math.abs(partX - x);
				int absMathY = Math.abs((partY-20) - (y+50)); 

				if (x < partX)
					mPoint.add(x + (randstrom.nextInt(absMathX)+1));				
				else 
					mPoint.add(partX + (randstrom.nextInt(absMathX+1)));
				mPoint.add(y+50 + (randstrom.nextInt(absMathY+1)));	
				points.add(mPoint);
			}

			ePoint.add(x);
			ePoint.add(y);
			points.add(ePoint);

		}
		g.drawPath(points, 3);
		System.out.println("points: "+points.size());
	}
}

