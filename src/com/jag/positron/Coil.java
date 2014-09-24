package com.jag.positron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.graphics.Color;
import android.os.SystemClock;

import com.jag.framework.Graphics;

public class Coil {

	private ArrayList<Particle> parts;	
	private Graphics g;
	private Collider collider;
	private ArrayList<Strand> strands;
	private ArrayList<Strand> fadeStrands;
	public static Point origin;
	boolean charged;
	private int sw;
	private int sh;
	private int radius;
	//	private int partNum = 0;
	private boolean strandClear;
	private PosTimer fadeTimer;
	private PosTimer nextChargedBolt;
	private PosTimer nextChargedBolt2;
	Random rand;
	private double skullTiplyer;
	private boolean strike;
	//	private Object strandNum;
	private Point strikePoint;
	private Shaker shakey;
	private Random randstrom;
	public static int bAlpha;
	public int skw;
	public int skh;

	public Coil(Graphics g, Collider collider){
		this.g = g;
		this.collider = collider;
		strands = new ArrayList<Coil.Strand>();
		sw = GameScreen.screenwidth;
		sh = GameScreen.screenheight;	
		radius = 120;
		origin = new Point(sw/2, (int) Math.round(sh * .910));
		this.parts = collider.getParticles();
		strandClear = false;
		rand = new Random();
		nextChargedBolt = new PosTimer(rand.nextInt(10));
		nextChargedBolt2 = new PosTimer(rand.nextInt(800));
		skullTiplyer = 1.5;
		shakey = new Shaker(50);
		randstrom = new Random();
		skw = Assets.skull.getWidth();
		skh = Assets.skull.getHeight();

	}	

