package com.jag.positron;

import java.util.ArrayList;

public class Frenzy {

	private int sw;
	private int sh;
	ArrayList<TimeCharge> tcArray;
	
	public Frenzy(int sw, int sh){
		tcArray = new ArrayList<TimeCharge>();
		for (int i = 0 ; i < 15 ; i++){
			tcArray.add(new TimeCharge(sw/2, sh/10, -1));
		}	
	}
	
	public void update(){
		for (TimeCharge tc : tcArray){
			tc.update();
		}
	}
	
	
}
