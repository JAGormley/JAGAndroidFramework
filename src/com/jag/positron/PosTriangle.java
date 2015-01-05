package com.jag.positron;

import java.util.Random;

import android.graphics.Path;

public class PosTriangle {
	Path path = new Path();
	float moveSpeed = -20;
	Boolean side;
	int height;
	int lane = 120;
	int x;

	public PosTriangle(boolean s){

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(5);
		height = (randomInt+3)*lane;
		side = s;

		if (s){
			path.moveTo(-50, -50);
			path.lineTo(25, 0);
			path.lineTo(-50, 50);
			path.lineTo(-50, -50);
			path.close();
			path.offset(0, height);
			x = -50;
		}

		if (!s){

			path.moveTo(1250, 1250);
			path.lineTo(1175, 1200);
			path.lineTo(1250, 1150);
			path.lineTo(1250, 1250);
			path.close();
			path.offset(0, height-1200);
			x = 1250;
		}
	}

	public void moveTri(){
		if (!side){
			path.offset(moveSpeed, 0);
			x += moveSpeed;
		}
		if (side){
			path.offset(Math.abs(moveSpeed), 0);
			x -= moveSpeed;
		}
	}	

	public Integer getHeight(){
		return height;
	}

	public void setSpeed(int speed){
		moveSpeed = -speed;
	}

	public Path getPath(){
		return path;
	}

	public int getX(){
		return x;
	}
	
	public boolean getSide(){
		return side;
	}
}

