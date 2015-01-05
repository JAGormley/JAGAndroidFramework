package com.jag.positron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Debug;
import android.os.SystemClock;

import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.Image;

import com.jag.framework.Graphics.ImageFormat;
import com.jag.framework.Input.TouchEvent;
import com.jag.framework.Screen;
import com.jag.positron.Coil.Point;
//import com.jag.positron.Tutorial.TutState;
import com.jag.positron.Tooltips.Tip;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	// Variable Setup
	public static Scene scene;
	public static int screenheight;
	public static int screenwidth;
	public static Game thisGame;
	public static int lane;

	// screen h/w
	int sh;
	int sw;

	public ArrayList<Pieces> pieces;
	public ArrayList<PosTriangle> pts;
	Queue<Integer> heights = new LinkedList<Integer>();

	public Particle pcl;

	Paint paint, paint2, paint3, paint4;
	public Paint cPaint;
	Image fore, rings1, rings2, base1, base2, bolt,
	lightningLineImage;

	boolean recent, freeze, touch, posPressed, negPressed, wrongButton,
	lightning, lock, nextLevel, redRing, levelInA, levelInB, level1a, level1b,
	level2, level3, level4, level5, level6, level7, level8,
	typePass;

	List<TouchEvent> touchEvents;

	public boolean scoreReset;

	int timePassed, difficulty, recentInterval, score, scoreTemp,
	scoreTempTemp, scoreTempDraw, drawTimer, killx, killy,
	lightningDuration, ballDuration, scoreMult, updateInterval, tcx,
	tcy, i, newLevel, gridInt, baseGrowth, flashUpdate;

	ArrayList<Integer> pointXs;

	float fingery, circleRad, j;
	public Animation alert, magnet, elecBase, elecBase2;

	public Paint paint5, paint6;
	public double depLevel;

	public TimeCharge tc;
	public TimeGrid tg;
	public TimeGrid tgTemp;
	public ArrayList<ShakeString> sStrings;
	public ArrayList<ShakeString> lStrings;
	public ArrayList<ShakeString> tStrings;
	public ArrayList<ShakeString> cStrings;

	public int lineDuration, scoreDeathDur, levelStart, postScore;

	public boolean currentTC, currentTG;
	public boolean tcDeath;
	public boolean tgDeath;
	public boolean topFreeze;
	public boolean newHigh;

	public int freezeDur;
	public LinearGradient lg;
	public LinearGradient lg2;

	public Paint tPaint;
	public Paint paint7;
	public Paint paint8;
	public Paint paint9;
	public Paint paint10;
	public Paint paint11;
	public Paint paint12;

	public Collider collider;
	public Level level;
	public CougarLock cLock;
	public Tooltips tooltips;
	public int tempyScore;
	public boolean newGridPiece;
	public int gridPieceCount;
	public boolean exitCases;
	public boolean newPiece;
	public int gridX;
	public int gridY;
	public Paint paint13;
	public boolean recentTooth;
	public int recentToothInterval = 13;
	public boolean teeth;
	public int toothCount;
	public Pieces tempPiece;
	public boolean frenzy = false;
	public ArrayList<TextFader> faders;
	public Animation cougar;
	protected int drawFace;
	private Paint lockPaint;
	public TopLock tLock;
	private PosTimer fDeathTimer;
	protected PosTimer topFade;
	protected PosTimer topFadeDeath;
	protected int freezeScore;
	protected Paint freezeScorePaint;
	protected boolean topFreezeWin;
	protected PosTimer topFadeFinal;
	private Paint freezeScorePaint2;
	private int lowerDist;
	private Bitmap orangeCoug;
	private Bitmap blueCoug;
	private int tempMult;
	private boolean tracing;
	private Slider slider;
	private Magnet mag;
	private boolean fingerMove;
	public static Graphics graph;
	private Message multMessage;
	private Game freshGame;

	private PosTimer frameTimer;
	private int frameCount = 0;

	private int frames;

	private boolean obsDeath;

	private float current;

	private float previous;


	// private PosTriangle posT;

	public GameScreen(Game game) {
		super(game);
		freshGame = game;

		// Initialize game objects here

		sh = game.getLoadHeight();
		sw = game.getLoadWidth();

		graph = game.getGraphics();

		scene = new Scene(sw / 2);
		screenheight = game.getGraphics().getHeight();
		screenwidth = game.getGraphics().getWidth();
		thisGame = game;

		setPieces(new ArrayList<Pieces>());
		pts = new ArrayList<PosTriangle>();
		sStrings = new ArrayList<ShakeString>();
		lStrings = new ArrayList<ShakeString>();
		tStrings = new ArrayList<ShakeString>();
		cStrings = new ArrayList<ShakeString>();

		tracing = false;

		pointXs = new ArrayList<Integer>();

		lane = sw / 8;
		recent = true;

		collider = Collider.getInstance();
		collider.reset();
		collider = Collider.getInstance();

		slider = Slider.getInstance();
		mag = Magnet.getInstance();
		level = Level.getInstance();
		collider.reset();
		tooltips = Tooltips.getInstance();
		tooltips.activate(Tip.MOVE);

		lg = new LinearGradient(sh / 2, -(sh / 12), sh / 2,
				(float) (sh * .033), Color.BLUE, Color.alpha(0),
				android.graphics.Shader.TileMode.CLAMP);
		lg2 = new LinearGradient(sh / 2, (float) (sh * .66), sh / 2,
				(float) (sh * .813), Color.GREEN, Color.alpha(0),
				android.graphics.Shader.TileMode.CLAMP);

		paint = new Paint();
		paint.setTextSize(Math.round(sh * .025));
		paint.setTextAlign(Paint.Align.CENTER);
		//		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		paint2 = new Paint();
		paint2.setColor(Color.RED);
		paint2.setTypeface(Assets.font);
		paint2.setTextSize(Math.round(sh * .027));
		paint2.setTextAlign(Paint.Align.LEFT);

		paint3 = new Paint();
		paint3.setTypeface(Assets.font);
		paint3.setTextSize(Math.round(sh * .1));
		paint3.setTextAlign(Paint.Align.CENTER);
		//		paint3.setAntiAlias(true);
		paint3.setColor(Color.GRAY);

		paint4 = new Paint();
		paint4.setTypeface(Assets.font);
		paint4.setTextSize(Math.round(sh * .027));
		paint4.setTextAlign(Paint.Align.CENTER);
		paint4.setAlpha(40);
		//		paint4.setAntiAlias(true); 
		paint4.setColor(Color.MAGENTA);

		paint5 = new Paint();
		paint5.setTypeface(Assets.font);
		paint5.setTextSize(Math.round(sh * .15));
		paint5.setTextAlign(Paint.Align.CENTER);
		//		paint5.setAntiAlias(true);
		paint5.setColor(Color.MAGENTA);
		paint5.setShadowLayer(1, -5, -5, Color.GRAY);

		paint6 = new Paint();
		paint6.setTypeface(Assets.font);
		paint6.setTextSize(Math.round(sh * .033));
		paint6.setTextAlign(Paint.Align.CENTER);
		//		paint6.setAntiAlias(true);
		paint6.setColor(Color.GRAY);

		paint7 = new Paint();
		paint7.setTypeface(Assets.font);
		paint7.setTextSize(Math.round(sh * .066));
		paint7.setTextAlign(Paint.Align.CENTER);
		//		paint7.setAntiAlias(true);
		paint7.setColor(Color.MAGENTA);
		paint7.setShadowLayer(1, -5, -5, Color.GRAY);

		paint8 = new Paint();
		paint8.setColor(Color.rgb(255, 215, 0));
		paint8.setStyle(Style.FILL);

		paint9 = new Paint();
		// paint9.setMaskFilter(new BlurMaskFilter(40, Blur.INNER));
		paint9.setShader(lg);

		paint10 = new Paint();
		paint10.setShader(lg2);

		paint11 = new Paint();
		paint11.setTypeface(Assets.font);
		paint11.setTextSize(Math.round(sh * .05));
		paint11.setTextAlign(Paint.Align.CENTER);
		//		paint11.setAntiAlias(true);
		paint11.setColor(Color.GRAY);
		paint11.setShadowLayer(1, -5, -5, Color.MAGENTA);

		paint12 = new Paint();
		paint12.setTypeface(Assets.font);
		paint12.setTextSize(Math.round(sh * .133));
		paint12.setTextAlign(Paint.Align.CENTER);
		//		paint12.setAntiAlias(true);
		paint12.setColor(Color.GRAY);
		paint12.setShadowLayer(1, -5, -5, Color.MAGENTA);

		paint13 = new Paint();
		paint13.setTypeface(Assets.font);
		paint13.setTextSize(Math.round(sh * .066));
		paint13.setTextAlign(Paint.Align.CENTER);
		//		paint13.setAntiAlias(true);
		paint13.setColor(Color.BLUE);

		tPaint = new Paint();
		tPaint.setTypeface(Assets.font);
		tPaint.setTextSize(Math.round(sh * .033));
		tPaint.setTextAlign(Paint.Align.CENTER);
		tPaint.setColor(Color.MAGENTA);

		cPaint = new Paint();
		cPaint.setTypeface(Assets.font);
		cPaint.setTextSize(Math.round(sh * .066));
		cPaint.setTextAlign(Paint.Align.CENTER);
		cPaint.setColor(Color.MAGENTA);

		freezeScorePaint = new Paint();
		freezeScorePaint.setTypeface(Assets.font);
		freezeScorePaint.setTextSize(Math.round(sh * .6));
		freezeScorePaint.setTextAlign(Paint.Align.CENTER);
		//		freezeScorePaint.setAntiAlias(true);
		freezeScorePaint.setColor(Color.CYAN);

		freezeScorePaint2 = new Paint();
		freezeScorePaint2.setTypeface(Assets.font);
		freezeScorePaint2.setTextSize(Math.round(sh * .1));
		freezeScorePaint2.setTextAlign(Paint.Align.CENTER);
		//		freezeScorePaint2.setAntiAlias(true);
		freezeScorePaint2.setColor(Color.CYAN);

		lockPaint = new Paint();
		lockPaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));

		recentInterval = 40;
		timePassed = 0;
		freeze = false;
		wrongButton = false;
		lightning = false;

		setAlert(new Animation());
		getAlert().addFrame(Assets.alarm1, 100);
		getAlert().addFrame(Assets.alarm2, 100);

		cougar = new Animation();
		cougar.addFrame(Assets.coug1, 100);
		cougar.addFrame(Assets.coug2, 100);
		cougar.addFrame(Assets.coug3, 100);
		cougar.addFrame(Assets.coug4, 100);
		cougar.addFrame(Assets.coug5, 100);

		cLock = new CougarLock(game.getGraphics(), cougar);
		cLock.setActive(false);

		nextLevel = false;
		lock = false;
		fingery = (float) (screenheight * .91);
		depLevel = 1;
		nextLevel = false;
		scoreMult = 1;
		updateInterval = 1;
		currentTC = false;
		tcDeath = false;
		tcx = 0;
		tcy = 0;
		ballDuration = 0;
		i = 0;
		level1a = true;
		teeth = false;

		Assets.tcDrone.setLooping(true);
		Assets.gridDrone.setLooping(true);

		tempPiece = new Pieces(0, 0, true, this, level.getRecentInterval());
		score = ControlPanel.score;
	}

	@Override
	public void update(float deltaTime) {

		touchEvents = game.getInput().getTouchEvents();

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
			if (event.x > Math.round(sh * .27)
					&& event.x < Math.round(sh * .394)
					&& event.y > Math.round(sh * .46)
					&& event.y < Math.round(sh * .6)) {
				game.getGraphics().clearScreen(Color.BLACK);
				state = GameState.Running;
				Assets.theme.stop();
				if (ControlPanel.trace)
					Debug.startMethodTracing();
			}
		}
	}

	public void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

		Assets.theme.stop();
		// 1. All touch input is handled here:
		int len = touchEvents.size();

		if ((touch == false) && circleRad < Math.round(sh * .066)) {
			circleRad += Math.round(sh * .017);
			lock = true;
		}

		if (touch == false && !topFreeze) {
			if (getAlert().getImage() == Assets.alarm1) {
				if (scoreTempTemp < 1) {
					scoreTempTemp = 1;
				}
			}
			if (getAlert().getImage() == Assets.alarm2 && !topFreeze) {
				scoreTemp += scoreTempTemp * level.getScoreMult();
				scoreTempTemp = 0;
			}
		}
		// TOUCH
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			fingerMove = false;

			if (((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN))
					&& currentTC && event.y < Coil.origin.y) {
				int tcBounds = Assets.coug1.getHeight();
				if (event.y >= tc.y-tcBounds 
						&& event.y <= tc.y+tcBounds
						&& event.x >= tc.x-tcBounds
						&& event.x <= tc.x+tcBounds){
					obsDeath("c");
				}
			}

			if ((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN)
					&& event.y >= Math.round(sh * .83)
					&& ((event.x < scene.getLine() + Math.round(sh * .083)) && (event.x > scene
							.getLine() - Math.round(sh * .083)))
							&& (event.y > fingery - Math.round(sh * .075))) {
				lock = false;
			}

			if ((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN)
					&& !lock) {
				if (event.y >= Math.round(sh * .83) && !topFreeze){
					touch = true;
					scene.setLine(event.x);
					fingerMove = true;
					fingery = event.y;
					circleRad = 0;
				}
			}

			// ACTIVATES ALERT
			if (event.type == TouchEvent.TOUCH_UP) {
				touch = false;
				posPressed = false;
				negPressed = false;
			}
		}

		// CHECKS AND UPDAteS:
		level.update(score, scoreReset);

		//		System.out.println("mult: "+level.getScoreMult());
		//		System.out.println("level: "+level.getLevel());
		//		System.out.println("speed: "+level.getSpriteSpeed());

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(6);
		int randomInt2 = randomGenerator.nextInt(7);
		boolean randomBool = randomGenerator.nextBoolean();
		int chanceOfNewPiece = 8;


		// DELAY PIECES
		if (!freeze && !scoreReset && !cLock.getStartup()) {
			timePassed += 1;
			if ((timePassed % level.getRecentInterval()) == 0) {
				recent = false;
			}
			if ((timePassed % recentToothInterval) == 0) {
				recentTooth = false;
			}
			if (level.falcSpacer() && !level.obsGate(Level.Obs.FALCON)) {
				teeth = true;
			}
		}

		// timeCHARGE
		if (!cLock.getActive() && !currentTG 
				&& !currentTC && tc == null 
				&& !scoreReset && !level.obsGate(Level.Obs.COUGAR)){

			if (level.cougSpacer()) {
				tc = new TimeCharge((randomInt2 + 1) * lane,
						(int) Math.round(sh * .78), level.getCougSpeed());
				Assets.tcDrone.play();
				currentTC = true;
				level.nullTimer(Level.Obs.COUGAR);

			}
		}

		if (tc != null && currentTC) {			
			tc.update();

			if (tc.getY() <= 0) {
				cLock = new CougarLock(game.getGraphics(), cougar);
				tcx = tc.x;
				tcy = tc.y;
				cLock.setActive(true);
				cLock.setStartPos(tcx, tcy);
				cLock.setStartup(true);
				recent = true;
				//				tg = null;
				tc = null;
				currentTG = false;
				Assets.tcDrone.stop();
				currentTC = false;
			}
		}

		// timeGRID
		if (!currentTG && tg == null && !level.obsGate(Level.Obs.GRID)) {
			if (level.gridSpacer()) {
				tg = new TimeGrid((int) Math.round(sh * .87));
				currentTG = true;
				Assets.gridDrone.play();
				gridX = -10;
				gridY = -10;
				level.nullTimer(Level.Obs.GRID);
			}
		}

		if (tg != null) {
			if (tg.getY() - tg.getSize() < (screenheight / 2 - (int) Math
					.round(sh * .166)) || scoreReset || cLock.getActive()) {
				tgDeath = true;
				tgTemp = tg;
				tg = null;
				currentTG = false;
				Assets.gridDrone.stop();
			}
		}

		if (tg != null) {
			tg.update();
			for (GridLine gl : tg.getGLs()) {
				gl.setY(gl.getY() + level.getGridSpeed());
				if (gl.getY() > sh) {
					gl.setY(0);
				}
			}
		}

		// TEETH
		if ((randomInt < chanceOfNewPiece) && !recentTooth && !topFreeze
				&& teeth) {
			PosTriangle pt = new PosTriangle(randomBool);

			if (heights.size() >= 2 && currentTG) {
				heights.poll();
			} else if (heights.size() >= 5) {
				heights.poll();
			}

			if (!heights.contains(pt.getHeight())) {
				if (currentTG)
					if (tg.getY() - tg.getSize() > pt.getHeight()) {
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

		if (!freeze) {
			Iterator<PosTriangle> it2 = pts.iterator();
			while (it2.hasNext()) {
				PosTriangle posT = it2.next();
				posT.moveTri();
				if ((posT.getSide() && posT.getX() > (int) Math
						.round(sh * .664))
						|| (!posT.getSide() && posT.getX() < 0)) {
					it2.remove();
				}
			}
		}
		if (toothCount >= 25) {
			teeth = false;
			toothCount = 0;
			level.nullTimer(Level.Obs.FALCON);
		}

		// Pieces

		//		if (cLock.getStartup()){
		//			cLock.update();
		//		}
		if (cLock.getActive())
			cLock.update();

		// RUN TOOLTIP UPDATE
		tooltips.update(fingerMove, score);

		if ((randomInt < chanceOfNewPiece) && !recent && !scoreReset
				&& !(Tooltips.currentTip == Tip.MOVE) && !Tooltips.pointStart()) {
			int pLane = (randomInt2 + 1) * lane;
			Pieces p = null;

			p = new Pieces(pLane, (int) Math.round(sh * .9),
					randomBool, this, level.getRecentInterval());


			if (currentTG) {
				Assets.gridVoice.play(100);
			}
			//			System.out.println(p==null);
			if (p != null){
				p.wayback = false;
				//				p.setSpeedX(p.getSpeedX() * depLevel);
				p.setSpeedX(level.getSpriteSpeed());
				p.setBackspeed(level.getBackSpeed());
				if ((p.getSpeedX() == tempPiece.getSpeedX()) || (topFreeze && topFade != null))
					getPieces().add(p);
				else
					tempPiece = p;
				exitCases = false;
				newPiece = true;
				freeze = false;
				recent = true;
			}
		}


		Iterator<Pieces> it = getPieces().iterator();
		while (it.hasNext()) {
			Pieces p = it.next();

			if (p.y >= p.getGenPoint() + Math.round(sh * .018) && topFreeze
					&& p.wayback && !negPressed && !posPressed && !exitCases) {
				killx = p.x;
				killy = p.y;
				if (postScore < score) {
					postScore = score;	
					newHigh = true;
				} else
					tempyScore = score;
				tc = null;
				topFade = null;
				topFadeFinal = null;
				// Assets.tcDrone.pause();
				tLock = null;
				//				tg = null;
				currentTC = false;
				currentTG = false;
				Assets.gridDrone.stop();
				topFreeze = false;
				score = 0;
				freeze = false;
				wrongButton = false;
				recent = false;
				scoreReset = true;
				p.wayback = false;
				setPieces(new ArrayList<Pieces>());
				Assets.failSound.play(100);
				exitCases = true;
				freezeDur = 0;
				teeth = false;

				//								System.out.println("case1");
			}

			// COUGARLOCK
			else if (cLock.getStartup()){
				//				System.out.println("here");
				recent = true;
			}

			else if (p.isVisible() && !p.wayback && !freeze && currentTG
					&& p.getY() > tg.getY() - tg.getSize() && !exitCases) {
				//								System.out.println("yes3");
				p.setY((int) ((tg.getY() - tg.getSize()) - Math
						.round(sh * .017)));
				// if (currentTG){

				gridX = p.getX();
				gridY = p.getY();
				newGridPiece = true;

				// }
			}

			else if (p.isVisible() && !p.wayback && !freeze && !exitCases) {
				for (PosTriangle pt : pts) {
					if (p.getX() - Math.round(sh * .033) <= pt.getX()
							&& p.getX() + Math.round(sh * .033) >= pt.getX()
							&& p.getY() + Math.round(sh * .037) >= pt
							.getHeight()
							&& p.getY() - Math.round(sh * .021) <= pt
							.getHeight()) {
						if (!p.getSwitched()) {
							p.switchType();
						}
					}
				}

				p.update(level.getAccMod());
				newPiece = false;

				//								 System.out.println("yes4");
			}

			else if (p.wayback && collider.isLazer() && !exitCases) {
				//								System.out.println("wb1");

				tempMult = level.getScoreMult();

				if (score - 10 * level.getScoreMult() < 0) {
					score = 0;
				} else
					score -= 10 * level.getScoreMult();

				if (p.type)
					typePass = true;	
				else typePass = false;

				tcx = p.getX();
				tcy = p.getY();			

				lightningDuration = 0;
				freeze = false;
				negPressed = false;
				Assets.click.play(150);
				killx = p.x;
				killy = p.y;
				lightning = true;
				resetObs();
				it.remove();
			}

			else if (p.isVisible() && p.wayback && !freeze
					&& !exitCases && !newPiece && !topFreeze) {
				p.updateback();
				//								System.out.println("wb2");
				freeze = true;

				collider.checkCharged(p.x, p.y, scene.getLine());
				if (collider.isLazer()){
					resetObs();
					killx = p.x;
					killy = p.y;
				}
				//												 System.out.println("yes7");
			}

			else if (p.isVisible() && freeze && !exitCases && p.wayback) {
				p.updateback();
				//								System.out.println("wb3");
				collider.checkCharged(p.x, p.y, scene.getLine());
				if (collider.isLazer()){
					resetObs();
					killx = p.x;
					killy = p.y;
				}
				//												 System.out.println("yes7b");
			}

			else if (p.y < Math.round(sh * .008)) {
				cLock.setPointT();

				if (cLock.getActive()){
					tPaint.setColor(Color.GRAY);
				}
				else tPaint.setColor(Color.MAGENTA);

				tStrings.add(new ShakeString(game.getGraphics(), "+"+String.valueOf(level.getScoreMult()*1), p.x, 40, tPaint));
				pointXs.add(p.x);
				sStrings.add(new ShakeString(game.getGraphics(), String.valueOf(score+level.getScoreMult()), 
						sw / 2, (int) Math.round(sh * .84)));

				score += 1 * level.getScoreMult();
				if (!cLock.getActive()){
					if (p.type)
						Assets.posPoint.play(20);
					if (!p.type)
						Assets.negPoint.play(40);
				}


				it.remove();
				freeze = false;
				wrongButton = false;

			}
			// else if (p.y > 950&&p.wayback){
			//			 System.out.println("yes9");
			// }

			// FINAL
			else if (!exitCases
					&& p.y >= p.getGenPoint() - Math.round(sh * .012)
					&& p.wayback) {
				killx = p.x;
				killy = p.y;
				if (postScore < score) {
					postScore = score;
					newHigh = true;
				} else
					tempyScore = score;
				//				System.out.println("case10");
				tc = null;
				Assets.tcDrone.pause();
				//				tg = null;
				setPieces(new ArrayList<Pieces>());
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
				tLock = null;
				teeth = false;
			}
		}
	}

	protected int checkLane(int lane) {
		//		System.out.println("try: " + lane);
		int acc = 0;
		for (int i = 0; i < pieces.size(); i++){
			if (pieces.get(i).getX() == lane){
				acc++;
			}
		}
		if (tLock.secondLanes().contains(lane)){
			acc++;
		}
		if (tLock.thirdLanes().contains(lane)){
			acc ++;
		}
		if (tLock.deadLanes().contains(lane)){
			acc ++;
		}
		if (acc < 3)
			return lane;
		else {
			Random randomGenerator = new Random();
			return checkLane ((randomGenerator.nextInt(7)+1) * this.lane);
		}
	}

	public void fail(){
		collider.reset();
		collider = Collider.getInstance();
		tempPiece = new Pieces(0, 0, true, this, level.getRecentInterval());
		topFreezeWin = false;
		tLock = null;
		topFade = null;
		topFadeDeath = null;
		topFadeFinal = null;
		lowerDist = 0;
		freezeScore = 0;
		freeze = false;
		posPressed = false;
		wrongButton = true;
		if (postScore < score) {
			postScore = score;
			newHigh = true;
		}
		tempyScore = score;
		tc = null;
		Assets.tcDrone.pause();
		//		tg = null;
		setPieces(new ArrayList<Pieces>());
		Assets.failSound.play(150);
		currentTC = false;
		currentTG = false;
		Assets.gridDrone.stop();
		topFreeze = false;
		freezeDur = 0;
		scoreReset = true;
		score = 0;
		freeze = false;
		wrongButton = false;		
		exitCases = true;
		teeth = false;
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		Graphics g = game.getGraphics();
		Assets.theme.play();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP
					&& event.x > Math.round(sw * .188)
					&& event.x < Math.round(sw * .813)
					&& event.y < Math.round(sh * .145)) {
				touch = false;
				state = GameState.Running;
				game.getGraphics().drawRect(0, 0, game.getGraphics().getWidth()+5, 
						game.getGraphics().getHeight()+5, Color.BLACK);
				Assets.click2.play(100);
				Assets.theme.play();
			}
			if (event.type == TouchEvent.TOUCH_UP
					&& event.x > Math.round(sw * .375)
					&& event.x < Math.round(sw * .625)
					&& event.y > Math.round(sh * .705)
					&& event.y < Math.round(sh * .809)) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}

			if (event.type == TouchEvent.TOUCH_UP
					&& event.x > Math.round(sw * .281)
					&& event.x < Math.round(sw * .731)
					&& event.y > Math.round(sh * .324)
					&& event.y < Math.round(sh * .423)) {
				//				nullify();
				//				cLock = null;
				collider.reset();
				level.reset();
				tooltips.reset();
				mag.reset();
				Assets.theme.stop();
				Assets.click2.play(100);
				nullify();
				game.setScreen(new MainMenuScreen(freshGame));
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				nullify();
				game.setScreen(new MainMenuScreen(freshGame));
				return;
			}
		}
	}

	@Override
	public void paint(float deltaTime) {

		Graphics g = game.getGraphics();
		getAlert().update(10);


		// BOARD ELEMENTS / SCORE
		if (state == GameState.Running) {

			//			topFreeze = true;

			int fingerx = scene.getLine();

			if (!topFreeze) {

				if (scoreReset && scoreDeathDur < 6)

					g.drawImage(Assets.mFaced, 0, 0, scoreDeathDur*50);

				if (!scoreReset){
					g.drawRect(fingerx, 0, g.getWidth() + 3, sh + 5, Color.BLACK);
					g.drawRect(0, 0, fingerx + 2, sh + 2, Color.WHITE);
				}
				//				
				if (!freeze && !topFreeze) {
					baseGrowth = 0;
				}			

				if (touch == false) {

					drawTimer = 0;
					if ((getAlert().getImage() == Assets.alarm2)
							&& circleRad > 79) {
						circleRad += 1;
					}
					if ((getAlert().getImage() == Assets.alarm1)
							&& circleRad > 79) {
						if (circleRad > 80)
							circleRad -= 1;
					}

					//					g.drawCircOut(fingerx, fingery, circleRad, Color.RED, 5, 150);
				}
			}
			if (ControlPanel.framerate){

				int countTime = 500;
				if (frameTimer == null || frameTimer.getTrigger()){
					frames = frameCount*(1000/countTime);
					frameTimer = new PosTimer(countTime);
					frameCount = 0;	
				}
				else {
					g.drawString(String.valueOf(frames), 30, 150, paint2);
					frameTimer.update();
					frameCount++;			
				}
			}

			// SCORERESET DRAW
			if (scoreReset) {
				collider.death();
				if (!cLock.getLaugh())
					cLock.setActive(false);
				paint3.setColor(Color.RED);
				paint6.setColor(Color.RED);
				if (scoreDeathDur < 60) {
					paint11.setAlpha(240 - scoreDeathDur * 4);
					paint12.setAlpha(240 - scoreDeathDur * 4);
					int slider = scoreDeathDur/3;
					if (newHigh) {	
						g.drawString("NEW HIGH SCORE!", sw / 2 +slider,
								(int) Math.round(sh * .349) +slider, paint11);
						g.drawString(String.valueOf(postScore), sw / 2 +slider,
								(int) Math.round(sh * .456)+slider, paint12);
					} else {
						g.drawString("YA BLEW IT!", sw / 2 +slider,
								(int) Math.round(sh * .349) +slider, paint11);
						g.drawString(String.valueOf(tempyScore) , sw / 2 +slider,
								(int) Math.round(sh * .456)+slider, paint12);
					}
					failCircle(g);
					scoreDeathDur++;
				} else {
					if (collider.isSet()){
						collider.reset();
						collider = Collider.getInstance();
					}
					scoreReset = false;
					paint3.setColor(Color.GRAY);
					paint6.setColor(Color.GRAY);
					scoreDeathDur = 0;
					newHigh = false;
				}
			}

			// MULTMESSAGE
			if (level.getMessageTruth() && level.getLevel() != 1)
				level.displayMessage();

			//cLock
			if (cLock.getActive())
				cLock.draw();

			// SPRITES

			for (Pieces p : getPieces()) {

				// FALCON SWITCH
				if (p.getSwitched() && !p.wayback) {
					if (p.type) {
						p.updateTimer();
						g.drawCircFill(p.x, p.y,
								(float) Math.round(sh * .041), Color.MAGENTA,
								p.getTimer() * 10);
						if (p.getTimer() == 0)
							p.resetSwitched();
					}
					if (!p.type) {
						p.updateTimer();
						g.drawCircFill(p.x, p.y,
								(float) Math.round(sh * .041), Color.MAGENTA,
								p.getTimer() * 10);
						if (p.getTimer() <= 1)
							p.resetSwitched();
					}
				}


				if (p.type == true){
					//					if (!CougarLock.running)
					g.drawImage(Assets.pos, p.x - Assets.pos.getWidth()/2,
							p.y - Assets.pos.getHeight()/2, p.fadeTimer());

				}

				if (p.type == false){
					//					if (!CougarLock.running)
					g.drawImage(Assets.neg, p.x - Assets.neg.getWidth()/2,
							p.y - Assets.neg.getHeight()/2, p.fadeTimer());
				}

				if (p.wayback) {
					if (cLock.getActive() && !cLock.getDying())
						cLock.setLaugh();
					if (getAlert().getImage() == Assets.alarm2 && !topFreeze) {
						g.drawCircOut(p.x, p.y,
								(int) Math.round(sh * .03), Color.RED,
								(int) Math.round(sh * .013));
					}
				}

				//draw TTips
				if (freeze)
					tooltips.draw(fingerx, fingery, p, p.wayback);
			}
			if (i < 30) {
				i += 2;
			} else {
				i = 0;
				flashUpdate++;
				if (flashUpdate == 3)
					flashUpdate = 0;
			}

			// GRID

			if (recent && currentTG && newGridPiece) {

				if (gridPieceCount < 11) {

					g.drawCircFill(gridX, (int) Math.round(sh * .834),
							(int) Math.round(sh * .029), Color.GREEN,
							255 - gridPieceCount * 25);
					g.drawCircFill(gridX, gridY + (int) Math.round(sh * .012),
							(int) Math.round(sh * .029), Color.GREEN,
							255 - gridPieceCount * 25);

					gridPieceCount++;

				} else {
					gridPieceCount = 0;
					newGridPiece = false;
					gridX = (int) -(Math.round(sh * .083));
					gridY = (int) -(Math.round(sh * .083));
				}
			}

			if (tg != null) {
				// horizontal

				for (GridLine gl : tg.getGLs()) {
					if (gl.getY() < (int) Math.round(sh * .871)
							&& gl.getY() - 5 > tg.getY() - tg.getSize())
						g.drawLine(-1, gl.getY(), sw + 1, gl.getY(),
								Color.GREEN, 255, 12,
								paint10);
				}

				// vertical

				g.drawLine(1, (int) Math.round(sh * .871), 1,
						tg.getY() - tg.getSize(), Color.GREEN, 255, 12, paint10);

				g.drawLine((int) Math.round(sw * .101),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .101), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);

				g.drawLine((int) Math.round(sw * .201),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .201), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);

				g.drawLine((int) Math.round(sw * .301),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .301), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .401),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .401), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .501),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .501), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .601),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .601), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .701),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .701), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .801),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .801), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .901),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .901), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
				g.drawLine((int) Math.round(sw * .1001),
						(int) Math.round(sh * .871),
						(int) Math.round(sw * .1001), tg.getY() - tg.getSize(),
						Color.GREEN, 255, 12, paint10);
			}

			if (tgDeath) {
				if (lineDuration < 20) {

					for (GridLine gl : tgTemp.getGLs()) {
						if (gl.getY() < 1050
								&& gl.getY() - 5 > tgTemp.getY()
								- tgTemp.getSize())
							g.drawLine(-1, gl.getY(), 801, gl.getY(),
									Color.GREEN, 200 - lineDuration * 10, 12,
									paint10);
					}

					g.drawLine(1, tgTemp.getY(), 1,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(81, tgTemp.getY(), 81,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(161, tgTemp.getY(), 161,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(241, tgTemp.getY(), 241,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(321, tgTemp.getY(), 321,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(401, tgTemp.getY(), 401,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(481, tgTemp.getY(), 481,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(561, tgTemp.getY(), 561,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(641, tgTemp.getY(), 641,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(721, tgTemp.getY(), 721,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);
					g.drawLine(801, tgTemp.getY(), 801,
							tgTemp.getY() - tgTemp.getSize(), Color.GREEN,
							200 - lineDuration * 10, 12, paint10);

					lineDuration++;
				} else {
					tgDeath = false;
					lineDuration = 0;
				}
			}

			// RUN COLLIDER UPDATE			
			collider.update();
			// RUN SLIDER UPDATE
			if (!scoreReset)
				slider.drawUpdate(fingerx, (int)(sh*.951), fingerMove);

			// COUGDRAW
			if (tc != null) {
				//				g.drawCircBlue(tc.getX(), tc.getY(),
				//						(int) Math.round(sh * .075), Color.rgb(255, 215, 0), i);
				g.drawImage(cougar.getImage(), tc.getX()-cougar.getImage().getWidth()/2, tc.getY()-cougar.getImage().getHeight()/2, -1);	
				cougar.update(10);
			}

			if (tcDeath) {
				if (ballDuration < 20) {
					if (blueCoug == null)
						blueCoug = cougar.getImage().getBitmap().copy(cougar.getImage().getBitmap().getConfig() ,true);					
					g.drawImage(blueCoug, tcx-cougar.getImage().getWidth()/2, tcy-cougar.getImage().getHeight()/2, 
							200 - ballDuration * 10);
					ballDuration++;
				} else {
					tcDeath = false;
					ballDuration = 0;
					blueCoug = null;
				}
			}


			// TEETH

			if (!obsDeath)
				for (PosTriangle posT : pts) {
					if (!posT.getSide()) {
						if (posT.getX() > Math.round(sh * .083))
							g.drawImage(Assets.eagleb, posT.getX() - Assets.eagleb.getWidth()/2, 
									posT.getHeight() - Assets.eagleb.getHeight()/2, 280 - (posT.getX() / 3));
						else
							g.drawImage(Assets.eagleb, posT.getX() - Assets.eagleb.getWidth()/2, 
									posT.getHeight() - Assets.eagleb.getHeight()/2, 255);

					}
					if (posT.getSide())
						if (posT.getX() < 700)	{

							g.drawImage(Assets.eagle, posT.getX() - Assets.eagle.getWidth()/2, 
									posT.getHeight() - Assets.eagle.getHeight()/2, (posT.getX() / 3) - 247);
						}
						else 
							g.drawImage(Assets.eagle, posT.getX() - Assets.eagle.getWidth()/2, 
									posT.getHeight() - Assets.eagle.getHeight()/2, 255);
				}
			if ((freeze || cLock.getActive() || scoreReset) && pts.size() != 0) {
				obsDeath("f");
				//				obsDeath("g");
			}

			// LAUNCHERS
			if (!topFreeze) {
				if (baseGrowth <= Math.round(sw * .16))
					baseGrowth += Math.round(sw * .043);
			}

			// LIGHTNING
			//		System.out.println(collider.isLazer());
			if (collider.isLazer() && !scoreReset) {
				if (lightningDuration < 20) {
					//					System.out.println("x: "+killx);
					//					System.out.println("y: "+killy);

					collider.checkCharged(killx, killy, scene.getLine());

					if (typePass)
						g.drawImage(Assets.pos,
								killx - (int) Math.round(sw * .05), killy,
								200 - lightningDuration * 10);
					else
						g.drawImage(Assets.neg,
								killx - (int) Math.round(sw * .05), killy,
								200 - lightningDuration * 10);

					if (lightningDuration == 1){
						g.drawRect(0, 0, g.getWidth() + 1, g.getHeight() + 1,
								Color.rgb(255, 215, 0));

						// PENALTY SHAKESTRING
						Paint tempPaint = new Paint();
						tempPaint.set(paint13);
						lStrings.add(new ShakeString(g, "-" + String.valueOf(10 * tempMult), killx,
								killy - (int) Math.round(sw * .019), tempPaint));
					}

					lightningDuration++;
				} else {
					lightning = false;
					lightningDuration = 0;
					collider.reset();
					collider = Collider.getInstance();
					collider.setLazer(false);
					//					System.out.println("here");
				}	
			}

			if (j < 50) {
				j += 2;
			} else {
				j = 0;
			}
			if (CougarLock.running) {
				lg = new LinearGradient(sw / 2, -(sh / 40), sw / 2,
						(float) (sh * .033) + Math.round(j), Color.GRAY,
						Color.alpha(0), android.graphics.Shader.TileMode.CLAMP);
			}
			else {
				lg = new LinearGradient(sw / 2, -(sh / 40), sw / 2,
						(float) (sh * .033) + Math.round(j), Color.BLUE,
						Color.alpha(0), android.graphics.Shader.TileMode.CLAMP);
			}

			int magSprite = (pieces != null && pieces.size() > 0 && pieces.get(0).getSlowing()) ? pieces.get(0).y: sh;
			if (!scoreReset)
				mag.updateAndDraw(magSprite);

			// TOOLTIPS DRAW
			if (Tooltips.currentTip == Tip.MOVE)
				tooltips.drawMove();

			// SHAKERS 2
			updateShakeStrings(sStrings, true);
			updateShakeStrings(lStrings, false);
			updateShakeStrings(tStrings, false);
			updateShakeStrings(cStrings, false);
			// high holder
			if (!cLock.getActive() && !scoreReset && !collider.charged && !(Tooltips.currentTip == Tip.MOVE)){
				paint4.setAlpha(100);	
				g.drawString("high " + String.valueOf(postScore),
						sw / 2, Coil.origin.y + 85, paint4);

			}
			paint9.setShader(lg);
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
	}

	void failCircle(Graphics g){
		g.drawCircOut(killx, killy, scoreDeathDur * 60, Color.GRAY,
				10, 121 - scoreDeathDur*2);
		g.drawCircOut(killx, killy, scoreDeathDur * 68, Color.GRAY,
				10, 121 - scoreDeathDur*2);
		g.drawCircOut(killx, killy, scoreDeathDur * 52, Color.GRAY,
				10, 121 - scoreDeathDur*2);
		g.drawCircOut(killx, killy, scoreDeathDur * 76, Color.GRAY,
				10, 121 - scoreDeathDur*2);

	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;
		scene = null;
		setPieces(null);
		scene = null;
		paint2 = null;
		paint3 = null;
		//		Assets.theme = null;
		//		Assets.click = null;
		collider = null;
		mag = null;
		level = null;
		slider = null;
		//		cougL.setRunning(false);
		//		cLock = null;
		// Call garbage collector to clean up memory.
		//		System.gc();
	}

	protected void drawReadyUI() {
		Graphics g = game.getGraphics();

		// g.drawARGB(155, 0, 0, 0);
		// g.drawString("TOUCH THE SCREEN YA DUMMY",
		// 400, 300, paint);
		g.drawImage(Assets.rdscreen, 0, 0);
	}

	protected void drawRunningUI() {
		Graphics g = game.getGraphics();
	}

	protected void drawPausedUI() {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.pauseScreen, 0, 0);
	}

	protected void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER BRO", 640, 300, paint);
	}

	@Override
	public void pause() {
		if (ControlPanel.trace)
			Debug.stopMethodTracing();
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

	public static Scene getScene() {
		return scene;
	}


	public void setScore(int i) {
		score += i;
	}

	public boolean getTopFreeze() {
		return topFreeze;
	}

	public Animation getAlert() {
		return alert;
	}

	public void setAlert(Animation alert) {
		this.alert = alert;
	}

	public ArrayList<Pieces> getPieces() {
		return pieces;
	}

	public void setPieces(ArrayList<Pieces> pieces) {
		this.pieces = pieces;
	}

	//////////////// HELPERS //////////////////

	private void updateShakeStrings(ArrayList<ShakeString> shakeS, Boolean pointString){
		if (shakeS.size() != 0){

			Iterator<ShakeString> ss = shakeS.iterator();
			int position = 0;

			while (ss.hasNext()) {
				ShakeString shake = ss.next();
				shake.drawAndUpdate();
				int alph = (shake.shakeMillisLeft()/2)-50;
				if (alph < 0) alph = 0;

				if (pointString) {
					//					game.getGraphics().drawCircOut(pointXs.get(position), -15, 150-(alph/2), Color.MAGENTA,
					//							(alph/20), alph/2);
					mag.scoreLight(alph);
				}

				if (shake.shakerIsDead()){
					ss.remove();
					if (pointString)
						pointXs.remove(position);
				}
				else position++;					
			}
		}
	}

	private void obsDeath(String obsName){
		Graphics g = graph;

		// COUGAR
		if (obsName == "c"){
			if (!freeze){
				score+=level.getScoreMult()*10;
				cStrings.add(new ShakeString(graph, "+"+level.getScoreMult()*10, tc.x, tc.y, cPaint));
				Assets.TCdeath.play(100);
			}
			tcDeath = true;
			tcx = tc.x;
			tcy = tc.y;
			tc = null;
			currentTC = false;
			Assets.tcDrone.stop();
		}


		// GRID
		else if (obsName == "g"){
			if (currentTG)
				tgDeath = true;
		}


		// FALCON
		else if (obsName == "f"){
			obsDeath = true;
			if (fDeathTimer == null)
				fDeathTimer = new PosTimer(250);
			else fDeathTimer.update();

			int falcAlph = (int) (ControlPanel.alphize(fDeathTimer.getRemainingMillis(), 250));

			for (PosTriangle posT : pts) {
				int greaterAlph =  falcAlph - (255 - (280 - (posT.getX() / 3)));
				greaterAlph = (greaterAlph < 0) ? 0 : greaterAlph;
				int lessAlph = falcAlph - (255 - ((posT.getX() / 3) - 249));
				lessAlph = (lessAlph < 0) ? 0 : lessAlph;

				if (!posT.getSide()) {
					if (posT.getX() > Math.round(sh * .083)){
						g.drawImage(Assets.eagleb, posT.getX() - Assets.eagleb.getWidth()/2, 
								posT.getHeight() - Assets.eagleb.getHeight()/2, greaterAlph);
					}
					else {
						g.drawImage(Assets.eagleb, posT.getX() - Assets.eagleb.getWidth()/2, 
								posT.getHeight() - Assets.eagleb.getHeight()/2, falcAlph);
					}

				}
				if (posT.getSide())
					if (posT.getX() < 700)	{			

						g.drawImage(Assets.eagle, posT.getX() - Assets.eagle.getWidth()/2, 
								posT.getHeight() - Assets.eagle.getHeight()/2, lessAlph);
					}
					else 
						g.drawImage(Assets.eagle, posT.getX() - Assets.eagle.getWidth()/2, 
								posT.getHeight() - Assets.eagle.getHeight()/2, falcAlph);
			}



			if (fDeathTimer.getTrigger()){
				resetObs();
			}

		}


	}
	// clear obs variables
	private void resetObs(){
		fDeathTimer = null;
		pts.clear();
		obsDeath = false;
		teeth = false;
	}

}