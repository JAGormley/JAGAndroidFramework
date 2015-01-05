package com.jag.positron;

public class ControlPanel {

	
	// Debug
	public static final boolean framerate = true;
	public static final boolean trace = false;	
	// Game Settings
	public static final int score = 8;
	public static final boolean enableTips = true;
	// Obstacles
	public static final boolean cougar = true;
	public static final boolean grid = true;
	public static final boolean falcon = true;
	
	
	// STATIC HELPERS
	/**
	 * p = current time
	 * t = total time
	 */
	public static int alphize(double p, double t){
		int alph = (int)((p/t)*255);
		if (alph > 255) alph = 255;
		if (alph < 0) alph = 0;
		return alph;
	}

}