	public void updateAndDraw(boolean charged, boolean lazer){
		nextChargedBolt.update();
		nextChargedBolt2.update();

		//		(headLoc = (charged) ?  : Math.round(sh * .910);)

		if (charged){
			//			origin.y = (int) (Math.round(sh * .910)+((sh+5)-Math.round(sh * .910))/2);
			origin.y = (int) Math.round(sh * .80);
		}
		else origin.y = (int) Math.round(sh * .80); 

		this.parts = collider.getParticles();
		if (!charged) strandClear = false;

		// NOTCHARGED NUMBER
		if (strands.size() < 10 && !charged){
			strands.add(new Strand());
		}

		// SKULL NUMBER
		else if (!strike){			
			if (!strandClear && charged) {
				strands.clear();
				strandClear = true;
			}
			//
			//			//			//SET SIZE OF CHARGED STRANDARRAY
			//			while (strands.size() < 5 && charged && nextChargedBolt.getTrigger()){
			//				//				System.out.println("strands: "+ strands.size());
			//				strands.add(new Strand());
			//				nextChargedBolt = new PosTimer(rand.nextInt(300));
			//				//				strandClear = false;
			//			}
			//			while (strands.size() < 5 && charged && nextChargedBolt2.getTrigger()){
			//				//				System.out.println("strands: "+ strands.size());
			//				strands.add(new Strand());
			//				nextChargedBolt2 = new PosTimer(rand.nextInt(800));
			//				//				strandClear = false;
			//			}
		}

		// STRIKE NUMBER
		else if (strands.size() < 5){
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
		bAlpha = 0;
		int boltAlph = charged ? 180 : 230;

		if (strike){
			if (fadeTimer == null){
				fadeTimer = new PosTimer(350);
			}
			if (!fadeTimer.getTrigger() && strike) {
				fadeTimer.update();
			}
			int boltTimer = (int) fadeTimer.getRemainingMillis();
			bAlpha = (boltTimer < 200) ? boltTimer : 220;
			bAlpha = (bAlpha < 0) ? 0 : bAlpha;
		}
		


		for (Strand s : strands){
			int boltCol = Color.CYAN;

			if (!lazer && !collider.getDeath() && !CougarLock.active && !strike){
				if (charged)
					//					boltAlph = bAlpha;
					boltAlph = (boltAlph<0) ? 0 : boltAlph;
				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), boltAlph, Color.BLUE, 7, charged, strike);
				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), boltAlph, boltCol, 4, charged, strike);
			}			
			// DRAW STRIKE
			else if (charged && strike){
				boltAlph = bAlpha;
				g.drawCircFill(s.boltOrigin.x, s.boltOrigin.y, 3, Color.MAGENTA, boltAlph);
				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), boltAlph, Color.BLUE, 7, charged, strike);
				g.drawPointBoltPath(s.getPoints(), s.getPoints().size(), boltAlph, boltCol, 5, charged, strike);
			}

			// COIL CONTACT POINTS
			if (!charged && !CougarLock.active) {
				g.drawCircFill(s.outer.x, s.outer.y, 2, Color.MAGENTA, 200);
			}

		}
		double skullShrinkMult = .3;


		if (!charged){
			if (skullTiplyer > .075)
				skullTiplyer-=skullShrinkMult;
			if (skullTiplyer < .075)
				skullTiplyer = .075;
			//			skullTiplyer = .25;
			//			g.drawCircFill(sw/2, headLoc, headSize, headCol, 255);			
			g.drawScaledImage(Assets.skull, (int)(sw/2-skw*skullTiplyer/2), (int) (origin.y - skh*skullTiplyer/2), (int)(skw*skullTiplyer), (int)(skh*skullTiplyer), 0, 0, skw, skh);
		}
		else if (lazer){
			g.drawScaledImage(Assets.skull, (int)(sw/2-skw*skullTiplyer/2), (int) ((origin.y - skh*skullTiplyer/2)+(skh*skullTiplyer/3)), (int)(skw*skullTiplyer), (int)(skh*skullTiplyer), 0, 0, skw, skh);

			skw = Assets.coolSkull.getWidth();
			skh = Assets.coolSkull.getHeight();			

			g.drawScaledImage(Assets.coolSkull, (int)(sw/2-skw*skullTiplyer/2), (int) ((origin.y - skh*skullTiplyer/2)+(skh*skullTiplyer/3)), (int)(skw*skullTiplyer), (int)(skh*skullTiplyer), 0, 0, skw, skh, bAlpha);
		}
		else {
			shakey.resetTimer();
			shakey.update();
			int shiftsterX = 0;
			int shiftsterY = 0;
			if (shakey.getxShift()){
				shiftsterX += shakey.getShifter();
			}
			else {
				shiftsterY += shakey.getShifter();
			}
			if (skullTiplyer < .7)
				skullTiplyer+=skullShrinkMult;
			if (skullTiplyer > .7)
				skullTiplyer = .7;
			g.drawScaledImage(Assets.skull, (int)(sw/2-skw*skullTiplyer/2)+shiftsterX, (int) ((origin.y - skh*skullTiplyer/2)+(skh*skullTiplyer/3))+shiftsterY, (int)(skw*skullTiplyer), (int)(skh*skullTiplyer), 0, 0, skw, skh);
		}
	}
	
	public int skullGlassesX(boolean dir){
		if (dir)
			return (int) (origin.x+skw*(skullTiplyer/2)/6);
		else return (int) (origin.x-skw*(skullTiplyer/2)/6);
	}

	public void setStrike(int x, int y)
	{
//		if (strikePoint != null)
//		System.out.println("x: "+x+" sX: "+ strikePoint.x);
		if (!strike || (strikePoint.x != x && strikePoint.y != y+Assets.pos.getHeight()/2)){
			strike = true;		
			strikePoint = new Point(x, y+Assets.pos.getHeight()/2);
			if (fadeTimer != null) {
				fadeTimer.reset();
			}
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

		private ArrayList<Point> points;
		Point outer;
		Point chargedOuter;

		private Point outerPoint;
		private double radian;
		private PosTimer lifeTime;
		private double mover;
		//		private int strandNum;
		private PosTimer chargedLifetime;
		private double degs;
		private double chargedRadius;
		private int strandNum;
		private Point boltOrigin;
		
		

		private Strand(){
			points = new ArrayList<Point>();
			outer = getOuter(radius, origin);
			degs = getChargedRadian(origin);
			Random randTime = new Random();
			lifeTime = new PosTimer(randTime.nextInt(1500)+150);
			chargedLifetime = new PosTimer(1500);
			strike = false;
		}

		public void update(boolean charged){

			// NOT CHARGED:
			if (!charged){
				setPoints(10, radius, charged);
				slideOuter();
				points.add(outer);	
			}
			// SKULL CHARGE
			else if (!strike){
				setPoints(8, radius*3, charged);
			}

			// STRIKE
			else setPoints(20, sh, charged);
		}

		// PointsPerLine, LineLen (this is radius for !charged), 
		private void setPoints(int pointsPer, int lineLen, boolean charged) {
			if (!charged)
				lifeTime.update();
			else chargedLifetime.update();

			if (!charged){
				points.clear();
			}
			if (strike) {
				points.clear();
			}

			// FIRST POINT
			Point point;
			if (points.size() == 0){
				if (strike) {
					if (boltOrigin == null) {
						boltOrigin = skullPoint((int)(Assets.skull.getWidth()*skullTiplyer/1.85));
						while (boltOrigin.y < strikePoint.y)
							boltOrigin = skullPoint((int)(Assets.skull.getWidth()*skullTiplyer/1.85));
					}
					point = boltOrigin;
				}
				else if (charged){
					point = skullPoint((int)(Assets.skull.getWidth()*skullTiplyer/1.85));
				}					
				else point = new Point(origin.x, origin.y);
				points.add(point);
			}

			Point prevPoint = (strike) ? boltOrigin : origin;
			// point to track bolt line
			Point linePoint = boltOrigin;
			int mulTensity = (charged) ? 100 : 20;
			int pointsPerLine = pointsPer;
			int radianPointSpacer = lineLen/pointsPerLine;
			boolean dir = randstrom.nextBoolean();

			for (int j = 0 ; j < pointsPerLine ; j++){				

				int xMult = randstrom.nextInt(mulTensity) - (mulTensity/2);
				int yMult = randstrom.nextInt(mulTensity) - (mulTensity/2);

				int neXtPoint = 0;
				int neYtPoint = 0;
				Point midPoint = null;

				// COIL STRANDS
				if (!charged && !strike){
					neXtPoint = (int) (prevPoint.x + (radianPointSpacer * Math.cos(radian)) + xMult);
					neYtPoint = (int) (prevPoint.y + (radianPointSpacer * Math.sin(radian)) + yMult);

					midPoint = new Point(neXtPoint, neYtPoint);

					if (boundsCheck(midPoint))
						points.add(midPoint);				
				}

				// STRIKE STRANDS
				else if (strike && points.size() < pointsPerLine){

					degs = getStrikeRadian();

					// YMULT FOR HORIZ STRIKE
					//					if (degs < .2 || > 2.8) {
					//						
					//					}
					//					else 					

					xMult = randstrom.nextInt(mulTensity);							

					if (!dir){
						xMult = -xMult;
						dir = !dir;
					}
					else dir = !dir;

					int pureX = (int) (linePoint.x + (radianPointSpacer * -Math.cos(degs)));

					neXtPoint = (int) (prevPoint.x + (radianPointSpacer * -Math.cos(degs) + xMult));
					neYtPoint = (int) (prevPoint.y - (radianPointSpacer * Math.sin(degs)));

					if (Math.abs(neXtPoint - pureX) > 100 && neYtPoint > strikePoint.y){
						neXtPoint = pureX;
					}

					if (neYtPoint < strikePoint.y) {
						midPoint = strikePoint;
					}
					else midPoint = new Point(neXtPoint, neYtPoint);

					points.add(midPoint);

					linePoint = new Point(pureX, neXtPoint);


				}

				// SKULL STRANDS
				else if (points.size() < pointsPerLine){
					xMult = randstrom.nextInt(mulTensity);
					if (!dir){
						xMult = -xMult;
						dir = !dir;
					}
					else dir = !dir;
					yMult = randstrom.nextInt(mulTensity)/3;
					neXtPoint = (int) (prevPoint.x + (radianPointSpacer * Math.cos(degs) + xMult));
					neYtPoint = (int) (prevPoint.y + (radianPointSpacer * Math.sin(degs)));

					midPoint = new Point(neXtPoint, neYtPoint);
					points.add(midPoint);
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

		private double getChargedRadian(Point origin) {			
			Random randy = new Random();

			int pointX = randy.nextInt(GameScreen.screenwidth);	
			int pointY = randy.nextInt(50)+950;
			outerPoint = new Point(pointX, pointY);

			double deg = Math.atan2(outerPoint.y-origin.y, outerPoint.x-origin.x);

			return deg;
		}


		private double getStrikeRadian() {	
			Random randy = new Random();

			double deg = Math.atan2(boltOrigin.y-strikePoint.y, boltOrigin.x-strikePoint.x);
			return deg;
		}

		private Point getBoltOrigin(){
			Random randy = new Random();
			if (boltOrigin == null) {
				strandNum = randy.nextInt(parts.size());
				int pointX = (int) parts.get(strandNum).x;	
				int pointY = (int) parts.get(strandNum).y;

				boltOrigin = new Point(pointX, pointY);				
			}
			return boltOrigin;
		}
	}

	private Point skullPoint(int radius) {
		Random random = new Random();
		float angleInDegrees = random.nextInt(180)+180;

		// Convert degrees to radians, set point coords
		int x = (int) ((float)(radius * Math.cos(angleInDegrees * Math.PI / 180F)) + origin.x);
		int y = (int) ((float)(radius * Math.sin(angleInDegrees * Math.PI / 180F)) + origin.y + radius/2);

		return new Point(x, y);
	}
}