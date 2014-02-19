package com.jag.positron;

import java.io.InputStream;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.Screen;
import com.jag.framework.Graphics.ImageFormat;
import com.jag.framework.Input.TouchEvent;


public class MainMenuScreen extends Screen {	
	private int instr = 0;
	private Animation menu;
	private boolean loaded;



	public MainMenuScreen(Game game) {
		super(game);
		
		Assets.loadScreen.getBitmap().recycle();
		menu = new Animation();
		menu.addFrame(Assets.menu1, 150);
		menu.addFrame(Assets.menu2, 150);
		menu.addFrame(Assets.menu3, 150);
		menu.addFrame(Assets.menu4, 150);
		menu.addFrame(Assets.menu5, 150);
		menu.addFrame(Assets.menu6, 150);
		menu.addFrame(Assets.menu7, 150);
		Assets.theme = game.getAudio().createMusic("positheme3.mp3");
		Assets.theme.setLooping(true);
		loaded = false;
		}


	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		menu.update(14);
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


		// WEIRD ANDROID GLITCH: plays halfsec of theme on boot unless I do this:

		if (loaded){
			Assets.theme.play();
		}		
		if (menu.getImage() == Assets.menu1)
			loaded = true;

		int len = touchEvents.size();
		//		System.out.println(idle);
		//		System.out.println(instr);
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if (event.x < game.getLoadWidth()/2 && event.y > game.getLoadHeight()*.87 &&instr == 0 && event.type == TouchEvent.TOUCH_UP && loaded) {
				//START GAME
				Assets.click.play(100);
				Assets.theme.stop();
				Assets.coug1 = g.newImage("coug1b.png", ImageFormat.RGB565);
				Assets.coug2 = g.newImage("coug2b.png", ImageFormat.RGB565);
				Assets.coug3 = g.newImage("coug3b.png", ImageFormat.RGB565);	
				Assets.coug4 = g.newImage("coug4b.png", ImageFormat.RGB565);
				Assets.coug5 = g.newImage("coug5b.png", ImageFormat.RGB565);
				Assets.mFace = g.newImage("face2PIXX.gif", ImageFormat.RGB565);
				Assets.mFaced	 = g.newImage("facedeathPIX2.gif", ImageFormat.RGB565);
				game.setScreen(new GameScreen(game));
			}
			if (event.x > game.getLoadWidth()/2 && event.y > game.getLoadHeight()*.79 && event.y < game.getLoadHeight()*.9 && instr == 0 && event.type == TouchEvent.TOUCH_UP && loaded){		
				Assets.click2.play(100);
				Assets.coug1 = g.newImage("coug1b.png", ImageFormat.RGB565);
				Assets.coug2 = g.newImage("coug2b.png", ImageFormat.RGB565);
				Assets.coug3 = g.newImage("coug3b.png", ImageFormat.RGB565);
				Assets.coug4 = g.newImage("coug4b.png", ImageFormat.RGB565);
				Assets.coug5 = g.newImage("coug5b.png", ImageFormat.RGB565);
				Assets.mFace = g.newImage("face2PIXX.gif", ImageFormat.RGB565);
				Assets.mFaced = g.newImage("facedeathPIX2.gif", ImageFormat.RGB565);
				game.setScreen(new Tutorial(game));
				
				
			}
			
			if (event.x > game.getLoadWidth()*.69 && event.y < game.getLoadHeight()*.21 && event.type == TouchEvent.TOUCH_UP && instr != 0 && loaded){
				Assets.click2.play(100);
				instr++;
			}

			if (event.x > game.getLoadWidth()*.53 && event.y > game.getLoadHeight()*.91 && instr == 0 && event.type == TouchEvent.TOUCH_UP && loaded){		
				android.os.Process.killProcess(android.os.Process.myPid());
			}



		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {	    	

		Graphics g = game.getGraphics();	
		//g.drawImage(Assets.menu, 0, 0);
		g.drawImage(menu.getImage(), 0, 0);

		if (instr == 1){
			Assets.instr1 = g.newImage("instr1.png", ImageFormat.RGB565);
			if (Assets.instr2 != null)
				Assets.instr2.getBitmap().recycle();
			g.drawImage(Assets.instr1, 0, 0);
		}

		if (instr == 2){
			Assets.instr1.getBitmap().recycle();
			Assets.instr2 = g.newImage("instr2.png", ImageFormat.RGB565);
			if (Assets.instr3 != null)
				Assets.instr3.getBitmap().recycle();
			g.drawImage(Assets.instr2, 0, 0);
		}

		if (instr == 3){
			Assets.instr3 = g.newImage("instr3.png", ImageFormat.RGB565);
			Assets.instr2.getBitmap().recycle();
			if (Assets.instr4 != null)
				Assets.instr4.getBitmap().recycle();
			g.drawImage(Assets.instr3, 0, 0);
		}


		if (instr == 4){
			Assets.instr4 = g.newImage("instr4.png", ImageFormat.RGB565);
			Assets.instr3.getBitmap().recycle();
			if (Assets.instr5 != null)
				Assets.instr5.getBitmap().recycle();
			g.drawImage(Assets.instr4, 0, 0);
		}

		if (instr == 5){
			Assets.instr5 = g.newImage("instr5.png", ImageFormat.RGB565);
			Assets.instr4.getBitmap().recycle();
			if (Assets.instr6 != null)
			Assets.instr6.getBitmap().recycle();
			g.drawImage(Assets.instr5, 0, 0);
		}

		if (instr == 6){
			Assets.instr6 = g.newImage("instr6.png", ImageFormat.RGB565);
			Assets.instr5.getBitmap().recycle();
			if (Assets.instr7 != null)
			Assets.instr7.getBitmap().recycle();
			g.drawImage(Assets.instr6, 0, 0);
		}

		if (instr == 7){
			Assets.instr7 = g.newImage("instr7.png", ImageFormat.RGB565);
			Assets.instr6.getBitmap().recycle();
			if (Assets.instr8 != null)
			Assets.instr8.getBitmap().recycle();
			g.drawImage(Assets.instr7, 0, 0);
		}

		if (instr == 8){
			Assets.instr8 = g.newImage("instr8.png", ImageFormat.RGB565);
			Assets.instr7.getBitmap().recycle();
			if (Assets.instr9 != null)
			Assets.instr9.getBitmap().recycle();
			g.drawImage(Assets.instr8, 0, 0);
		}

		if (instr == 9){
			Assets.instr9 = g.newImage("instr9.png", ImageFormat.RGB565);
			Assets.instr8.getBitmap().recycle();
			if (Assets.instrPause != null)
			Assets.instrPause.getBitmap().recycle();
			g.drawImage(Assets.instr9, 0, 0);
		}

		if (instr == 10){

			Assets.instrPause = g.newImage("instrPAUSE2.png", ImageFormat.RGB565);
			Assets.instr9.getBitmap().recycle();

			g.drawRect(0, 0, 805, 1205, Color.BLACK);
			g.drawImage(Assets.instrPause, 0, 0);
		}

		if (instr == 11){
			Assets.instrPause.getBitmap().recycle();
			instr = 0;
		}

	}

	@Override
	public void pause() {
		Assets.theme.pause();
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
	}

	@Override
	public void backButton() {
		//android.os.Process.killProcess(android.os.Process.myPid());
		if (instr > 0){
			Assets.click2.play(100);
			instr--;
			}
	}
}
