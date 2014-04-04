package com.jag.positron;

public class Launcher {
	private static Launcher instance = null;
	protected Launcher() {
		// Exists only to defeat instantiation.
	}
	public static Launcher getInstance() {
		if(instance == null) {
			instance = new Launcher();
		}
		return instance;
	}
	public void drawLauncher(){
		
	}
}


// POINTS: 
// launchRings (array of points)
// genSpots
// chargeLines (array of points: start points are genSpots, endpoints are launchRing points)


// METHODS:
// halfCharge(Sprite x/y)
	// drawIcon()

// Generate(Sprite x/y) [at Sprite gen point]
	// light up line, light up ring

// Destroy (Sprite x/y)
// 
