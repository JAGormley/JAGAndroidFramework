package com.jag.positron;

public class TimeCharge {
	
	public int x, y, speed;
	
	public TimeCharge(int x, int y, int speed){
		this.x = x;
		this.y = y;
		this.speed = speed;			
	}	
	public void update(){
		this.y -= speed;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	

}
