package com.jag.positron;

import java.util.Random;

import com.jag.positron.GameScreen;
import com.jag.positron.Scene;
import com.jag.framework.Screen;

public class Pieces {

	public int x, y, backspeed;
	public double speed;
	private double acc;

	private boolean visible;
	public boolean direction;
	private int screenHeight, genPoint;
	public int score;
	public boolean type, wayback;
	private Scene scene;
	private boolean switched;
	private int switchTimer = 25;
	GameScreen gamescreen;




	public Pieces(int startX, int startY, boolean inittype, GameScreen gameScreen2){
		scene = GameScreen.getScene();
		screenHeight = GameScreen.screenheight;
		gamescreen = gameScreen2;
		x = startX;
		y = startY;
		speed = 15;
		visible = true;
		direction = true;
		type = inittype;
		wayback = false;
		backspeed = 25;
		acc = 0;
		score = 0;
		genPoint = (screenHeight/5)*4;
		switched = false;

	}

	public void update(){
		if (y < Assets.lock.getHeight())
			checkCollision();
		else if (y < 0){
			checkCollision();
		}	

		if (direction){

			if (y >= 200){
				y -= speed;

			} 
			else if (y < 210&&gamescreen.getTopFreeze()){
				y -= speed;
			}
			else if (y < 210&&!gamescreen.getTopFreeze()){

				if (type){
					if (x > scene.getLine()){
						y -= (y / 12) + 4;
					}
					if (x < scene.getLine()){
						y -= speed*acc;
						acc += .5;
					}
				}
				if (!type){
					if (x < scene.getLine()){
						y -= (y / 12) + 4;
					}
					if (x > scene.getLine()){
						y -= speed*acc;
						acc += .5;
					}
				}
			}
		}
	}

	public void updateback(){

		if (y > (genPoint)){
			visible = false;
			wayback = false;
		}

		if (!direction&&gamescreen.getTopFreeze()){
			y += backspeed;
		}

		if (!direction&&!gamescreen.getTopFreeze())
			y += backspeed;
	}

	private void checkCollision() {
		if (gamescreen.getTopFreeze()){
			direction = !direction;
			wayback = true;
		}

		else if (type){
			if (x < scene.getLine()){
				visible = false;
			}
			if (x > scene.getLine()){
				direction = !direction;
				wayback = true;
			}
		}
		else if (!type){
			if (x < scene.getLine()){
				direction = !direction;
				wayback = true;
			}
			if (x > scene.getLine()){				
				visible = false;
			}
		}
	}
	
	


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getSpeedX() {
		return speed;
	}

	public void setSpeedX(double f) {
		this.speed = f;
	}

	public boolean isVisible() {
		return visible;
	}
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setType(boolean type){
		this.type = type;
	}
	
	public void switchType(){
		this.type = !type;
		switched = true;
	}
	
	public boolean getType() {
		return type;
	}
	
	public boolean getSwitched(){
		return switched;
	}
	
	public void resetSwitched(){
		switched = false;
	}


	public void setBackspeed(int b){
		backspeed = b;
	}

	public int getGenPoint() {
		return genPoint;
	}
	
	public int getTimer(){
		return switchTimer;
	}
	public void updateTimer(){
		switchTimer -= 1;
	}

	public void setGenPoint(int genPoint) {
		this.genPoint = genPoint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (direction ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(speed);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (type ? 1231 : 1237);
		result = prime * result + (visible ? 1231 : 1237);
		result = prime * result + (wayback ? 1231 : 1237);
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieces other = (Pieces) obj;
		if (direction != other.direction)
			return false;
		if (Double.doubleToLongBits(speed) != Double
				.doubleToLongBits(other.speed))
			return false;
		if (type != other.type)
			return false;
		if (visible != other.visible)
			return false;
		if (wayback != other.wayback)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	

}


