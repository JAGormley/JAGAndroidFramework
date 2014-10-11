package com.jag.positron;

import java.util.ArrayList;

import android.graphics.Color;

import com.jag.framework.Graphics;
import com.jag.positron.Coil.Point;

public class Slider {
	private static Slider theSlider;
	private Graphics g;
	private int moveSpeed; 
	private int boundX;
	private int boundY;
	private int blockSize;
	private ArrayList<SliderBlock> blocks;
	private PosTimer sAlph;
	public static int sliderAlph;

	private enum Dir {
		UP, RIGHT, DOWN, LEFT;
	}
	private Slider (){
		this.g = GameScreen.thisGame.getGraphics();
		//global
		moveSpeed = 15;
		blockSize = 15;
		boundX = 90;
		boundY = 30;
		blocks = new ArrayList<Slider.SliderBlock>();
//		sAlph = new PosTimer(2500);
		
		for (int i = 0 ; i < 12 ; i++){
			blocks.add(new SliderBlock(boundX - (i*blockSize)));
		}
	}

	public void drawUpdate(int x, int y, boolean fingerMove){		
		// TRACK
//		g.drawLine(20, y, x-boundX, y, Color.GRAY, 120, 3);
//		g.drawLine(x+boundX, y, GameScreen.screenwidth-20, y, Color.GRAY, 120, 3);

		int slices = 15;
		int boxStartX = x-boundX;
		int boxStartY = y-boundY+(blockSize/2);
		for (int i = 0 ; i < slices ; i++){
			g.drawLine((boxStartX/slices)*(i+1), boxStartY, (boxStartX/slices)*(i+1), y+boundY-(blockSize/2), Color.GRAY, 90, 3);
			g.drawLine(
					((GameScreen.screenwidth-(x+boundX))/slices)*(i+1) + x+boundX-(blockSize/2), 
					boxStartY, 
					((GameScreen.screenwidth-(x+boundX))/slices)*(i+1) + x+boundX-(blockSize/2), 
					y+boundY-(blockSize/2), 
					Color.GRAY, 90, 3);
		}
		
		// SLIDER ALPHA
		
				if (sAlph == null || sAlph.getTrigger()){
					sAlph = new PosTimer(750);
				}
				sAlph.update();
				
				double sRem = sAlph.getRemainingMillis();		
				double sEla = sAlph.getElapsedMillis();
				double sTot = sAlph.getTotalMillis();
				sliderAlph = (int) ((sRem > sTot/2) ?
						255* ( (sRem-(sTot/2)) / (sTot/2) ) :
							255* ( (sEla-(sTot/2)) / (sTot/2) ));
				if (sliderAlph > 255) sliderAlph = 255;
				if (sliderAlph < 0) sliderAlph = 0;
				sliderAlph /= 3;
		
		// SLIDER COLOUR BODY
		int slideSlicer = 10;
		int sliderSlices = boundX*2/slideSlicer;
		int blockWidth = (int)(blockSize*(slideSlicer*.08));
		int sliderCol = (fingerMove) ? Color.CYAN : Color.RED;
		sliderAlph = (fingerMove) ? 200 : sliderAlph;
		
		for (int i = 0; i < slideSlicer ; i++){
			g.drawRect(x-boundX+(blockSize/2)+(i*sliderSlices), y-boundY+(blockSize/2), blockWidth, boundY*2-(blockSize)+3, sliderCol, sliderAlph);
		}
		

		// SLIDER BLOCKS
		for (int i = 0; i < blocks.size(); i++) {
//			blocks.get(i).draw(x, y, i);
		}
	}	

	public static Slider getInstance(){
		if (theSlider == null){
			theSlider = new Slider();
		}
		return theSlider;
	}


	///////////// BLOCKS //////////////

	private class SliderBlock {
		private int drawX;
		private int drawY;
		Dir dir;


		public SliderBlock(int startX){
			drawX = startX;
			dir = Dir.RIGHT;
		}

		public void draw(int x, int y, int arrayPos){
			// set first
			int alph = 255 - (arrayPos*20);
			alph /= 3;
			if (alph < 0) alph = 0;
			update(x, y);	
			g.drawRect(drawX+x - (blockSize/2), drawY+y - (blockSize/2), blockSize, blockSize, Color.GRAY, alph);

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


	}
}
