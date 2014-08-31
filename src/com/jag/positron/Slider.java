package com.jag.positron;

import android.graphics.Color;

import com.jag.framework.Graphics;
import com.jag.positron.Coil.Point;

public class Slider {
	private static Slider theSlider;
	private Graphics g;
	private int moveSpeed; 
	private int drawX;
	private int drawY;
	private int boundX;
	private int boundY;
	private int blockSize;
	Dir dir;
	private enum Dir {
		UP, RIGHT, DOWN, LEFT;
	}
	
	private Slider (){
		this.g = GameScreen.thisGame.getGraphics();
		moveSpeed = 30;
		boundX = 90;
		boundY = 30;
		dir = Dir.RIGHT;
		drawX = -boundX;
		blockSize = 15;
	}
	
	public void draw(int x, int y){
		// set first
		update(x, y);	
		g.drawRect(drawX+x - (blockSize/2), drawY+y - (blockSize/2), blockSize, blockSize, Color.GRAY, 255);
		
	}
	
	// need to move thing
	private void update(int x, int y) {
		switch (dir) {
		case UP:
			drawX = -boundX;
			drawY -= moveSpeed;
			
			if (drawY <= -boundY)
				dir = Dir.RIGHT;
			break;
			
		case RIGHT:
			drawY = -boundY;
			drawX += moveSpeed;
			if (drawX >= boundX){
				dir = Dir.DOWN;
			}
			break;
			
		case DOWN:
			drawX = boundX;
			drawY += moveSpeed;
			
			if (drawY >= boundY)
				dir = Dir.LEFT;
			break;
			
		case LEFT:
			drawY = boundY;
			drawX -= moveSpeed;
			
			if (drawX <= -boundX)
				dir = Dir.UP;
			break;
		}
		
		
		
	}

	public static Slider getInstance(){
		if (theSlider == null){
			theSlider = new Slider();
		}
		return theSlider;
	}
}
