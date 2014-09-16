package com.jag.positron;

import java.util.ArrayList;
import java.util.Random;

import com.jag.positron.Level.Obs;

import android.graphics.Color;

public class Level {
	private static Level theLevel;
	private int level;
	private int recentInterval;
	// this is an array that contains number of sprites required to access next level
	private ArrayList<Integer> levelSpriteNumber;
	private ArrayList<Integer> scoreThreshs;
	private ArrayList<Integer> scoreMults;
	private int numLevels;
	public int scoreMult;
	public int cumulativeThresh;
	private int prevLevel;
	private boolean message;
	private Message multMessage;
	private int sw;
	private int sh;
	private PosTimer cougTimer;
	private PosTimer gridTimer;
	private PosTimer falcTimer;
	private Random randy;
	// Obstacle enable/disable
	private boolean cEnable = true;
	private boolean gEnable = true;
	private boolean fEnable = true;
	
	
	public enum Obs {
		COUGAR, GRID, FALCON;
	}

	private Level(){
		sw = GameScreen.screenwidth;
		sh = GameScreen.screenheight;
		randy = new Random();
		level = 1;
		prevLevel = level;
		scoreMults = new ArrayList<Integer>();
		levelSpriteNumber = new ArrayList<Integer>();
		scoreThreshs = new ArrayList<Integer>();
		numLevels = 12;

		// explicitly define scoreMult levels
		scoreMults.add(0);
		scoreMults.add(1);
		scoreMults.add(2);
		scoreMults.add(5);
		scoreMults.add(10);
		scoreMults.add(15);
		scoreMults.add(25);
		scoreMults.add(35);
		scoreMults.add(50);
		scoreMults.add(70);
		scoreMults.add(95);
		scoreMults.add(120);


		// set number of sprites per level
		for (int i = 0; i < numLevels; i++) {	
			levelSpriteNumber.add( (int)(25/((-.41*Math.pow(5*(level+i), 1.1) + 40) * .018)) );
			int accumulator = (i > 0) ? scoreThreshs.get(i-1): 0;
			scoreThreshs.add(scoreMults.get(i)*levelSpriteNumber.get(i) + accumulator);				
		}
		levelSpriteNumber.add(0, 0);
		System.out.println(levelSpriteNumber.toString());
		System.out.println(scoreMults.toString());
		System.out.println(scoreThreshs.toString());
		
	}

	public void update(int score, boolean scoreReset){
//		System.out.println(level);
		if (!scoreReset){
			if (cougTimer != null){
				cougTimer.update();
			}
			if (gridTimer != null){
				gridTimer.update();
			}
			if (falcTimer != null){
				falcTimer.update();
			}
		}

		recentInterval = (int) (-.41*Math.pow(5*level, 1.15)) + 40;
//		System.out.println(recentInterval);
		for (int i = 0; i < scoreThreshs.size()-1; i++) {
			if (score >= scoreThreshs.get(i) && score < scoreThreshs.get(i+1)){
				level = i+1;
//				System.out.println(level);
			}
		}

		if (prevLevel != level){
			if (getScoreMult() != 1)
				message = true;
			multMessage = null;
			prevLevel = level;
		}
	}

	public void displayMessage() {
		if (multMessage == null){
			multMessage = new Message(sw/2, 
					sh/2-(int)(sh * .083), .5f, .5f, 
					"x"+String.valueOf(getScoreMult()), 
					.1f, GameScreen.graph, Color.MAGENTA, true);
			multMessage.setMAlpha(200);
		}
		if (multMessage != null && multMessage.isAlive()){
			multMessage.growM(.2f);
			multMessage.drawMessage();
		}
		else {
			multMessage = null;
			message = false;
		}
	}

	public Integer getSpriteSpeed(){
		return (int) (.47*Math.pow(5*level, 1.1	)) + 15;
	}
	public Integer getBackSpeed(){
		return (int) (.55*Math.pow(level, 2))+ 25;
	}
	public Integer getAccMod(){
		return (12-level+1 < 3) ? 3 : 12-level+1;
	}
	public Integer getCougSpeed(){
		return (int) Math.pow(.5*level, 1.2) + 7;
	}
	// FIX
	public boolean cougSpacer(){
		if (cougTimer == null)
			cougTimer = new PosTimer(randy.nextInt(30000-(level*1500)));
		return cougTimer.getTrigger();
	}

	public boolean gridSpacer(){
		if (gridTimer == null)
			gridTimer = new PosTimer(randy.nextInt(30000-(level*1500)));
		return gridTimer.getTrigger();
	}
	public boolean falcSpacer(){
		if (falcTimer == null)
			falcTimer = new PosTimer(randy.nextInt(30000-level*1500));
		return falcTimer.getTrigger();
	}
	public void nullTimer(Obs cougar){
		if (cougar == Obs.COUGAR) {
			cougTimer = null;
		}
		else if (cougar == Obs.GRID){
			gridTimer = null;
		}
		else if (cougar == Obs.FALCON){
			falcTimer = null;
		}
	}
	public boolean obsGate(Obs obs) {
		if (obs == Obs.COUGAR ){
			if (level <= 2 || level == 4 || level == 5)
				return true;
			else return !cEnable;		
		}
		else if (obs == Obs.GRID){
			if (level <= 3 || level == 5)
				return true;
			else return !gEnable;
			
		}
		else if (obs == Obs.FALCON){
			if (level <= 5 || level == 6)
				return true;
			else return !fEnable;
			
		}
		else return true;
	}

	public Integer getGridSpeed(){
		return 2;
	}
	public Integer getFalcSpeed(){
		return 10;
	}
	public Integer getLevel(){
		return level;
	}
	public Integer getRecentInterval(){
		return recentInterval;
	}
	public Integer getScoreMult(){
		return scoreMults.get(level);
	}
	public Boolean getMessageTruth(){
		return message;
	}
	
	public static Level getInstance(){
		if (theLevel == null){
			theLevel = new Level();
		}
		return theLevel;
	}

	public void reset() {
		theLevel = new Level();
		
	}

}