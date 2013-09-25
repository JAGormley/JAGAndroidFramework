package com.jag.positron;



import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;

import com.jag.framework.Music;


import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.Screen;
import com.jag.framework.Graphics.ImageFormat;
import android.content.Context;


public class LoadingScreen extends Screen {

	int screenW;
	int screenH;	

	public Bitmap thisbitmap, croppedBmp;	
	AssetManager assets;

	public LoadingScreen(Game game) {
		super(game);
		screenH = game.getLoadHeight();
		screenW = game.getLoadWidth();

	}

	@Override
	public void update(float deltaTime) {	
		Graphics g = game.getGraphics();
		//g.drawString("LOADING", 350, 500, null);



		//Assets.block = g.newImage("PositronBlock.png", ImageFormat.RGB565);

		//Assets.menu = g.newImage("menudr3(dr2).png", ImageFormat.ARGB8888);






		//IMGS!!
		if (screenH > 1000){
			Assets.loadScreen = g.newImage("LOADSCREEN.png", ImageFormat.ARGB8888);
			g.drawImage(Assets.loadScreen, 0, 0);

			Assets.menu1 = g.newImage("newMenu1.png", ImageFormat.RGB565);
			Assets.menu2 = g.newImage("newMenu2.png", ImageFormat.RGB565);
			Assets.menu3 = g.newImage("newMenu3.png", ImageFormat.RGB565);
			Assets.menu4 = g.newImage("newMenu4.png", ImageFormat.RGB565);
			Assets.menu5 = g.newImage("newMenu5.png", ImageFormat.RGB565);
			Assets.menu6 = g.newImage("newMenu6.png", ImageFormat.RGB565);
			Assets.menu7 = g.newImage("newMenu7.png", ImageFormat.RGB565);


			Assets.rdscreen = g.newImage("RDSCREEN.png", ImageFormat.ARGB8888);
			Assets.pauseScreen = g.newImage("PAUSESCREEN.png", ImageFormat.RGB565);

		}
		else {
			
			Assets.loadScreen = g.newImage("LOADSCREENt.png", ImageFormat.RGB565);
			g.drawImage(Assets.loadScreen, 0, 0);	
			
			Assets.menu1 = g.newImage("newMenu1b.png", ImageFormat.RGB565);
			Assets.menu2 = g.newImage("newMenu2b.png", ImageFormat.RGB565);
			Assets.menu3 = g.newImage("newMenu3b.png", ImageFormat.RGB565);
			Assets.menu4 = g.newImage("newMenu4b.png", ImageFormat.RGB565);
			Assets.menu5 = g.newImage("newMenu5b.png", ImageFormat.RGB565);
			Assets.menu6 = g.newImage("newMenu6b.png", ImageFormat.RGB565);
			Assets.menu7 = g.newImage("newMenu7b.png", ImageFormat.RGB565);	
			
			Assets.rdscreen = g.newImage("RDSCREENt.png", ImageFormat.RGB565);
			Assets.pauseScreen = g.newImage("PAUSESCREENt.png", ImageFormat.RGB565);
		}


		//		Assets.instr1 = g.newImage("instr1.png", ImageFormat.RGB565);
		//		Assets.instr2 = g.newImage("instr2.png", ImageFormat.RGB565);
		//		Assets.instr3 = g.newImage("instr3.png", ImageFormat.RGB565);
		//		Assets.instr4 = g.newImage("instr4.png", ImageFormat.RGB565);
		//		Assets.instr5 = g.newImage("instr5.png", ImageFormat.RGB565);
		//		Assets.instr6 = g.newImage("instr6.png", ImageFormat.RGB565);
		//		Assets.instr7 = g.newImage("instr7.png", ImageFormat.RGB565);
		//		Assets.instr8 = g.newImage("instr8.png", ImageFormat.RGB565);
		//		Assets.instr9 = g.newImage("instr9.png", ImageFormat.RGB565);
		//		Assets.instrPause = g.newImage("instrPAUSE2.png", ImageFormat.ARGB8888);



		//UNCOMMENT:

//		Assets.pos = g.newImage("pos1.png", ImageFormat.ARGB8888);
//		Assets.neg = g.newImage("neg2.png", ImageFormat.ARGB8888);				
//		Assets.alarm1 = g.newImage("alarm1b.png", ImageFormat.ARGB8888);
//		Assets.alarm2 = g.newImage("alarm2b.png", ImageFormat.ARGB8888);
//
//		Assets.elecbase1 = g.newImage("elecbuttonNEG1.png", ImageFormat.ARGB8888);
//		Assets.elecbase2 = g.newImage("elecbuttonNEG2.png", ImageFormat.ARGB8888);
//		Assets.elecbase3 = g.newImage("elecbuttonNEG3.png", ImageFormat.ARGB8888);
//		Assets.elecbase4 = g.newImage("elecbuttonNEG4.png", ImageFormat.ARGB8888);
//		Assets.elecbase5 = g.newImage("elecbuttonNEG5.png", ImageFormat.ARGB8888);
//		Assets.elecbase6 = g.newImage("elecbuttonNEG6.png", ImageFormat.ARGB8888);
//		Assets.elecbasea = g.newImage("elecbuttonPOS1.png", ImageFormat.ARGB8888);
//		Assets.elecbaseb = g.newImage("elecbuttonPOS2.png", ImageFormat.ARGB8888);
//		Assets.elecbasec = g.newImage("elecbuttonPOS3.png", ImageFormat.ARGB8888);
//		Assets.elecbased = g.newImage("elecbuttonPOS4.png", ImageFormat.ARGB8888);
//		Assets.elecbasee = g.newImage("elecbuttonPOS5.png", ImageFormat.ARGB8888);
//		Assets.elecbasef = g.newImage("elecbuttonPOS6.png", ImageFormat.ARGB8888);


		//SOUNDS!
		Assets.menu = g.newImage("newMenu.png", ImageFormat.ARGB8888);
		Assets.click = game.getAudio().createSound("lightning_strike.ogg");
		Assets.posPoint = game.getAudio().createSound("POSSOUND.ogg");
		Assets.negPoint = game.getAudio().createSound("NEGSOUND.ogg");
		Assets.failSound = game.getAudio().createSound("FAILSOUND2.ogg");
		Assets.click2 = game.getAudio().createSound("click.mp3");
		Assets.gridVoice = game.getAudio().createSound("GRIDVOICE.ogg");
		Assets.TCdeath = game.getAudio().createSound("tcDeath7.mp3");
		Assets.teeth = game.getAudio().createSound("teethFINAL.mp3");

		Assets.tcDrone = game.getAudio().createMusic("TCdrone.mp3");		
		Assets.gridDrone = game.getAudio().createMusic("GRIDDRONE.mp3");


		game.setScreen(new MainMenuScreen(game));



	}

	@Override
	public void paint(float deltaTime) {

	}


	@Override
	public void pause() {


	}


	@Override
	public void resume() {


	}


	@Override
	public void dispose() {


	}


	@Override
	public void backButton() {


	}
}