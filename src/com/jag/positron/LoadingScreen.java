package com.jag.positron;



import java.io.IOException;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
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
		Assets.block = g.newImage("block.png", ImageFormat.RGB565);
		//Assets.menu = g.newImage("menudr3(dr2).png", ImageFormat.ARGB8888);
		
		Assets.lock = g.newImage("lock.png", ImageFormat.RGB565);

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
			Assets.loadScreen = g.newImage("LOADSCREENs.png", ImageFormat.RGB565);
			g.drawImage(Assets.loadScreen, 0, 0);	
			
			Assets.menu1 = g.newImage("newMenu1b.png", ImageFormat.RGB565);
			Assets.menu2 = g.newImage("newMenu2s.png", ImageFormat.RGB565);
			Assets.menu3 = g.newImage("newMenu3s.png", ImageFormat.RGB565);
			Assets.menu4 = g.newImage("newMenu4s.png", ImageFormat.RGB565);
			Assets.menu5 = g.newImage("newMenu5s.png", ImageFormat.RGB565);
			Assets.menu6 = g.newImage("newMenu6s.png", ImageFormat.RGB565);
			Assets.menu7 = g.newImage("newMenu7s.png", ImageFormat.RGB565);	
			
			Assets.rdscreen = g.newImage("RDSCREENs.png", ImageFormat.RGB565);
			Assets.pauseScreen = g.newImage("PAUSESCREENs.png", ImageFormat.RGB565);
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

		
		Assets.eagle = g.newImage("eagle.png", ImageFormat.ARGB8888);
		Assets.eagleb = g.newImage("eagleb.png", ImageFormat.ARGB8888);
		Assets.pos = g.newImage("pos1prev.png", ImageFormat.ARGB8888);
		Assets.neg = g.newImage("neg2prev.png", ImageFormat.ARGB8888);		
		Assets.alarm1 = g.newImage("alarm1b.png", ImageFormat.ARGB8888);
		Assets.alarm2 = g.newImage("alarm2b.png", ImageFormat.ARGB8888);
		Assets.posGray = g.newImage("pos1GRAY.png", ImageFormat.ARGB8888);
		Assets.negGray = g.newImage("neg2GRAY.png", ImageFormat.ARGB8888);
		
		Assets.skull = g.newImage("skullorig.png", ImageFormat.ARGB8888);
		Assets.smallSkull = g.newImage("skull.png", ImageFormat.ARGB8888);
		Assets.coolSkull = g.newImage("skullorigglass.png", ImageFormat.ARGB8888);
