package com.jag.positron;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;



public class Frenzy {

	public static final int NUMBER = 25;
	private int swidth;
	private int sheight;
	public double topBar;
	public int bottomBar;
	public boolean active;
	ArrayList<TimeCharge> tcArray;
	ArrayList<Integer> angles;
	ArrayList<Integer> delay;
	private long startTime;
	private boolean barShrink;
	private int bbarSize;
	private long bottomBarY;

	// can set SPEED of TC, SIZE of BAR


	public Frenzy(int sw, int sh){
		active = true;
		startTime = System.currentTimeMillis()/1000;
		swidth = sw;
		sheight = sh;
		topBar = sheight*.05;
		bbarSize = (int) Math.round(swidth*.1);
		tcArray = new ArrayList<TimeCharge>();
		angles = new ArrayList<Integer>();
		delay = new ArrayList<Integer>();
		Random angleInts = new Random();
		for (int i = 0 ; i < NUMBER ; i++){
			tcArray.add(new TimeCharge(sw/2, sh/10, -6));
			angles.add(angleInts.nextInt(10) - 5);
			delay.add(i);
		}
	}

	public int getBbarSize() {
		return bbarSize;
	}

	public void setBbarSize(int bbarSize) {
		this.bbarSize = bbarSize;
	}

	public void update(GameScreen game, int fx, float fy){
		bottomBar = fx;
		bottomBarY = Math.round(sheight*.73);
		for (int i = 0 ; i < tcArray.size() ; i++){
			//			System.out.println("time: "+(System.currentTimeMillis()/1000 - startTime));
			//			System.out.println("delay#: "+ delay.get(i)/2);
			TimeCharge tcTemp = tcArray.get(i);
			tcTemp.setX(tcTemp.getX() + angles.get(i));
			if (tcTemp.getX() < 0 || tcTemp.getX() > swidth){
				angles.set(i, -angles.get(i));
			}
			if (tcTemp.getY() < topBar){
				tcArray.remove(i);
				angles.remove(i);
				topBar -= topBar/NUMBER;
				barShrink = true;
			}
			if (tcTemp.getX() > bottomBar-bbarSize && tcTemp.getX() < bottomBar+bbarSize && 
					tcTemp.getY() > Math.round(sheight*.73) && tcTemp.getY() < Math.round(sheight*.79) &&
					tcTemp.getSpeed() < 0){
				tcTemp.setSpeed(-tcTemp.getSpeed());
			}
			if (delay.get(i) <= System.currentTimeMillis()/1000 - startTime){
				tcTemp.update();			
			}
			else
			{
				tcTemp.setX(tcTemp.getX()+(angles.get(i)/5));
			}
			if (tcTemp.getY() > bottomBarY+Math.round(sheight*.08)){
				game.topFreeze = false;
				game.frenzy = false;
				tcArray.clear();
				game.fail();				
			}
			if (tcArray.size() == 0){				
				game.topFreeze = false;
				game.frenzy = false;
				tcArray.clear();
			}
		}
	}

	public int getBottomBar() {
		return bottomBar;
	}

	public void setBottomBar(int bottomBar) {
		this.bottomBar = bottomBar;
	}

	public boolean isBarShrink() {
		return barShrink;
	}

	public void setBarShrink(boolean barShrink) {
		this.barShrink = barShrink;
	}

	public ArrayList<TimeCharge> getFArray(){
		return tcArray;
	}

	public int getNumber(){
		if (tcArray != null)
			return tcArray.size();
		else return 0;
	}

	public double getTopBar(){
		return topBar;
	}


}
