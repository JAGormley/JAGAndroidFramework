package com.jag.positron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.graphics.Color;

import com.jag.framework.Graphics;

public class Coil {

	private ArrayList<Particle> parts;	
	private Graphics g;
	private Collider collider;
	private ArrayList<Strand> strands;
	private Point origin;
	boolean charged;
	private int sw;
	private int sh;
	private int radius;
	private int partNum = 0;

	public Coil(Graphics g, Collider collider){
		this.g = g;
		this.collider = collider;
		strands = new ArrayList<Coil.Strand>();
		sw = GameScreen.screenwidth;
		sh = GameScreen.screenheight;	
		radius = 90;
		origin = new Point(sw/2, (int) Math.round(sh * .910));
		this.parts = collider.getParticles();
	}	

	public void updateAndDraw(boolean charged){
		this.parts = collider.getParticles();
		
		// !CHARGED NUMBER
		if (strands.size() < 10 && !charged){
			strands.add(new Strand());
		}
		// CHARGED NUMBER
		else if (strands.size() < 20 && charged){
			strands.add(new Strand());
		}


		Iterator<Strand> it = strands.iterator();
		while (it.hasNext()) {
			Strand s = it.next();
			// NOT CHARGED
			if (!charged){
				if (!s.lifeTime.getTrigger())
					s.update(charged);
				else it.remove();
			}
			// CHARGED
			else s.update(charged);
		}
		draw(charged);

	}

	private boolean boundsCheck(Point midPoint) {
		int xCheck = (int) Math.pow((midPoint.x - origin.x),2);
		int yCheck = (int) Math.pow((midPoint.y - origin.y),2);

		return (xCheck + yCheck) < Math.pow(radius,2);
	}


	public void draw(boolean charged){
		g.drawLine(sw/2, sh+5, sw/2, (int) Math.round(sh * .910), Color.GRAY, 255, 3);				
		g.drawCircFill(sw/2, Math.round(sh * .910), 7, Color.CYAN, 255);				

		System.out.println(strands.size());
		for (Strand s : strands){
			
			g.drawPointBoltPath(s.getPoints(), 2, 255);
			if (!charged)
			g.drawCircFill(s.outer.x, s.outer.y, 2, Color.MAGENTA, 200);
		}
		

	}
	public class Point{
		public int x;
		public int y;
		private Point(int x, int y){
			this.x=x;
			this.y=y;	
		}		
	}

	public class Strand{

		// FINISH THIS CLASS

		private ArrayList<Point> points;
		Point outer;

		private Point outerPoint;
		private double radian;
		private PosTimer lifeTime;
		private double mover;
		

		private Strand(){
			points = new ArrayList<Point>();
			outer = getOuter(radius, origin);
			Random randTime = new Random();
			lifeTime = new PosTimer(randTime.nextInt(1500)+150);

		}

		public void update(boolean charged){

			// NOT CHARGED:
			if (!charged){
				setPoints(10, radius, charged);
				slideOuter();
				points.add(outer);
			}
			else {					
				setPoints(2, 10, charged);
				points.add(getChargedOuter());

			}

		}

		// PointsPerLine, LineLen (this is radius for !charged), 

		private Point getChargedOuter() {
			
			int pointX = (int) parts.get(strands.size()).x;
			int pointY = (int) parts.get(strands.size()).y;
			
			return new Point(pointX, pointY);
		}

		private void setPoints(int pointsPer, int lineLen, boolean charged) {
			if (!charged)
				lifeTime.update();
			Random randstrom = new Random();

			points.clear();

			///FIRST POINT
			Point point;					
			point = new Point(origin.x, origin.y);
			points.add(point);

			Point prevPoint = origin;
			int mulTensity = 20;
			int pointsPerLine = pointsPer;
			int radianPointSpacer = lineLen/pointsPerLine;

			for (int j = 0 ; j < pointsPerLine ; j++){

				int xMult = randstrom.nextInt(mulTensity) - (mulTensity/2);
				int yMult = randstrom.nextInt(mulTensity) - (mulTensity/2);

				int neXtPoint = 0;
				int neYtPoint = 0;

				if (!charged){
					neXtPoint = (int) (prevPoint.x + (radianPointSpacer * Math.cos(radian)) + xMult);
					neYtPoint = (int) (prevPoint.y + (radianPointSpacer * Math.sin(radian)) + yMult);

					Point midPoint = new Point(neXtPoint, neYtPoint);


					//					System.out.println(boundsCheck(midPoint));
					if (boundsCheck(midPoint))
						points.add(midPoint);				
					prevPoint = midPoint;
				}
				else {
					
					// DRAW POINTS ALONG LINE TO PartCoord
				}
			}

		}

		public ArrayList<Point> getPoints(){
			return points;
		}

		private void slideOuter(){

			radian += mover/100;	
			outer.x = (int) ((float)(radius * Math.cos(radian)) + origin.x);
			outer.y = (int) ((float)(radius * Math.sin(radian)) + origin.y);
		}

		private Point getOuter(float radius, Point origin) {
			Random random = new Random();
			float angleInDegrees = random.nextInt(360);

			// Convert degrees to radians, set point coords     
			int x = (int) ((float)(radius * Math.cos(angleInDegrees * Math.PI / 180F)) + origin.x);	
			int y = (int) ((float)(radius * Math.sin(angleInDegrees * Math.PI / 180F)) + origin.y);
			if (outerPoint == null){

				// SET OUTER POINT
				outerPoint = new Point(x, y);
				radian = angleInDegrees * Math.PI / 180;
				// SET MOVER
				Random randy = new Random();
				mover = (double) randy.nextInt(4)-2;

			}
			return outerPoint;
		}
	}
}

// States: charged/notCharged 