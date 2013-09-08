package com.jag.positron;

import java.util.ArrayList;
import java.util.List;

public class TimeGrid {
	
	private int x, y, size, gridSpeed, sizeSpeed;
	private List<GridLine> gls;
	
	public TimeGrid(int y){
		this.y = y;
		gridSpeed = 3;
		gls = new ArrayList<GridLine>();
		gls.add(new GridLine(0));
		gls.add(new GridLine(80));
		gls.add(new GridLine(160));
		gls.add(new GridLine(240));
		gls.add(new GridLine(320));
		gls.add(new GridLine(400));
		gls.add(new GridLine(480));
		gls.add(new GridLine(560));
		gls.add(new GridLine(640));
		gls.add(new GridLine(720));
		gls.add(new GridLine(800));
		gls.add(new GridLine(880));
		gls.add(new GridLine(960));
		gls.add(new GridLine(1040));
		gls.add(new GridLine(1120));
		gls.add(new GridLine(1200));
		sizeSpeed = 2;
		
	}
	
	public void update(){
		size += sizeSpeed;
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
	public int getSize() {
		return this.size;
	}

	public int getGridSpeed() {
		return gridSpeed;
	}

	public void setGridSpeed(int gridSpeed) {
		this.gridSpeed = gridSpeed;
	}
	
	public List<GridLine> getGLs(){
		return gls;
	}
	
	public void addGL(GridLine gl){
		gls.add(gl);
	}
	
	public void removeGL(GridLine gl){
		gls.remove(gl);
	}

	public int getSizeSpeed() {
		return sizeSpeed;
	}

	public void setSizeSpeed(int sizeSpeed) {
		this.sizeSpeed = sizeSpeed;
	}
}
