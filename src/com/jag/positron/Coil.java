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
	private ArrayList<Strand> fadeStrands;
	private Point origin;
	boolean charged;
	private int sw;
	private int sh;
	private int radius;
	//	private int partNum = 0;
	private boolean strandClear;

	public Coil(Graphics g, Collider collider){
		this.g = g;
		this.collider = collider;
		strands = new ArrayList<Coil.Strand>();
		sw = GameScreen.screenwidth;
		sh = GameScreen.screenheight;	
		radius = 90;
		origin = new Point(sw/2, (int) Math.round(sh * .910));
		this.parts = collider.getParticles();
		strandClear = false;
	}	

	public void updateAndDraw(boolean charged, boolean lazer){
		this.parts = collider.getParticles();

		if (!charged) strandClear = false;
		// !CHARGED NUMBER
		if (strands.size() < 10 && !charged){
			strands.add(new Strand());
		}

		// CHARGED NUMBER
		else {
			if (!strandClear && charged) {
				strands.clear();
				strandClear = true;
			}

			//SET SIZE OF CHARGED STRANDARRAY
			while (strands.size() < 1 && charged){
				//				System.out.println("strands: "+ strands.size());
				strands.add(new Strand());
//				strandClear = false;
			}
			//						System.out.println(strands.size());
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
			else {
				//				if (strands.size() < 5)
				if (!s.chargedLifetime.getTrigger())
					s.update(charged);
				else it.remove();
			}			
		}
		draw(charged, lazer);

	}

	private boolean boundsCheck(Point midPoint) {
		int xCheck = (int) Math.pow((midPoint.x - origin.x),2);
		int yCheck = (int) Math.pow((midPoint.y - origin.y),2);

		return (xCheck + yCheck) < Math.pow(radius,2);
	}

	public void draw(boolean charged, boolean lazer){
		g.drawLine(sw/2, sh+5, sw/2, (int) Math.round(sh * .910), Color.GRAY, 255, 3);		

		//		Random randy = new Random();

		int boltAlph = charged ? 180 : 230;

		for (Strand s : strands){
			int boltCol = Color.CYAN;

			if (charged){
				//				if (randy.nextInt(10) == 9) boltCol = Color.MAGENTA;
				//				if (charged && randy.nextInt(100) < 10) boltAlph = 0;
				//				else if (randy.nextInt(10) == 8) boltCol = Color.MAGENTA;
//				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), 150, Color.BLUE, 15, true);
			}

			if (!lazer && !collider.getDeath() && !CougarLock.running){		
				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), 100, Color.BLUE, 7, charged);
				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), boltAlph, boltCol, 4, charged);
				

			}
			if (!charged && !CougarLock.running)
				g.drawCircFill(s.outer.x, s.outer.y, 2, Color.MAGENTA, 200);
		}

		int headCol;
		headCol = (lazer || CougarLock.running) ? Color.GRAY : Color.CYAN;
		g.drawCircFill(sw/2, Math.round(sh * .910), 7, headCol, 255);


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
		Point chargedOuter;

		private Point outerPoint;
		private double radian;
		private PosTimer lifeTime;
		private double mover;
		//		private int strandNum;
		private int strandNum;
		private PosTimer chargedLifetime;


		private Strand(){
			points = new ArrayList<Point>();
			outer = getOuter(radius, origin);
			chargedOuter = getChargedOuter();
			Random randTime = new Random();
			lifeTime = new PosTimer(randTime.nextInt(1500)+150);
			chargedLifetime = new PosTimer(randTime.nextInt(400)+50);
			//			strandNum = strands.size();

		}

		public void update(boolean charged){
			
			// NOT CHARGED:
			if (!charged){
				//				System.out.println("falseCalls");
				setPoints(10, radius, charged);
				slideOuter();
				points.add(outer);	
			}
			else {
				//				System.out.println("setCall");
				setPoints(8, 7, charged);				
				//				points.add(chargedOuter);
				//				points.add(new Point(400, 400));
			}
		}

		// PointsPerLine, LineLen (this is radius for !charged), 
		private void setPoints(int pointsPer, int lineLen, boolean charged) {
			if (!charged)
				lifeTime.update();
			else chargedLifetime.update();

			Random randstrom = new Random();

			if (!charged)
				points.clear();

			// FIRST POINT
			Point point;
			if (points.size() == 0){
				point = new Point(origin.x, origin.y);
				points.add(point);
			}
			//			if (charged)
			//				System.out.println("1st: "+point.x+"    "+point.y);

			Point prevPoint = origin;
			int mulTensity = 20;
			int pointsPerLine = pointsPer;
			int radianPointSpacer = lineLen/pointsPerLine;	

			for (int j = 0 ; j < pointsPerLine ; j++){			

				int xMult = randstrom.nextInt(mulTensity) - (mulTensity/2);
				int yMult = randstrom.nextInt(mulTensity) - (mulTensity/2);

				int neXtPoint = 0;
				int neYtPoint = 0;
				Point midPoint = null;


				if (!charged){
					neXtPoint = (int) (prevPoint.x + (radianPointSpacer * Math.cos(radian)) + xMult);
					neYtPoint = (int) (prevPoint.y + (radianPointSpacer * Math.sin(radian)) + yMult);

					midPoint = new Point(neXtPoint, neYtPoint);

					if (boundsCheck(midPoint))
						points.add(midPoint);				
				}

				else if (points.size() < pointsPerLine){					
					// calculate slope using getOuter/origin, apply the shift along the line at each point, set the end.
					int xSlope = getChargedOuter().x - origin.x;
					xSlope = (xSlope == 0) ? 1 : xSlope	;
					int ySlope = getChargedOuter().y - origin.y;
					ySlope = (ySlope == 0) ? 1 : ySlope;					
					neXtPoint = (int) (origin.x + (j+1)*(xSlope/pointsPerLine) + xMult*2);
					neYtPoint = (int) (origin.y + (j+1)*(ySlope/pointsPerLine) - Math.abs(yMult));

					midPoint = new Point(neXtPoint, neYtPoint);

 					if (midPoint.y > getChargedOuter().y){
						points.add(midPoint);
//						System.out.println(j+": " + midPoint.x + "   "+midPoint.y);
						/// LOOPS BACK TO 0 AND ADDS POINT FOR SOME REASON
					}
//					else j--;

				}

				prevPoint = midPoint;
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

		/**
		 * This will give the correct outer point for a strand while it's moving
		 * @return
		 */
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

		
		private Point getChargedOuter() {
//			if (strandNum == 0){
				Random randy = new Random();
				strandNum = randy.nextInt(parts.size());
				strandNum = (strandNum == 0) ? 1 : strandNum;
//			}
//
//			int pointX = (int) parts.get(strandNum-1).x;
//			int pointY = (int) parts.get(strandNum-1).y;
			
			int pointX = randy.nextInt(GameScreen.screenwidth);
			int pointY = randy.nextInt(50)+900;

			outerPoint = new Point(pointX, pointY);

			return outerPoint;
//			return new Point(400, 0);
		}
	}
}