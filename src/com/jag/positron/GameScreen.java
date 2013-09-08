package com.jag.positron;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;



import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.Image;

import com.jag.framework.Graphics.ImageFormat;
import com.jag.framework.Input.TouchEvent;
import com.jag.framework.Screen;


public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver	
	}

	GameState state = GameState.Ready;

	// Variable Setup
	private static Scene scene;
	public static int screenheight;

	private ArrayList<Pieces> pieces;
	private ArrayList<PosTriangle> pts;
	Queue<Integer> heights = new LinkedList<Integer>();

	//lanes:
	int lane;

	Paint paint, paint2, paint3, paint4;
	Image fore, rings1, rings2, base1, base2, bolt, currentMagnet, lightningLineImage;

	boolean recent, freeze, touch, posPressed, negPressed, wrongButton, lightning, lock, nextLevel, redRing,	
	levelInA, levelInB, level1, level2, level3, level4, level5, level6, level7, level8, scoreReset, typePass;

	int timePassed, difficulty, recentInterval, score, scoreTemp, scoreTempTemp, scoreTempDraw, drawTimer, killx, killy, lightningDuration,
	ballDuration, scoreMult, updateInterval, tcx, tcy, i, newLevel, gridInt, baseGrowth;

	float fingery, circleRad, j;
	private Animation alert, magnet, elecBase, elecBase2;

	private Paint paint5, paint6;
	private double level;

	private TimeCharge tc;
	private TimeGrid tg;
	private TimeGrid tgTemp;

	private int lineDuration, scoreDeathDur, levelStart, postScore;

	private boolean currentTC, currentTG;
	private boolean tcDeath;
	private boolean tgDeath;
	private boolean topFreeze;
	private boolean newHigh;

	private int freezeDur;
	private LinearGradient lg;
	private LinearGradient lg2;

	private Paint paint7;
	private Paint paint8;
	private Paint paint9;
	private Paint paint10;
	private Paint paint11;
	private Paint paint12;

	private int tempyScore;

	private boolean newGridPiece;

	private int gridPieceCount;

	private boolean exitCases;

	private boolean newPiece;

	private int gridX;

	private int gridY;

	private Paint paint13;

	private boolean recentTooth;

	private int recentToothInterval = 13;

	private boolean teeth;

	private int toothCount;

	private Pieces tempPiece;



	//private PosTriangle posT;


	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here
		scene = new Scene(400);
		screenheight = game.getGraphics().getHeight();
		pieces = new ArrayList<Pieces>();
		pts = new ArrayList<PosTriangle>();

		lane = 100;
		recent = true;

		lg = new LinearGradient(600, -10 , 600, 40, Color.BLUE, Color.alpha(0), android.graphics.Shader.TileMode.CLAMP);
		lg2 = new LinearGradient(600, 800 , 600, 980, Color.GREEN, Color.alpha(0), android.graphics.Shader.TileMode.CLAMP);

		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);	

		paint2 = new Paint();
		paint2.setColor(Color.BLUE);
		paint2.setStyle(Style.STROKE);
		paint2.setStrokeWidth(10);

		paint3 = new Paint();
		paint3.setTypeface(Assets.font);
		paint3.setTextSize(120);
		paint3.setTextAlign(Paint.Align.CENTER);
		paint3.setAntiAlias(true);
		paint3.setColor(Color.BLUE);

		paint4 = new Paint();
		paint4.setTypeface(Assets.font);
		paint4.setTextSize(43);
		paint4.setTextAlign(Paint.Align.CENTER);
		paint4.setAntiAlias(true);
		paint4.setColor(Color.RED);

		paint5 = new Paint();
		paint5.setTypeface(Assets.font);
		paint5.setTextSize(180);
		paint5.setTextAlign(Paint.Align.CENTER);
		paint5.setAntiAlias(true);
		paint5.setColor(Color.RED);


		paint6 = new Paint();
		paint6.setTypeface(Assets.font);
		paint6.setTextSize(40);
		paint6.setTextAlign(Paint.Align.CENTER);
		paint6.setAntiAlias(true);
		paint6.setColor(Color.BLUE);

		paint7 = new Paint();
		paint7.setTypeface(Assets.font);
		paint7.setTextSize(80);
		paint7.setTextAlign(Paint.Align.CENTER);
		paint7.setAntiAlias(true);
		paint7.setColor(Color.RED);

		paint8 = new Paint();
		paint8.setColor(Color.rgb(255,215,0));
		paint8.setStyle(Style.FILL);

		paint9 = new Paint();
		//paint9.setMaskFilter(new BlurMaskFilter(40, Blur.INNER));
		paint9.setShader(lg);

		paint10 = new Paint();		
		paint10.setShader(lg2);

		paint11 = new Paint();
		paint11.setTypeface(Assets.font);
		paint11.setTextSize(60);
		paint11.setTextAlign(Paint.Align.CENTER);
		paint11.setAntiAlias(true);
		paint11.setColor(Color.RED);

		paint12 = new Paint();
		paint12.setTypeface(Assets.font);
		paint12.setTextSize(160);
		paint12.setTextAlign(Paint.Align.CENTER);
		paint12.setAntiAlias(true);
		paint12.setColor(Color.RED);

		paint13 = new Paint();
		paint13.setTypeface(Assets.font);
		paint13.setTextSize(43);
		paint13.setTextAlign(Paint.Align.CENTER);
		paint13.setAntiAlias(true);
		paint13.setColor(Color.BLUE);

		recentInterval = 30;
		timePassed = 0;
		freeze = false;
		wrongButton = false;
		lightning = false;

		alert = new Animation();
		alert.addFrame(Assets.alarm1, 100);
		alert.addFrame(Assets.alarm2, 100);

		magnet = new Animation();
		magnet.addFrame(Assets.bf1, 100);
		magnet.addFrame(Assets.bf2, 100);
		magnet.addFrame(Assets.bf3, 100);
		magnet.addFrame(Assets.bf4, 100);
		currentMagnet = magnet.getImage();

		elecBase = new Animation();
		elecBase.addFrame(Assets.elecbase1, 30);
		elecBase.addFrame(Assets.elecbase2, 30);
		elecBase.addFrame(Assets.elecbase3, 30);
		elecBase.addFrame(Assets.elecbase4, 30);
		elecBase.addFrame(Assets.elecbase5, 30);
		elecBase.addFrame(Assets.elecbase6, 30);
		elecBase.addFrame(Assets.elecbase5, 30);
		elecBase.addFrame(Assets.elecbase4, 30);
		elecBase.addFrame(Assets.elecbase3, 30);
		elecBase.addFrame(Assets.elecbase2, 30);

		elecBase2 = new Animation();
		elecBase2.addFrame(Assets.elecbasea, 30);
		elecBase2.addFrame(Assets.elecbaseb, 30);
		elecBase2.addFrame(Assets.elecbasec, 30);
		elecBase2.addFrame(Assets.elecbased, 30);
		elecBase2.addFrame(Assets.elecbasee, 30);
		elecBase2.addFrame(Assets.elecbasef, 30);
		elecBase2.addFrame(Assets.elecbasee, 30);
		elecBase2.addFrame(Assets.elecbased, 30);
		elecBase2.addFrame(Assets.elecbasec, 30);
		elecBase2.addFrame(Assets.elecbaseb, 30);

		nextLevel = false;
		lock = false;
		fingery = screenheight/2;
		level = 1;
		nextLevel = false;
		scoreMult = 1;
		updateInterval = 1;
		currentTC = false;
		tcDeath = false;
		tcx = 0;
		tcy = 0;
		ballDuration = 0;
		i = 0;		
		level1 = true;
		teeth = false;

		Assets.tcDrone.setLooping(true);
		Assets.gridDrone.setLooping(true);

		tempPiece = new Pieces(0, 0, true, this);	
		score = 0;

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.x > 325 && event.x< 475 && event.y > 550 && event.y< 725){
			game.getGraphics().clearScreen(Color.BLACK);			
			state = GameState.Running;
			Assets.theme.stop();
			}
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		Assets.theme.stop();
		// 1. All touch input is handled here:
		int len = touchEvents.size();

		if ((touch == false)&&circleRad<80){
			circleRad+=20;
			lock = true;
		}

		if (touch == false && !topFreeze){
			if (alert.getImage() == Assets.alarm1){
				if (scoreTempTemp < 1){
					scoreTempTemp = 1;
				}
			}
			if (alert.getImage() == Assets.alarm2 && !topFreeze){
				scoreTemp += scoreTempTemp*scoreMult;
				scoreTempTemp = 0;
			}
		}


		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if ((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) && event.y <= 1000 &&			
					((event.x < scene.getLine()+100)&&(event.x > scene.getLine()-100))&&(event.y < fingery+100)&&(event.y > fingery-100)){
				lock = false;
				if (!topFreeze){
					if (score-scoreTemp < 0)
						score = 0;
					else {
						score -= scoreTemp;
					}
					if (scoreTemp != 0){
						scoreTempDraw = scoreTemp;
						scoreTemp = 0;
					}
				}
			}

			if ((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) && event.y <= 1000&&!lock) {
				touch = true;
				scene.setLine(event.x);
				fingery = event.y;
				circleRad = 0;
			}	

			//POSITIVE BUTTON
			if (event.type == TouchEvent.TOUCH_DOWN && ((0 < event.x) && (event.x < 250)) && event.y > 1000) {
				posPressed = true;
			}

			//NEGATIVE BUTTON
			if (event.type == TouchEvent.TOUCH_DOWN && ((550 < event.x) && (event.x < 800)) && event.y > 1000) {
				negPressed = true;
			}

			//ACTIVATES ALERT 
			if (event.type == TouchEvent.TOUCH_UP) {
				touch = false;
				posPressed = false;
				negPressed = false;
			}	
		}

		//CHECKS AND UPDAteS:

		if (score < 50){
			if (level1){
				levelStart = 0;
			}
			level = 1;
			scoreMult = 1;
			recentInterval = 30;
			if (levelStart < 68){
				nextLevel = true;
				level1 = false;
			}
			else {
				nextLevel = false;
				level2 = true;

			}

		}
		if ((score>=50)&&(score < 125)){
			if (level2){
				levelStart = 0;
				level1 = true;
			}
			level = 1.2;
			scoreMult = 2;
			recentInterval = 27;
			if (levelStart < 68){
				nextLevel = true;
				level2 = false;
			}
			else {
				nextLevel = false;
				level3 = true;				
			}
		}
		if ((score>=125)&&(score < 350)){
			if (level3){
				levelStart = 0;
				level2 = true;
			}
			if (topFreeze)
				level = 1;
			else level = 1.4;
			scoreMult = 5;
			recentInterval = 24;
			if (levelStart < 68){
				nextLevel = true;
				level3 = false;
			}
			else {
				nextLevel = false;
				level4 = true;
			}
		}		
		if ((score>=350)&&(score < 1200)){
			if (level4){
				levelStart = 0;
				level3 = true;
			}
			if (topFreeze)
				level = 1;
			else level = 1.6;
			scoreMult = 10;
			recentInterval = 20;
			if (levelStart < 68){
				nextLevel = true;
				level4 = false;
			}			
			else {
				nextLevel = false;
				level5 = true;
			}
		}
		if ((score>=1200)&&(score < 2400)){
			if (level5){
				levelStart = 0;
				level4 = true;
			}
			if (topFreeze)
				level = 1.1;
			else level = 1.8;
			scoreMult = 15;
			recentInterval = 17;

			if (levelStart < 70){
				nextLevel = true;
				level5 = false;
			}
			else {
				nextLevel = false;
				level6 = true;
			}
		}
		if ((score>=2400 && score < 4000)){
			if (level6){
				levelStart = 0;
				level5 = true;
			}
			if (topFreeze)
				level = 1.2;
			else level = 2;
			scoreMult = 25;
			recentInterval = 13;
			if (levelStart < 70){
				nextLevel = true;
				level6 = false;
			}
			else {
				nextLevel = false;
				level7 = true;
			}
		}

		if ((score>=3500)){
			if (level7){
				levelStart = 0;
				level6 = true;
			}
			if (topFreeze)
				level = 1.2;
			else level = 2.3;
			scoreMult = 50;
			recentInterval = 13;
			if (levelStart < 70){
				nextLevel = true;
				level7 = false;
			}
			else {
				nextLevel = false;
				level8 = true;
			}
		}

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(6);
		int randomInt2 = randomGenerator.nextInt(7);
		int randomInt3 = randomGenerator.nextInt(45000);
		int randomInt4 = randomGenerator.nextInt(45000);
		int randomInt5 = randomGenerator.nextInt(300);
		int randomInt6 = randomGenerator.nextInt(45000);
		boolean randomBool = randomGenerator.nextBoolean();
		int chanceOfNewPiece = 8; 


		// DELAY PIECES

		if (!freeze){
			timePassed += 1;
			if ((timePassed % recentInterval) == 0){
				recent = false;
			}
			if ((timePassed % recentToothInterval ) == 0){
				recentTooth = false;
			}
			if (randomInt6 < 70 && (scoreMult == 15 || scoreMult == 25)){
				teeth = true;
			}
			if (randomInt5 == 200 && scoreMult >= 50){
				teeth = true;
			}


		}

		// timeCHARGE
		if ((scoreMult == 5 || scoreMult == 10)&&!currentTC&&tc==null&&!currentTG){
			//	if (!currentTC&&tc==null&&scoreMult==1){
			if (100	 > randomInt3 && !topFreeze){
				tc = new TimeCharge((randomInt2+1)*lane, 940, 5);				
				Assets.tcDrone.play();
				currentTC = true;
			}			
		}
		if ((scoreMult >= 25)&&!currentTC&&tc==null){
			if (400	 > randomInt3 && !topFreeze){
				tc = new TimeCharge((randomInt2+1)*lane, 940, 5);
				Assets.tcDrone.play();
				currentTC = true;
				tc.setSpeed(5);
			}			
		}

		if (tc != null && currentTC){			
			tc.update();

			if (tc.getY() <= 0){
				tcx = tc.x;
				tcy = tc.y;
				topFreeze = true;
				tg = null;
				pts.clear();
				tc = null;
				currentTG = false;
				//	System.out.println("1"+Assets.tcDrone.isPlaying());
				Assets.tcDrone.stop();
				//	System.out.println("2"+Assets.tcDrone.isStopped());
				//				Assets.tcDrone.seekBegin();
				currentTC = false;
			}

			if ((!touch)&&(currentTC))
				if ((Math.abs(tc.getY()-fingery)<150)&&(Math.abs(tc.getX()-scene.getLine())<150)){
					tcDeath = true;
					tcx = tc.x;
					tcy = tc.y;
					tc = null;
					Assets.TCdeath.play(100);
					//System.out.println("3"+Assets.tcDrone.isPlaying());
					Assets.tcDrone.stop();
					//System.out.println("4"+Assets.tcDrone.isStopped());
					//					Assets.tcDrone.seekBegin();
					currentTC = false;
				}
		}

		// timeGRID
		if ((scoreMult == 10 && scoreMult < 15)&&!currentTG&&tg==null&&!currentTC){
			if (100 > randomInt4 && !topFreeze){
				tg = new TimeGrid(1050);	
				currentTG = true;				
				Assets.gridDrone.play();
				gridX = -10;
				gridY = -10;
			}		
		}
		if ((scoreMult >= 50)&&!currentTG&&tg==null){
			if (400 > randomInt4 && !topFreeze){
				tg = new TimeGrid(1050);	
				currentTG = true;				
				Assets.gridDrone.play();
				tg.setSizeSpeed(2);
				gridX = -10;
				gridY = -10;
			}	
		}

		if (tg != null){
			if (tg.getY()-tg.getSize() < (screenheight/2-200)){
				tgDeath = true;
				tgTemp = tg;
				tg = null;
				currentTG = false;
				Assets.gridDrone.stop();
			}
		}

		if (tg != null){
			tg.update();
			for (GridLine gl: tg.getGLs()){
				gl.setY(gl.getY()+tg.getGridSpeed());
				if (gl.getY()>1200){				
					gl.setY(0);					
				}
			}
		}			

		// TEETH
		if ((randomInt < chanceOfNewPiece)&&!recentTooth&&!topFreeze&&teeth) {
			PosTriangle pt = new PosTriangle(randomBool);
			

			if (heights.size() >= 2 && currentTG){
				heights.poll();
			}
			else if (heights.size() >= 5){
				heights.poll();
			}

			if (!heights.contains(pt.getHeight())){
				if (currentTG)
					if (tg.getY()-tg.getSize() > pt.getHeight()){
						pts.add(pt);
						Assets.teeth.play(100);
						heights.add(pt.getHeight());
						recentTooth = true;
						toothCount++;
					}
				if (!currentTG) {
					pts.add(pt);
					Assets.teeth.play(100);
					heights.add(pt.getHeight());
					recentTooth = true;
					toothCount++;
				}
			}
		}
		if (freeze){
			pts.clear();
		}

		if (!freeze){
			Iterator<PosTriangle> it2 = pts.iterator();
			while (it2.hasNext()) {
				PosTriangle posT = it2.next();
				posT.moveTri();				
				if ((posT.getSide() && posT.getX() > 800) || (!posT.getSide() && posT.getX() < 0)){
					it2.remove();
				}
			}
		}	
		if (toothCount >= 25){
			teeth = false;
			toothCount = 0;
		}


		// Pieces
		if ((randomInt < chanceOfNewPiece)&&!recent) {
			Pieces p = new Pieces((randomInt2+1)*lane, 960, randomBool, this);
			if (currentTG){
				Assets.gridVoice.play(100);
			}
			p.wayback = false;
			p.setSpeedX(p.getSpeedX()*level);
			//	System.out.println("yep");
			if (p.getSpeedX() == tempPiece.getSpeedX())
				pieces.add(p);
			else
				tempPiece = p;
			exitCases = false;
			newPiece = true;
			freeze = false;

			//System.out.println(pieces);
			recent = true;

		}

		Iterator<Pieces> it = pieces.iterator();
		while (it.hasNext()) {
			Pieces p = it.next();			

			if (p.y>=p.getGenPoint()-22&&topFreeze&&p.wayback&&!negPressed&&!posPressed&&!exitCases){
				if (postScore<score){
					postScore = score;
					newHigh = true;
				}
				else tempyScore = score;
				tc = null;
				//Assets.tcDrone.pause();
				tg = null;
				currentTC = false;
				currentTG = false;
				Assets.gridDrone.stop();
				topFreeze = false;				
				score = 0;
				freeze = false;
				wrongButton = false;
				//recent = false;
				scoreReset = true;
				p.wayback = false;
				pieces = new ArrayList<Pieces>();
				Assets.failSound.play(100);
				exitCases = true;
				freezeDur = 0;
				teeth = false;
				//	System.out.println("yes1");
			}

			else if (p.isVisible()&&topFreeze&&!freeze&&p.wayback&&!negPressed&&!posPressed&&!exitCases){
				p.setBackspeed(15);		
				p.updateback();
				System.out.println("yes2");
			}


			else if (p.isVisible()&&!p.wayback&&!freeze&&currentTG&&p.getY()>tg.getY()-tg.getSize()&&!exitCases){
				System.out.println("yes3");
				p.setY((tg.getY()-tg.getSize())-15);
				//				if (currentTG){

				gridX = p.getX();
				gridY = p.getY();
				newGridPiece = true;
				

				//				}
			}

			else if (p.isVisible()&&!p.wayback&&!freeze&&!exitCases){
				for (PosTriangle pt : pts){
					if (p.getX()-40 <= pt.getX() && p.getX() + 40 >= pt.getX()
							&& p.getY()+45 >= pt.getHeight() && p.getY()-25 <= pt.getHeight()){
						if (!p.getSwitched()){
							p.switchType();							
						}
					}
				}

				p.update();
				newPiece = false;

				//	System.out.println("yes4");
			}

			else if (p.wayback && negPressed && !exitCases){

				if (p.type){
					if (score-10*scoreMult < 0){
						score = 0;
					}
					else if (!topFreeze)
						score -= 10*scoreMult;
					tcx = p.getX();
					tcy = p.getY();
					typePass = true;
					lightningDuration = 0;
					freeze = false;
					negPressed = false;
					Assets.click.play(150);
					lightning = true;
					killx = p.x;
					killy = p.y;
					lightning = true;
					it.remove();

				}
				if (!p.type){
					freeze = false;
					negPressed = false;
					wrongButton = true;
					if (postScore<score){
						postScore = score;
						newHigh = true;
					}
					tempyScore = score;
					tc = null;
					Assets.tcDrone.pause();
					tg = null;
					pieces = new ArrayList<Pieces>();
					Assets.failSound.play(150);
					currentTC = false;
					currentTG = false;
					Assets.gridDrone.stop();
					topFreeze = false;
					freezeDur = 0;
					scoreReset = true;
					score = 0;
					it.remove();
					freeze = false;
					wrongButton = false;
					exitCases = true;
					teeth = false;
				}
			}
			else if (p.wayback && posPressed){

				if (p.type){
					freeze = false;
					posPressed = false;
					wrongButton = true;
					if (postScore<score){
						postScore = score;
						newHigh = true;
					}
					tempyScore = score;
					tc = null;
					Assets.tcDrone.pause();
					tg = null;
					pieces = new ArrayList<Pieces>();
					Assets.failSound.play(150);
					currentTC = false;
					currentTG = false;
					Assets.gridDrone.stop();
					topFreeze = false;
					freezeDur = 0;
					scoreReset = true;
					score = 0;
					it.remove();
					freeze = false;
					wrongButton = false;
					exitCases = true;
					teeth = false;

				}
				if (!p.type){
					if (score-10*scoreMult < 0){
						score = 0;
					}
					else if (!topFreeze)
						score -= 10*scoreMult;
					tcx = p.getX();
					tcy = p.getY();
					typePass = false;
					lightningDuration = 0;
					killx = p.x;
					killy = p.y;
					it.remove();
					freeze = false;
					posPressed = false;		
					lightning = true;
					Assets.click.play(150);
				}

			}			
			else if (!topFreeze&&p.isVisible()&&p.wayback&&!freeze&&!exitCases&&!newPiece){
				if (scoreMult <= 15)
					p.setBackspeed(25);
				if (scoreMult > 15)
					p.setBackspeed(30);
				p.updateback();
				freeze = true;
				//	System.out.println("yes7");
			}

			else if (p.isVisible()&&freeze&&!exitCases&&p.wayback){
				if (scoreMult <= 15)
					p.setBackspeed(25);
				if (scoreMult > 15)
					p.setBackspeed(30);
				p.updateback();

				//	System.out.println("yes7b");
			}

			else if (p.y < 10){
				score += 1*scoreMult;
				if (p.type)
					Assets.posPoint.play(20);
				if (!p.type)
					Assets.negPoint.play(40);

				it.remove();
				freeze = false;
				wrongButton = false;
				//	System.out.println("yes8");

			}
			//			else if (p.y > 950&&p.wayback){
			//				System.out.println("yes9");
			//			}

			//FINAL			
			else if (!exitCases&&p.y >= p.getGenPoint()-15){
				if (postScore<score){
					postScore = score;
					newHigh = true;
				}
				else tempyScore = score;
				//	System.out.println("yes10");
				//tempyScore = score;
				//				System.out.println(score);
				//				System.out.println(tempyScore);
				tc = null;
				Assets.tcDrone.pause();
				tg = null;
				pieces = new ArrayList<Pieces>();
				Assets.failSound.play(100);
				currentTC = false;
				currentTG = false;
				Assets.gridDrone.stop();
				topFreeze = false;				
				freezeDur = 0;
				it.remove();
				freeze = false;
				wrongButton = false;
				exitCases = true;
				scoreReset = true;
				score = 0;
				teeth = false;
			}
		}

	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		Assets.theme.play();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP&& event.x >150 && event.x < 650 && event.y<175) {
				state = GameState.Running;
				Assets.click2.play(100);
				Assets.theme.play();
			}
			if (event.type == TouchEvent.TOUCH_UP && event.x > 300 && event.x < 500 && event.y > 850 && event.y< 975) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			
			if (event.type == TouchEvent.TOUCH_UP && event.x > 225 && event.x < 585 && event.y > 390 && event.y< 510) {
				//nullify();
				Assets.theme.stop();
				Assets.click2.play(100);
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				nullify();
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	@Override
	public void paint(float deltaTime) {

		//Debug.startMethodTracing();
		Graphics g = game.getGraphics();
		alert.update(10);
		g.drawRect(0, 1196, 801, 10, Color.BLACK);


		//BOARD ELEMENTS / SCORE
		if (state == GameState.Running){

			int fingerx = scene.getLine();

			if (topFreeze){
				g.drawRect(0, 0, g.getWidth(), 1202, Color.GRAY);
				g.drawString(String.valueOf(score),
						402, 1150, paint3);
				g.drawString("high "+String.valueOf(postScore),
						402, 1190, paint6);
			}

			if (!topFreeze){

				g.drawRect(fingerx, 0, g.getWidth(), 1205, Color.BLACK);
				g.drawRect(0, 0, fingerx+2, 1202, Color.WHITE);

				if (!freeze&&!topFreeze){
					baseGrowth = 0;		
				}

				if (touch == false){

					drawTimer = 0;
					paint4.setAlpha(255);
					if ((alert.getImage() == Assets.alarm2)&&circleRad>79){
						circleRad += 1;
					}
					if ((alert.getImage() == Assets.alarm1)&&circleRad>79){
						if (circleRad>80)
							circleRad -= 1;
					}
					g.drawCircFill(fingerx, fingery, circleRad, Color.GRAY, 90);
					g.drawCircOut(fingerx, fingery, circleRad, Color.RED, 5);
					g.drawCircOut(fingerx, fingery, (circleRad/5)*4, Color.RED, 5);
					g.drawCircOut(fingerx, fingery, (circleRad/5)*3, Color.RED, 5);
					g.drawString("-"+String.valueOf(scoreTemp), fingerx, (int)fingery+15, paint4);
				}

				if (touch == true){
					if (drawTimer < 40){
						paint4.setAlpha(200 - drawTimer*4);
						g.drawString("-"+String.valueOf(scoreTempDraw), 402, 950-(drawTimer), paint4);
						drawTimer += 1;
					}
				}		

				if (!scoreReset){

					g.drawString(String.valueOf(score),
							402, 1150, paint3);
					g.drawString("high "+String.valueOf(postScore),
							402, 1190, paint6);					

				}
				if (scoreReset){

					paint3.setColor(Color.RED);
					paint6.setColor(Color.RED);
					if (scoreDeathDur < 40){
						paint11.setAlpha(255 - scoreDeathDur*6);
						paint12.setAlpha(255 - scoreDeathDur*6);
						if (newHigh){
							g.drawString("NEW HIGH SCORE!",
									402, 420, paint11);
							g.drawString(String.valueOf(postScore), 402, 550, paint12);
						}
						else {
							g.drawString("YA BLEW IT!", 402, 420, paint11);
							g.drawString(String.valueOf(tempyScore), 402, 550, paint12);
						}

						g.drawString(String.valueOf(score),
								402, 1150, paint3);
						g.drawString("high "+String.valueOf(postScore),	402, 1190, paint6);
						g.drawCircOut(400, 1190, scoreDeathDur*60, Color.RED, 10, 255- scoreDeathDur*6);

						scoreDeathDur++;
					}
					else {
						scoreReset = false;
						paint3.setColor(Color.BLUE);
						paint6.setColor(Color.BLUE);
						scoreDeathDur = 0;
						newHigh = false;
					}
				}
			}

			if (nextLevel&&scoreMult>1){
				if (levelStart < 70){
					paint5.setAlpha(255-(levelStart*3));
					paint7.setAlpha(255-(levelStart*3));
					g.drawString(String.valueOf(scoreMult), g.getWidth()/2, g.getHeight()/2-100, paint5);
					if (scoreMult == 1){
						g.drawString("x", (g.getWidth()/2)-50, g.getHeight()/2-100, paint7);
					}
					if ((scoreMult > 1)&&(scoreMult<10)){						
						g.drawString("x", (g.getWidth()/2)-70, g.getHeight()/2-100, paint7);
					}
					if ((scoreMult>=10)&&(scoreMult<20)){						
						g.drawString("x", (g.getWidth()/2)-90, g.getHeight()/2-100, paint7);
					}					
					if ((scoreMult>=20)){						
						g.drawString("x", (g.getWidth()/2)-120, g.getHeight()/2-100, paint7);
					}

					levelStart++;
				}
			}

			//SPRITES

			for (Pieces p : pieces){

				if (p.getSwitched()&&!p.wayback){
					if (p.type){
						p.updateTimer();
						g.drawCircFill(p.x, p.y+39, 50, Color.MAGENTA, p.getTimer()*10);
						if (p.getTimer()==0)
							p.resetSwitched();
					}
					if (!p.type){
						p.updateTimer();
						g.drawCircFill(p.x, p.y+35, 50, Color.MAGENTA, p.getTimer()*10);
						if (p.getTimer()<=1)
							p.resetSwitched();
					}
				}
				if (p.type == true)
					g.drawImage(Assets.pos, (p.x - 40), p.y);

				if (p.type == false)
					g.drawImage(Assets.neg, (p.x - 40), p.y);

				if (p.wayback){
					if (alert.getImage() == Assets.alarm2 && !topFreeze){
						g.drawCircOut(p.x, p.y+39, 40, Color.RED, 20);
					}
				}	
				if (p.wayback){
					if (topFreeze && p.equals(pieces.get(0))){
						g.drawCircFill(p.x, p.y+35, 40, Color.YELLOW, 205-p.y/6);
					}
				}

			}
			if (i < 30){
				i += 2;
			}
			else {
				i = 0;
			}
			if (tc != null){
				g.drawCircBlue(tc.getX(), tc.getY(), 90, Color.rgb(255,215,0), i);
			}

			if (tcDeath){
				if (ballDuration < 20){
					g.drawCircFill(tcx, tcy, 100, Color.BLUE, 200-ballDuration*10);
					ballDuration++;				
				}
				else {
					tcDeath = false;
					ballDuration = 0;
				}
			}

			//GRID

			if (recent&&currentTG&&newGridPiece){

				if (gridPieceCount < 11){

					g.drawCircFill(gridX, 1005, 35, Color.GREEN, 255-gridPieceCount*25);
					g.drawCircFill(gridX, gridY+15, 35, Color.GREEN, 255-gridPieceCount*25);

					gridPieceCount++;
				}
				else {
					gridPieceCount = 0;
					newGridPiece = false;
					gridX = -100;
					gridY = -100;
				}				
			}

			if (tg != null){
				// horizontal

				for (GridLine gl: tg.getGLs()){
					if (gl.getY()<1050&&gl.getY()-5>tg.getY()-tg.getSize())
						g.drawLine(-1, gl.getY(), 801, gl.getY(), Color.GREEN, 255, 12, paint10);
				}		

				// vertical

				g.drawLine(1, 1050, 1, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(81, 1050, 81, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(161, 1050, 161, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(241, 1050, 241, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(321, 1050, 321, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(401, 1050, 401, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(481, 1050, 481, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(561, 1050, 561, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(641, 1050, 641, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(721, 1050, 721, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
				g.drawLine(801, 1050, 801, tg.getY()-tg.getSize(), Color.GREEN, 255, 12, paint10);
			}

			if (tgDeath){
				if (lineDuration < 20){
					//g.drawCircFill(tcx, tcy, 100, Color.BLUE, 200-lineDuration*10);
					//g.saveCanvas();
					//g.drawTransRect(0, tgTemp.getY(), g.getWidth(), tgTemp.getY()-tgTemp.getSize());

					for (GridLine gl: tgTemp.getGLs()){
						if (gl.getY()<1050&&gl.getY()-5>tgTemp.getY()-tgTemp.getSize())
							g.drawLine(-1, gl.getY(), 801, gl.getY(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					}		
					//g.restoreCanvas();

					g.drawLine(1, tgTemp.getY(), 1, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(81, tgTemp.getY(), 81, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(161, tgTemp.getY(), 161, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(241, tgTemp.getY(), 241, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(321, tgTemp.getY(), 321, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(401, tgTemp.getY(), 401, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(481, tgTemp.getY(), 481, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(561, tgTemp.getY(), 561, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(641, tgTemp.getY(), 641, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(721, tgTemp.getY(), 721, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);
					g.drawLine(801, tgTemp.getY(), 801, tgTemp.getY()-tgTemp.getSize(), Color.GREEN, 200-lineDuration*10, 12, paint10);

					lineDuration++;				
				}
				else {
					tgDeath = false;
					lineDuration = 0;
				}
			}

			// TEETH

			//if (scoreMult == 10){
			for (PosTriangle posT : pts){			
				if (!posT.getSide()){
					if (posT.getX()>100)
						g.drawPosTri(posT, 280-(posT.getX()/3));
					else g.drawPosTri(posT, 255);
				}
				if (posT.getSide())
					if (posT.getX()<700)
						g.drawPosTri(posT, (posT.getX()/3)-247);
					else g.drawPosTri(posT, 255);

				//g.drawPosTri(1, 200, 255);
			}

			// LAUNCHERS

			int launchCol = 0;
			if (topFreeze)
				launchCol = Color.RED;
			if (!topFreeze)
				launchCol = Color.GRAY;

			g.drawCircFill(lane, 1005, 40, launchCol, 100);
			g.drawCircFill(lane*2, 1005, 40, launchCol, 100);
			g.drawCircFill(lane*3, 1005, 40, launchCol, 100);
			g.drawCircFill(lane*4, 1005, 40, launchCol, 100);
			g.drawCircFill(lane*5, 1005, 40, launchCol, 100);
			g.drawCircFill(lane*6, 1005, 40, launchCol, 100);
			g.drawCircFill(lane*7, 1005, 40, launchCol, 100);

			if (freeze||topFreeze) {
				g.drawImage(elecBase.getImage(), 604, 1055);
				g.drawImage(elecBase2.getImage(), 64, 1055);
				elecBase.update(10);
				elecBase2.update(10);
				if (baseGrowth <= 125)
					baseGrowth+=35;

				int buttonCol = 0;
				if (!topFreeze)
					buttonCol = Color.BLUE;
				if (topFreeze)
					buttonCol = Color.YELLOW;

				g.drawCircOut(130, 1120, baseGrowth+5, buttonCol, 10, 140);
				g.drawCircOut(130, 1120, (baseGrowth/5)*4+5, buttonCol, 10, 140);
				g.drawCircOut(130, 1120, (baseGrowth/5)*3+5, buttonCol, 10, 140);
				g.drawCircOut(670, 1120, baseGrowth+5, buttonCol, 10, 120);
				g.drawCircOut(670, 1120, (baseGrowth/5)*4+5, buttonCol, 10, 140);
				g.drawCircOut(670, 1120, (baseGrowth/5)*3+5, buttonCol, 10, 140);
			}

			//LIGHTNING
			if (lightning){
				if (lightningDuration < 20){

					paint13.setAlpha(200-lightningDuration*10);
					g.drawString("-"+ String.valueOf(10*scoreMult), killx, killy-15, paint13);

					g.drawLine(100, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);
					g.drawLine(200, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);
					g.drawLine(300, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);
					g.drawLine(400, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);
					g.drawLine(500, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);
					g.drawLine(600, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);
					g.drawLine(700, 977, killx, killy+39, Color.BLUE, 200-lightningDuration*10, 12);

					g.drawCircFill(lane, 1005, 35, Color.BLUE, 200-lightningDuration*10);
					g.drawCircFill(lane*2, 1005, 35, Color.BLUE, 200-lightningDuration*10);
					g.drawCircFill(lane*3, 1005, 35, Color.BLUE, 200-lightningDuration*10);
					g.drawCircFill(lane*4, 1005, 35, Color.BLUE, 200-lightningDuration*10);
					g.drawCircFill(lane*5, 1005, 35, Color.BLUE, 200-lightningDuration*10);
					g.drawCircFill(lane*6, 1005, 35, Color.BLUE, 200-lightningDuration*10);
					g.drawCircFill(lane*7, 1005, 35, Color.BLUE, 200-lightningDuration*10);

					if (typePass)
						g.drawImage(Assets.pos, tcx-40, tcy, 200-lightningDuration*10);
					else g.drawImage(Assets.neg, tcx-40, tcy, 200-lightningDuration*10);

					if (lightningDuration == 1)
						g.drawRect(0, 0, g.getWidth()+1, g.getHeight()+1, Color.rgb(255,215,0));
					lightningDuration++;
				}
				else {
					lightning = false;
					lightningDuration = 0;
				}
			}

			//MAGNET ANIMATION
			if (topFreeze){				
				if (freezeDur < 250)	{
					if (i < 30){
						i += 1;
					}
					else {
						i = 0;
					}
					paint8.setAlpha(255-freezeDur);
					g.drawRect2(-5, 0, g.getWidth()+5, i, Color.rgb(255,215,0), paint8);
					g.drawCircOut(400, 0, i*50, Color.YELLOW, 10, 255-freezeDur);
					freezeDur++;
				}
				else {
					topFreeze = false;
					freezeDur = 0;	
					i = 0;
					pieces.clear();
				}
			}

			else {
				if (j < 30){
					j += .3;
				}
				else {
					j = 0;
				}
				lg = new LinearGradient(600, -5 , 600, 40+Math.round(j), Color.BLUE, Color.alpha(0), android.graphics.Shader.TileMode.CLAMP);
				paint9.setShader(lg);
				g.drawRect2(0, 0, g.getWidth(), 100, Color.BLUE, paint9);
				magnet.update(10);
			}
		}


		// draw the UI
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

		//Debug.stopMethodTracing();

		// OLD JUNK
		////g.drawImage(back, 0, 0);
		//			g.drawScaledImage(Assets.fore, fingerx, 0, g.getWidth()-fingerx, 
		//					Assets.fore.getHeight(), fingerx, 0, g.getWidth()-fingerx, Assets.fore.getHeight());		
		////g.drawImage(Assets.topwhite, 0, 0);
		//g.saveCanvas();
		//g.drawTransRect(0, 0, fingerx, g.getHeight());
		////g.drawImage(fore, 0, 0);			
		//g.drawImage(base2, 0, 955);			
		//			g.drawScaledImage(Assets.back, 0, 0, fingerx, Assets.back.getHeight(), 0, 0, fingerx, base2.getHeight());				
		//			g.drawScaledImage(base2, fingerx, 955, g.getWidth()-fingerx, base2.getHeight(), 
		//			fingerx, 0, g.getWidth()-fingerx, base2.getHeight());
		//g.drawCropped(base2);
		////g.drawImage(rings2, 0, 950);			
		//g.restoreCanvas();
	}
	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		scene = null;
		pieces = null;	
		scene = null;		
		paint2 = null;
		paint3 = null;
		Assets.theme = null;
		Assets.click = null;
		// Call garbage collector to clean up memory.
		System.gc();
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		//		g.drawARGB(155, 0, 0, 0);
		//		g.drawString("TOUCH THE SCREEN YA DUMMY",
		//				400, 300, paint);
		g.drawImage(Assets.rdscreen, 0, 0);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
	}


	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		//		g.drawRect(0, 0, 801, 1281, Color.BLACK);
		//		g.drawString("HEY GUY IT'S PAUSED", 640, 300, paint);
		g.drawImage(Assets.pauseScreen, 0, 0);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER BRO", 640, 300, paint);
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			System.gc();
		Assets.tcDrone.pause();
		Assets.gridDrone.pause();
		Assets.theme.pause();
		state = GameState.Paused;
	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
		Assets.theme.play();
		

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		Assets.tcDrone.pause();
		Assets.gridDrone.pause();
		pause();
	}

	public static Scene getScene(){
		return scene;
	}

	public void setScore(int i){
		score += i;
	}

	public boolean getTopFreeze(){
		return topFreeze;
	}


}