//
		
		//PBASE
		Assets.pbase0 = g.newImage("elecbuttonPOS1.png", ImageFormat.ARGB8888);
		Assets.pbase1 = g.newImage("elecbuttonPOS2.png", ImageFormat.ARGB8888);
		Assets.pbase2 = g.newImage("elecbuttonPOS2b.png", ImageFormat.ARGB8888);
		Assets.pbase3 = g.newImage("elecbuttonPOS3.png", ImageFormat.ARGB8888);
		Assets.pbase4 = g.newImage("elecbuttonPOS4.png", ImageFormat.ARGB8888);
		Assets.pbase5 = g.newImage("elecbuttonPOS5.png", ImageFormat.ARGB8888);
		Assets.pbase6 = g.newImage("elecbuttonPOS6.png", ImageFormat.ARGB8888);
		Assets.pbase7 = g.newImage("elecbuttonPOS7.png", ImageFormat.ARGB8888);
		Assets.pbase8 = g.newImage("elecbuttonPOS8.png", ImageFormat.ARGB8888);
		Assets.pbase9 = g.newImage("elecbuttonPOS9.png", ImageFormat.ARGB8888);
		Assets.pbase10 = g.newImage("elecbuttonPOS10.png", ImageFormat.ARGB8888);
		Assets.pbase11 = g.newImage("elecbuttonPOS11.png", ImageFormat.ARGB8888);
		Assets.pbase12 = g.newImage("elecbuttonPOS12.png", ImageFormat.ARGB8888);
		Assets.pbase13 = g.newImage("elecbuttonPOS13.png", ImageFormat.ARGB8888);
		Assets.pbase14 = g.newImage("elecbuttonPOS14.png", ImageFormat.ARGB8888);
		Assets.pbase15 = g.newImage("elecbuttonPOS15.png", ImageFormat.ARGB8888);
		Assets.pbase16 = g.newImage("elecbuttonPOS16.png", ImageFormat.ARGB8888);
		Assets.pbase17 = g.newImage("elecbuttonPOS17.png", ImageFormat.ARGB8888);
		Assets.pbase18 = g.newImage("elecbuttonPOS18.png", ImageFormat.ARGB8888);
		
		Assets.pbase19 = g.newImage("elecbuttonPOS19.png", ImageFormat.ARGB8888);
		Assets.pbase20 = g.newImage("elecbuttonPOS20.png", ImageFormat.ARGB8888);
		Assets.pbase21 = g.newImage("elecbuttonPOS21.png", ImageFormat.ARGB8888);
		Assets.pbase22 = g.newImage("elecbuttonPOS22.png", ImageFormat.ARGB8888);
		Assets.pbase23 = g.newImage("elecbuttonPOS23.png", ImageFormat.ARGB8888);
		Assets.pbase24 = g.newImage("elecbuttonPOS24.png", ImageFormat.ARGB8888);
		Assets.pbase25 = g.newImage("elecbuttonPOS25.png", ImageFormat.ARGB8888);
		Assets.pbase26 = g.newImage("elecbuttonPOS26.png", ImageFormat.ARGB8888);
		Assets.pbase27 = g.newImage("elecbuttonPOS27.png", ImageFormat.ARGB8888);
		Assets.pbase28 = g.newImage("elecbuttonPOS28.png", ImageFormat.ARGB8888);
		Assets.pbase29 = g.newImage("elecbuttonPOS29.png", ImageFormat.ARGB8888);
		Assets.pbase30 = g.newImage("elecbuttonPOS30.png", ImageFormat.ARGB8888);
		Assets.pbase31 = g.newImage("elecbuttonPOS31.png", ImageFormat.ARGB8888);
		Assets.pbase32 = g.newImage("elecbuttonPOS32.png", ImageFormat.ARGB8888);
		Assets.pbase33 = g.newImage("elecbuttonPOS33.png", ImageFormat.ARGB8888);
		Assets.pbase34 = g.newImage("elecbuttonPOS34.png", ImageFormat.ARGB8888);
		Assets.pbase35 = g.newImage("elecbuttonPOS35.png", ImageFormat.ARGB8888);

		//NBASE		
		Assets.nbase0 = g.newImage("elecbuttonNEG0.png", ImageFormat.ARGB8888);
		Assets.nbase1 = g.newImage("elecbuttonNEG1.png", ImageFormat.ARGB8888);
		Assets.nbase2 = g.newImage("elecbuttonNEG2.png", ImageFormat.ARGB8888);
		Assets.nbase3 = g.newImage("elecbuttonNEG3.png", ImageFormat.ARGB8888);
		Assets.nbase4 = g.newImage("elecbuttonNEG4.png", ImageFormat.ARGB8888);
		Assets.nbase5 = g.newImage("elecbuttonNEG5.png", ImageFormat.ARGB8888);
		Assets.nbase6 = g.newImage("elecbuttonNEG6.png", ImageFormat.ARGB8888);
		Assets.nbase7 = g.newImage("elecbuttonNEG7.png", ImageFormat.ARGB8888);
		Assets.nbase8 = g.newImage("elecbuttonNEG8.png", ImageFormat.ARGB8888);
		Assets.nbase9 = g.newImage("elecbuttonNEG9.png", ImageFormat.ARGB8888);
		Assets.nbase10 = g.newImage("elecbuttonNEG10.png", ImageFormat.ARGB8888);
		Assets.nbase11 = g.newImage("elecbuttonNEG11.png", ImageFormat.ARGB8888);
		Assets.nbase12 = g.newImage("elecbuttonNEG12.png", ImageFormat.ARGB8888);
		Assets.nbase13 = g.newImage("elecbuttonNEG13.png", ImageFormat.ARGB8888);
		Assets.nbase14 = g.newImage("elecbuttonNEG14.png", ImageFormat.ARGB8888);
		Assets.nbase15 = g.newImage("elecbuttonNEG15.png", ImageFormat.ARGB8888);
		Assets.nbase16 = g.newImage("elecbuttonNEG16.png", ImageFormat.ARGB8888);
		Assets.nbase17 = g.newImage("elecbuttonNEG17.png", ImageFormat.ARGB8888);
		Assets.nbase18 = g.newImage("elecbuttonNEG18.png", ImageFormat.ARGB8888);
		
		Assets.nbase19 = g.newImage("elecbuttonNEG19.png", ImageFormat.ARGB8888);
		Assets.nbase20 = g.newImage("elecbuttonNEG20.png", ImageFormat.ARGB8888);
		Assets.nbase21 = g.newImage("elecbuttonNEG21.png", ImageFormat.ARGB8888);
		Assets.nbase22 = g.newImage("elecbuttonNEG22.png", ImageFormat.ARGB8888);
		Assets.nbase23 = g.newImage("elecbuttonNEG23.png", ImageFormat.ARGB8888);
		Assets.nbase24 = g.newImage("elecbuttonNEG24.png", ImageFormat.ARGB8888);
		Assets.nbase25 = g.newImage("elecbuttonNEG25.png", ImageFormat.ARGB8888);
		Assets.nbase26 = g.newImage("elecbuttonNEG26.png", ImageFormat.ARGB8888);
		Assets.nbase27 = g.newImage("elecbuttonNEG27.png", ImageFormat.ARGB8888);
		Assets.nbase28 = g.newImage("elecbuttonNEG28.png", ImageFormat.ARGB8888);
		Assets.nbase29 = g.newImage("elecbuttonNEG29.png", ImageFormat.ARGB8888);
		Assets.nbase30 = g.newImage("elecbuttonNEG30.png", ImageFormat.ARGB8888);
		Assets.nbase31 = g.newImage("elecbuttonNEG31.png", ImageFormat.ARGB8888);
		Assets.nbase32 = g.newImage("elecbuttonNEG32.png", ImageFormat.ARGB8888);
		Assets.nbase33 = g.newImage("elecbuttonNEG33.png", ImageFormat.ARGB8888);
		Assets.nbase34 = g.newImage("elecbuttonNEG34.png", ImageFormat.ARGB8888);
		Assets.nbase35 = g.newImage("elecbuttonNEG35.png", ImageFormat.ARGB8888);
		
		
		
		//SOUNDS!
		Assets.click = game.getAudio().createSound("lightning_strike.ogg");
		Assets.posPoint = game.getAudio().createSound("POSSOUND.ogg");
		Assets.negPoint = game.getAudio().createSound("NEGSOUND.ogg");
		Assets.failSound = game.getAudio().createSound("FAILSOUND2.ogg");
		Assets.click2 = game.getAudio().createSound("click.mp3");
		Assets.gridVoice = game.getAudio().createSound("GRIDVOICE.ogg");
		Assets.TCdeath = game.getAudio().createSound("tcDeath7.mp3");
		Assets.teeth = game.getAudio().createSound("teethFINAL.mp3");
		Assets.ouch = game.getAudio().createSound("OUCH1.mp3");

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