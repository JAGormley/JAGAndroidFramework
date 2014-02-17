package com.jag.positron;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jag.framework.Graphics;

public class TopLock {
	private int lWidth;
	private Graphics gPhix;
	List<Integer> second;
	List<Integer> third;
	List<Integer> fourth;
	private int totalPs;
	private int killed;
	private static int completePieces;

	public TopLock (int laneWidth, Graphics g){
		lWidth = laneWidth;
		gPhix = g;
		second = new ArrayList<Integer>();
		third = new ArrayList<Integer>();
		fourth = new ArrayList<Integer>();
		
		completePieces = 21;
	}

	public void drawLock() {
		for (int i = 0; i < 7; i++){
			int lAlph = 255;
			if (second.contains((i+1)*lWidth))
				lAlph = 155;
			if (third.contains((i+1)*lWidth))
				lAlph = 55;
			if (!fourth.contains((i+1)*lWidth)){
				gPhix.drawImage(Assets.lock, (1+i)*lWidth-Assets.lock.getWidth()/2, 0, lAlph);
				gPhix.drawCircOut((1+i)*lWidth, Assets.lock.getHeight()/2+2, Assets.lock.getHeight()/2, Color.RED, 8, lAlph);
			}
		}
	}

	public void weaken(Pieces pieces) {
		int x = pieces.getX();
		if (!second.contains(x))
			second.add(x);
		else if (!third.contains(x))
			third.add(x);
		else if (!fourth.contains(x))
			fourth.add(x);
	}
	
	public List<Integer> secondLanes(){
		return second;
	}
	
	public List<Integer> thirdLanes(){
		return third;
	}
	
	public List<Integer> deadLanes(){
		return fourth;
	}

	public boolean fullLanes(){
		if (totalPs == completePieces)
			return true;
		else return false;
	}
	
	public boolean allKilled(){
		if (killed == completePieces)
			return true;
		else return false;
	}
	
	public void addKilled(){
		killed++;
	}

	public void addPiece() {
		totalPs++;
//		System.out.println("totalPs :" + totalPs);
		
	}


}
