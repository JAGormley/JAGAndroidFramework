package com.jag.positron;

import java.util.ArrayList;

public class Level {
	private static Level theLevel;
	private int level;

	private float accFactor;
	private int recentInterval;
	// this is an array that contains number of sprites required to access next level
	private ArrayList<Integer> levelSpriteNumber;
	private ArrayList<Integer> scoreMults;
	private int numLevels;
	public int scoreMult;
	
	private Level(){
		level = 1;
//		recentInterval = (int) (-.41*Math.pow(level, 2)) + 40;
		levelSpriteNumber = new ArrayList<Integer>();
		numLevels = 8;
		for (int i = 0; i < numLevels; i++) {
			levelSpriteNumber.add( (int)(25/((-.41*Math.pow(3*(level+i), 1.3) + 40) * .018)) );
		}
		
		// explicitly define scoreMult levels
		scoreMults.add(1);
		scoreMults.add(2);
		scoreMults.add(5);
		scoreMults.add(10);
		scoreMults.add(15);
		scoreMults.add(25);
		scoreMults.add(35);
		scoreMults.add(50);
	}
	
	public void update(int score){
		recentInterval = (int) (-.41*Math.pow(level, 2)) + 40;
		levelCheck();
		
		
	}

	private void levelCheck() {
		
	}

	public Integer getSpriteSpeed(){
		return (int) (.47*Math.pow(level, 2)) + 15;
	}
	public Integer getBackSpeed(){
		return (int) (.55*Math.pow(level, 2))+ 25;
	}
	public Float getAccMod(){
		return level*accFactor;
	}
	public Integer getCougSpeed(){
		return (int) (.63*level) + 5;
	}	
	public Integer getLevel(){
		return level;
	}
	public Integer getRecentInterval(){
		return recentInterval;
	}
	
	public static Level getInstance(){
		if (theLevel == null){
			theLevel = new Level();
		}
		return theLevel;
	}
	
	
}