package com.jag.positron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.os.CountDownTimer;

import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.Input.TouchEvent;
import com.jag.positron.GameScreen.GameState;

public class Tutorial extends GameScreen {
	enum TutState {
		One, Two, Three, Four, Five;
	}

	TutState TState = TutState.Five;
	public Message m;
	public Message n;
	PosTimer ptime;
	private Paint paintSPRITE;
	private Paint painty;
	private boolean TwoB;
	private Message c;
	private int destroyCount;
	private Paint painty2;

	public Tutorial(Game game) {
		super(game);
		state = GameState.Running;
		score = 0;
		paintSPRITE = new Paint();
		painty = new Paint();
		painty2 = new Paint();
		TwoB = false;
		destroyCount = 0;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//UPDATE/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {


		Assets.theme.stop();
		// 1. All touch input is handled here:
		int len = touchEvents.size();

		// FRENZY UPDATE

		int fingerx = scene.getLine();
		if (frenzy) {
			f.update(this, fingerx, fingery);
		}
		if (!frenzy)
			f = null;

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
				scoreTemp += scoreTempTemp * scoreMult;
				scoreTempTemp = 0;
			}
		}

		// TOUCH
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if ((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN)
					&& event.y <= Math.round(sh * .83)
					&& ((event.x < scene.getLine() + Math.round(sh * .083)) && (event.x > scene
							.getLine() - Math.round(sh * .083)))
							&& (event.y < fingery + Math.round(sh * .083))
							&& (event.y > fingery - Math.round(sh * .083))) {
				lock = false;
				//TSTATE2||3||4
				if (TState != TutState.Two && TState != TutState.Three && TState != TutState.Four){
					if (!topFreeze) {
						if (score - scoreTemp < 0)
							score = 0;
						else {
							score -= scoreTemp;
						}
						if (scoreTemp != 0) {
							scoreTempDraw = scoreTemp;
							scoreTemp = 0;
						}
					}
				}
			}

			if (((event.type == TouchEvent.TOUCH_DRAGGED || event.type == TouchEvent.TOUCH_DOWN) 
					&& TState != TutState.Three)
					&& !lock) {
				if (event.y <= Math.round(sh * .83) && !topFreeze){
					touch = true;
					scene.setLine(event.x);
					fingery = event.y;
					circleRad = 0;
				}
				if (topFreeze) {
					touch = true;
					scene.setLine(event.x);
					fingery = event.y;
					circleRad = 0;
				}
			}

			// POSITIVE BUTTON
			if (event.type == TouchEvent.TOUCH_DOWN
					&& ((0 < event.x) && (event.x < Math.round(sh * .207)))
					&& event.y > Math.round(sh * .83)) {
				posPressed = true;
			}

			// NEGATIVE BUTTON
			if (event.type == TouchEvent.TOUCH_DOWN
					&& ((Math.round(sh * .456) < event.x) && (event.x < Math
							.round(sh * .664)))
							&& event.y > Math.round(sh * .83)) {
				negPressed = true;
			}

			// ACTIVATES ALERT
			if (event.type == TouchEvent.TOUCH_UP) {
				touch = false;
				posPressed = false;
				negPressed = false;
			}
		}
		// CHECKS AND UPDAteS:

		if (score < 50) {
			if (level1) {
				levelStart = 0;
			}
			level = 1;
			scoreMult = 1;
			recentInterval = 30;
			if (TState == TutState.Four)
				recentInterval = 60;
			if (levelStart < 68) {
				nextLevel = true;
				level1 = false;
			} else {
				nextLevel = false;
				level2 = true;
			}
		}
		if ((score >= 50) && (score < 125)) {
			if (level2) {
				levelStart = 0;
				level1 = true;
			}
			level = 1.2;
			scoreMult = 2;
			recentInterval = 27;
			if (levelStart < 68) {
				nextLevel = true;
				level2 = false;
			} else {
				nextLevel = false;
				level3 = true;
			}
		}
		if ((score >= 125) && (score < 350)) {
			if (level3) {
				levelStart = 0;
				level2 = true;
			}
			if (topFreeze)
				level = 1;
			else
				level = 1.4;
			scoreMult = 5;
			recentInterval = 24;
			if (levelStart < 68) {
				nextLevel = true;
				level3 = false;
			} else {
				nextLevel = false;
				level4 = true;
			}
		}
		if ((score >= 350) && (score < 1200)) {
			if (level4) {
				levelStart = 0;
				level3 = true;
			}
			if (topFreeze)
				level = 1;
			else
				level = 1.6;
			scoreMult = 10;
			recentInterval = 20;
			if (levelStart < 68) {
				nextLevel = true;
				level4 = false;
			} else {
				nextLevel = false;
				level5 = true;
			}
		}
		if ((score >= 1200) && (score < 2400)) {
			if (level5) {
				levelStart = 0;
				level4 = true;
			}
			if (topFreeze)
				level = 1.1;
			else
				level = 1.8;
			scoreMult = 15;
			recentInterval = 17;

			if (levelStart < 70) {
				nextLevel = true;
				level5 = false;
			} else {
				nextLevel = false;
				level6 = true;
			}
		}
		if ((score >= 2400 && score < 4000)) {
			if (level6) {
				levelStart = 0;
				level5 = true;
			}
			if (topFreeze)
				level = 1.2;
			else
				level = 2;
			scoreMult = 25;
			recentInterval = 13;
			if (levelStart < 70) {
				nextLevel = true;
				level6 = false;
			} else {
				nextLevel = false;
				level7 = true;
			}
		}

		if ((score >= 3500)) {
			if (level7) {
				levelStart = 0;
				level6 = true;
			}
			if (topFreeze)
				level = 1.2;
			else
				level = 2.3;
			scoreMult = 50;
			recentInterval = 13;
			if (levelStart < 70) {
				nextLevel = true;
				level7 = false;
			} else {
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

		if (!freeze) {
			timePassed += 1;
			if ((timePassed % recentInterval) == 0) {
				recent = false;
			}
			if ((timePassed % recentToothInterval) == 0) {
				recentTooth = false;
			}
			if (randomInt6 < 70 && (scoreMult == 15 || scoreMult == 25)) {
				teeth = true;
			}
			if (randomInt5 == 200 && scoreMult >= 50) {
				teeth = true;
			}
		}

		// timeCHARGE
		if ((scoreMult == 5 || scoreMult == 10) && !currentTC && tc == null
				&& !currentTG) {
			if (100 > randomInt3 && !topFreeze) {
				tc = new TimeCharge((randomInt2 + 1) * lane,
						(int) Math.round(sh * .78), 5);
				Assets.tcDrone.play();
				currentTC = true;
			}
		}
		if ((scoreMult >= 25) && !currentTC && tc == null) {
			if (400 > randomInt3 && !topFreeze) {
				tc = new TimeCharge((randomInt2 + 1) * lane,
						(int) Math.round(sh * .78), 5);
				Assets.tcDrone.play();
				currentTC = true;
				tc.setSpeed(5);
			}
		}

		if (tc != null && currentTC) {
			tc.update();

			if (tc.getY() <= 0) {
				tcx = tc.x;
				tcy = tc.y;
				topFreeze = true;
				pieces.clear();
				tg = null;
				pts.clear();
				tc = null;
				currentTG = false;
				Assets.tcDrone.stop();				
				currentTC = false;
				if (TState == TutState.Four)
					destroyCount++;
			}

			if ((!touch) && (currentTC))
				if ((Math.abs(tc.getY() - fingery) < Math.round(sh * .124))
						&& (Math.abs(tc.getX() - scene.getLine()) < Math
								.round(sh * .124))) {
					tcDeath = true;
					tcx = tc.x;
					tcy = tc.y;
					tc = null;
					Assets.TCdeath.play(100);
					Assets.tcDrone.stop();
					currentTC = false;
					if (TState == TutState.Four)
						destroyCount++;
				}
		}

		// timeGRID
		if ((scoreMult == 10 && scoreMult < 15) && !currentTG && tg == null
				&& !currentTC) {
			if (100 > randomInt4 && !topFreeze) {
				tg = new TimeGrid((int) Math.round(sh * .87));
				currentTG = true;
				Assets.gridDrone.play();
				gridX = -10;
				gridY = -10;
			}
		}
		if ((scoreMult >= 50) && !currentTG && tg == null) {
			if (400 > randomInt4 && !topFreeze) {
				tg = new TimeGrid((int) Math.round(sh * .87));
				currentTG = true;
				Assets.gridDrone.play();
				tg.setSizeSpeed(2);
				gridX = -10;
				gridY = -10;
			}
		}

		if (tg != null) {
			if (tg.getY() - tg.getSize() < (screenheight / 2 - (int) Math
					.round(sh * .166))) {
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
				gl.setY(gl.getY() + tg.getGridSpeed());
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
		if (freeze) {
			pts.clear();
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
		}

		if (TState != TutState.One && TState != TutState.Two && TState != TutState.Three 
				&& TState != TutState.Four && TState != TutState.Five){

			// Pieces
			if ((randomInt < chanceOfNewPiece) && !recent && !topFreeze) {
				Pieces p = new Pieces((randomInt2 + 1) * lane,
						(int) Math.round(sh * .8), randomBool, this);
				if (currentTG) {
					Assets.gridVoice.play(100);
				}
				p.wayback = false;
				p.setSpeedX(p.getSpeedX() * level);
				if (p.getSpeedX() == tempPiece.getSpeedX())
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
			//			System.out.println(getPieces().toString());

			if (p.y >= p.getGenPoint() && topFreeze
					&& p.wayback && !negPressed && !posPressed && !exitCases) {
				if (postScore < score) {
					postScore = score;
					newHigh = true;
				} else
					tempyScore = score;
				tc = null;
				// Assets.tcDrone.pause();
				tg = null;
				currentTC = false;
				currentTG = false;
				Assets.gridDrone.stop();
				topFreeze = false;
				score = 0;
				freeze = false;
				wrongButton = false;
				// recent = false;
				scoreReset = true;
				p.wayback = false;
				setPieces(new ArrayList<Pieces>());
				Assets.failSound.play(100);
				exitCases = true;
				freezeDur = 0;
				teeth = false;
				//				 System.out.println("yes1");
			}

			else if (p.isVisible() && topFreeze && !freeze && p.wayback
					&& !negPressed && !posPressed && !exitCases) {
				p.setBackspeed((int) Math.round(sh * .0124));
				if (TState == TutState.Three && !TwoB)
					p.setBackspeed(10);
				p.updateback();
				//								System.out.println("yes2");
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
				if (TState == TutState.Three && ptime != null){
					ptime.update();				
				}
				p.update();
				newPiece = false;				
			}

			else if (p.wayback && negPressed && !exitCases) {

				if (p.type) {
					if (score - 10 * scoreMult < 0) {
						score = 0;
					} else if (!topFreeze)
						score -= 10 * scoreMult;
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
					if (TState == TutState.Three)
						destroyCount++;
					if (ptime != null)
						ptime = null;

				}
				if (!p.type) {
					it.remove();
					if (ptime != null)
						ptime = null;
					this.fail();
					if (TState == TutState.Three && destroyCount > 0)
						destroyCount--;
				}
			} else if (p.wayback && posPressed) {

				if (p.type) {
					it.remove();
					if (ptime != null)
						ptime = null;
					this.fail();
					if (TState == TutState.Three && destroyCount > 0)
						destroyCount--;
				}
				if (!p.type) {
					if (score - 10 * scoreMult < 0) {
						score = 0;
					} else if (!topFreeze)
						score -= 10 * scoreMult;
					tcx = p.getX();
					tcy = p.getY();
					typePass = false;
					lightningDuration = 0;
					killx = p.x;
					killy = p.y;
					it.remove();
					if (TState == TutState.Three)
						destroyCount++;
					if (ptime != null)
						ptime = null;
					freeze = false;
					posPressed = false;
					lightning = true;
					Assets.click.play(150);
				}
			} 
			else if (!topFreeze && p.isVisible() && p.wayback && !freeze
					&& !exitCases && !newPiece) {

				if (TState == TutState.Two && ptime.getTrigger()){
					it.remove();
					if (score > 0)
						score--;
					freeze = false;
					ptime = null;
					paintSPRITE.setAlpha(255);					
				}
				else {
					if (scoreMult <= 15)
						if (TState != TutState.Three)
							p.setBackspeed(25);
					if (TState == TutState.Three && !TwoB)
						p.setBackspeed(10);
					if (scoreMult > 15)
						p.setBackspeed(30);
					p.updateback();
					freeze = true;
					if (TState == TutState.Two && ptime != null){
						ptime.update();						
					}
					//					System.out.println("yes7");
				}
			}

			else if (p.isVisible() && freeze && !exitCases && p.wayback) {
				//TSTATE2
				if (TState == TutState.Two && ptime.getTrigger()){
					it.remove();
					if (score > 0)
						score -= 1;
					freeze = false;
					ptime = null;
					paintSPRITE.setAlpha(255);				
				}		
				else{	
					if (scoreMult <= 15)
						p.setBackspeed(25);
					if (TState == TutState.Three && !TwoB)
						p.setBackspeed(10);
					if (scoreMult > 15)
						p.setBackspeed(30);
					p.updateback();
					if (TState == TutState.Two && ptime != null){
						ptime.update();						
					}
					//					System.out.println("yes7b");
				}
			}

			else if (p.y < Math.round(sh * .008)) {
				score += 1 * scoreMult;
				if (p.type)
					Assets.posPoint.play(20);
				if (!p.type)
					Assets.negPoint.play(40);

				it.remove();
				if (ptime != null)
					ptime = null;
				freeze = false;
				wrongButton = false;

			}

			// FINAL
			else if (!exitCases
					&& p.y >= p.getGenPoint()) {
				if (postScore < score) {
					postScore = score;
					newHigh = true;
				} else
					tempyScore = score;
				tc = null;
				Assets.tcDrone.pause();
				tg = null;
				setPieces(new ArrayList<Pieces>());
				Assets.failSound.play(100);
				currentTC = false;
				currentTG = false;
				Assets.gridDrone.stop();
				topFreeze = false;
				freezeDur = 0;
				it.remove();
				if (ptime != null)
					ptime = null;
				freeze = false;
				wrongButton = false;
				exitCases = true;
				scoreReset = true;
				score = 0;
				teeth = false;
			}
		}	
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//DRAW///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void paint(float deltaTime){
		// Debug.startMethodTracing();
		Graphics g = game.getGraphics();
		getAlert().update(10);


		// BOARD ELEMENTS / SCORE
		if (state == GameState.Running) {			
			int fingerx = scene.getLine();

			if (topFreeze) {
				if (!frenzy && TState != TutState.Four){
					f = new Frenzy(sw, sh);
					frenzy = true;
				}
				g.drawRect(0, 0, g.getWidth() + 3, sh + 2, Color.GRAY,
						f.getNumber() * 10);
				g.drawString(String.valueOf(score), sw / 2,
						(int) Math.round(sh * .954), paint3);
				g.drawString("high " + String.valueOf(postScore), sw / 2,
						(int) Math.round(sh * .99), paint6);
			}

			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(6);
			boolean randomBool = randomGenerator.nextBoolean();


			if (!topFreeze) {

				g.drawRect(fingerx, 0, g.getWidth() + 3, sh + 5, Color.BLACK);
				g.drawRect(0, 0, fingerx + 2, sh + 2, Color.WHITE);

				if (!freeze && !topFreeze) {
					baseGrowth = 0;
				}

				if (!touch) {				
					drawTimer = 0;
					paint4.setAlpha(255);
					if(TState != TutState.Three){
						if ((getAlert().getImage() == Assets.alarm2)
								&& circleRad > 79) {
							circleRad += 1;
						}
						if ((getAlert().getImage() == Assets.alarm1)
								&& circleRad > 79) {
							if (circleRad > 80)
								circleRad -= 1;
						}

						g.drawCircFill(fingerx, fingery, circleRad, Color.GRAY,
								(int) Math.round(sh * .075));
						g.drawCircOut(fingerx, fingery, circleRad, Color.RED, 5, 150);
						g.drawCircOut(fingerx, fingery, (circleRad / 5) * 4,
								Color.RED, 5, 150);
						g.drawCircOut(fingerx, fingery, (circleRad / 5) * 3,
								Color.RED, 5, 150);
					}

					if (TState == TutState.Three){
						g.drawImage(Assets.lock, screenwidth/2 - Assets.lock.getWidth()/2, (int) (screenheight*.7) - Assets.lock.getHeight()/2 - 3);
						g.drawCircFill(screenwidth/2, (float) (screenheight*7), circleRad, Color.GRAY,
								(int) Math.round(sh * .075));
						g.drawCircOut(screenwidth/2, (float) (screenheight*.7), circleRad, Color.RED, 5, 150);
						g.drawCircOut(screenwidth/2, (float) (screenheight*.7), (circleRad / 5) * 4,
								Color.RED, 5, 150);
						g.drawCircOut(screenwidth/2, (float) (screenheight*.7), (circleRad / 5) * 3,
								Color.RED, 5, 150);
					}
				}

				//SCORE STUFF
				if (TState != TutState.One && TState != TutState.Three && TState != TutState.Four){
					if (TState != TutState.Two){
						g.drawString("-" + String.valueOf(scoreTemp), fingerx,
								(int) fingery + (int) Math.round(sw * .019), paint4);

						if (touch == true) {					
							if (drawTimer < 40) {
								paint4.setAlpha(200 - drawTimer * 4);
								g.drawString("-" + String.valueOf(scoreTempDraw),
										sw / 2,
										(int) (Math.round(sh * .788) - (drawTimer)),
										paint4);
								drawTimer += 1;
							}
						}
					}
					if (!scoreReset && TState != TutState.Three && TState != TutState.Four) {

						g.drawString(String.valueOf(score), sw / 2,
								(int) Math.round(sh * .954), paint3);
						g.drawString(String.valueOf(score), sw / 2,
								(int) Math.round(sh * .954), paint3);
						g.drawString("high " + String.valueOf(postScore), sw / 2,
								(int) Math.round(sh * .988), paint6);
					}
					if (scoreReset) {
						paint3.setColor(Color.RED);
						paint6.setColor(Color.RED);
						if (scoreDeathDur < 40) {
							paint11.setAlpha(255 - scoreDeathDur * 6);
							paint12.setAlpha(255 - scoreDeathDur * 6);
							if (newHigh) {
								g.drawString("NEW HIGH SCORE!", sw / 2,
										(int) Math.round(sh * .349), paint11);
								g.drawString(String.valueOf(postScore), sw / 2,
										(int) Math.round(sh * .456), paint12);
							} else {
								g.drawString("YA BLEW IT!", sw / 2,
										(int) Math.round(sh * .349), paint11);
								g.drawString(String.valueOf(tempyScore), sw / 2,
										(int) Math.round(sh * .456), paint12);
							}
							g.drawString(String.valueOf(score), sw / 2,
									(int) Math.round(sh * .954), paint3);
							g.drawString("high " + String.valueOf(postScore),
									sw / 2, (int) Math.round(sh * .988), paint6);
							this.failCircle(g);
							scoreDeathDur++;
						} else {
							scoreReset = false;
							paint3.setColor(Color.BLUE);
							paint6.setColor(Color.BLUE);
							scoreDeathDur = 0;
							newHigh = false;
						}
					}
				}
				if (scoreReset && TState == TutState.Three) {
					paint3.setColor(Color.RED);
					paint6.setColor(Color.RED);
					if (scoreDeathDur < 40) {
						paint11.setAlpha(255 - scoreDeathDur * 6);
						paint12.setAlpha(255 - scoreDeathDur * 6);
						g.drawString("YA BLEW IT!", sw / 2,
								(int) Math.round(sh * .349), paint11);		
						this.failCircle(g);

						scoreDeathDur++;
					} else {
						scoreReset = false;
						paint3.setColor(Color.BLUE);
						paint6.setColor(Color.BLUE);
						scoreDeathDur = 0;
						newHigh = false;
					}
				}
				if (nextLevel && scoreMult > 1) {
					if (levelStart < 70) {
						paint5.setAlpha(255 - (levelStart * 3));
						paint7.setAlpha(255 - (levelStart * 3));
						g.drawString(String.valueOf(scoreMult), g.getWidth() / 2,
								(int) (g.getHeight() / 2 - Math.round(sh * .083)),
								paint5);
						if (scoreMult == 1) {
							g.drawString(
									"x",
									(g.getWidth() / 2)
									- (int) Math.round(sw * .063), (int) (g
											.getHeight() / 2 - Math
											.round(sh * .083)), paint7);
						}
						if ((scoreMult > 1) && (scoreMult < 10)) {
							g.drawString(
									"x",
									(g.getWidth() / 2)
									- (int) Math.round(sw * .086), (int) (g
											.getHeight() / 2 - Math
											.round(sh * .083)), paint7);
						}
						if ((scoreMult >= 10) && (scoreMult < 20)) {
							g.drawString(
									"x",
									(g.getWidth() / 2)
									- (int) Math.round(sw * .113), (int) (g
											.getHeight() / 2 - Math
											.round(sh * .083)), paint7);
						}
						if ((scoreMult >= 20)) {
							g.drawString(
									"x",
									(g.getWidth() / 2) - (int) Math.round(sw * .15),
									(int) (g.getHeight() / 2 - Math
											.round(sh * .083)), paint7);
						}
						levelStart++;
					}
				}
			}
			// SPRITES
			for (Pieces p : getPieces()) 
			{

				//SWITCHED
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
					g.drawImage(Assets.pos, p.x - Assets.pos.getWidth()/2,
							p.y - Assets.pos.getHeight()/2, paintSPRITE);
					// TSTATE2
					if (TState == TutState.Two && !p.wayback && !TwoB){
						Paint painty = new Paint();
						painty.setTypeface(Assets.font);
						painty.setTextSize(Math.round(sh * .05));
						painty.setTextAlign(Paint.Align.CENTER);
						painty.setAntiAlias(true);
						painty.setColor(Color.MAGENTA);
						if (p.x < scene.getLine()){							
							g.drawString("yep", p.x, p.y+(int)Math.round(sh *.1), painty);
						}
						if (p.x > scene.getLine()){							
							g.drawString("nope", p.x, p.y+(int)Math.round(sh *.1), painty);
						}						
					}
					// TSTATE3
					if (TState == TutState.Three){
						if (ptime == null)					
							ptime = new PosTimer(255);
						if (ptime != null && (ptime.getRemainingMillis())>10 && ptime.getRemainingMillis()<10000){
							paintSPRITE.setAlpha((int) (255-ptime.getRemainingMillis()));
						}
					}
				}

				if (p.type == false){

					g.drawImage(Assets.neg, p.x - Assets.neg.getWidth()/2,
							p.y - Assets.neg.getHeight()/2, paintSPRITE);
					// TSTATE2
					if (TState == TutState.Two && !p.wayback && !TwoB){
						Paint painty = new Paint();
						painty.setTypeface(Assets.font);
						painty.setTextSize(Math.round(sh * .05));
						painty.setTextAlign(Paint.Align.CENTER);
						painty.setAntiAlias(true);
						painty.setColor(Color.MAGENTA);
						if (p.x < scene.getLine()){							
							g.drawString("nope", p.x, p.y+(int)Math.round(sh *.1), painty);
						}
						if (p.x > scene.getLine()){							
							g.drawString("yep", p.x, p.y+(int)Math.round(sh *.1), painty);
						}
					}
					// TSTATE3
					if (TState == TutState.Three){
						if (ptime == null)					
							ptime = new PosTimer(255);
						if (ptime != null && (ptime.getRemainingMillis())>10 && ptime.getRemainingMillis()<10000){
							paintSPRITE.setAlpha((int) (255-ptime.getRemainingMillis()));
						}
					}
				}

				if (p.wayback) {
					if (TState != TutState.Two){
						if (getAlert().getImage() == Assets.alarm2 && !topFreeze) {
							g.drawCircOut(p.x, p.y,
									(int) Math.round(sh * .03), Color.RED,
									(int) Math.round(sh * .013));
						}
					}
					if (TState == TutState.Two){
						if (ptime == null)					
							ptime = new PosTimer(350);
						paintSPRITE.setAlpha((int) (ptime.getRemainingMillis()/4));
					}
				}
				if (p.wayback) {
					if (topFreeze && p.equals(getPieces().get(0))) {
						g.drawCircFill(p.x, p.y + (int) Math.round(sh * .029),
								(int) Math.round(sh * .03), Color.YELLOW,
								205 - p.y / 6);
					}
				}
			}
			if (i < 30) {
				i += 2;
			} else {
				i = 0;
				flashUpdate++;
				if (flashUpdate == 3)
					flashUpdate = 0;
			}
			if (tc != null) {
//				g.drawCircBlue(tc.getX(), tc.getY(),
//						(int) Math.round(sh * .075), Color.rgb(255, 215, 0), i);
				
				g.drawImage(cougar.getImage(), tc.getX()-cougar.getImage().getWidth()/2, tc.getY()-cougar.getImage().getHeight()/2);	
				cougar.update(10);
			}

			// FRENZY DRAW
			if (f != null) {
				int inner = 0;
				int middle = 0;
				int outer = 0;
				int one = 60;
				int two = 100;
				int three = 140;
				if (flashUpdate == 0){
					inner = one;
					middle = two;
					outer = three;
				}
				else if (flashUpdate == 1){
					inner = three;
					middle = one;
					outer = two;
				}
				else if (flashUpdate == 2){
					inner = two;
					middle = three;
					outer = one;
				}
				// g.drawCircOut(fingerx, fingery, (int) Math.round(sh*.075),
				// Color.BLUE, (int) Math.round(sh*.020), 75);
				g.drawLine(f.getBottomBar() - f.getBbarSize(),
						(int) Math.round(sh * .77),
						f.getBottomBar() + f.getBbarSize(),
						(int) Math.round(sh * .77), Color.BLUE, outer,
						(int) Math.round(sh * .020));
				g.drawCircOut(fingerx, (float) ((sh * .77)+(sw*.1)), (float) (((sw*.1) / 5) * 4),
						Color.BLUE, 7, outer);
				g.drawCircOut(fingerx, (float) ((sh * .77)+(sw*.1)), (float) (((sw*.1) / 5) * 3),
						Color.BLUE, 7, middle);
				g.drawCircOut(fingerx, (float) ((sh * .77)+(sw*.1)), (float) (((sw*.1) / 5) * 2),
						Color.BLUE, 7, inner);
				for (TimeCharge t : f.getFArray()) {
					g.drawCircBlue(t.getX(), t.getY(),
							(int) Math.round(sh * .015),
							Color.rgb(255, 215, 0), i);
				}

			}
			if (tcDeath) {
				if (ballDuration < 20) {
					g.drawCircFill(tcx, tcy, Math.round(sh * .083), Color.BLUE,
							200 - ballDuration * 10);
					ballDuration++;
				} else {
					tcDeath = false;
					ballDuration = 0;
				}
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

			// TEETH

			for (PosTriangle posT : pts) {
				if (!posT.getSide()) {
					if (posT.getX() > Math.round(sh * .083))
						g.drawPosTri(posT, 280 - (posT.getX() / 3));
					else
						g.drawPosTri(posT, 255);
				}
				if (posT.getSide())
					if (posT.getX() < 700)
						g.drawPosTri(posT, (posT.getX() / 3) - 247);
					else
						g.drawPosTri(posT, 255);

				// g.drawPosTri(1, 200, 255);
			}
			// LAUNCHERS
			int launchCol = 0;
			if (!topFreeze){
				launchCol = Color.GRAY;
				g.drawCircFill(lane, Math.round(sh * .83), 
						Math.round(sw * .05), launchCol, 100);
				g.drawCircFill(lane * 2, Math.round(sh * .83),
						Math.round(sw * .05), launchCol, 100);
				g.drawCircFill(lane * 3, Math.round(sh * .83),
						Math.round(sw * .05), launchCol, 100);
				g.drawCircFill(lane * 4, Math.round(sh * .83),
						Math.round(sw * .05), launchCol, 100);
				g.drawCircFill(lane * 5, Math.round(sh * .83),
						Math.round(sw * .05), launchCol, 100);
				g.drawCircFill(lane * 6, Math.round(sh * .83),
						Math.round(sw * .05), launchCol, 100);
				g.drawCircFill(lane * 7, Math.round(sh * .83),
						Math.round(sw * .05), launchCol, 100);
			}

			if (TState != TutState.One && TState != TutState.Two){
				if (freeze) {
					g.drawImage(elecBase.getImage(), (int) Math.round(sw * .76),
							(int) Math.round(sh * .88));
					g.drawImage(elecBase2.getImage(), (int) Math.round(sw * .08),
							(int) Math.round(sh * .88));
					elecBase.update(10);
					elecBase2.update(10);
					if (baseGrowth <= Math.round(sw * .16))
						baseGrowth += Math.round(sw * .043);

					int buttonCol = 0;
					if (!topFreeze)
						buttonCol = Color.BLUE;
					if (topFreeze)
						buttonCol = Color.YELLOW;

					g.drawCircOut(Math.round(sw * .163), Math.round(sh * .93),
							baseGrowth + Math.round(sw * .006), buttonCol,
							(int) Math.round(sw * .013), 140);
					g.drawCircOut(Math.round(sw * .163), Math.round(sh * .93),
							(baseGrowth / 5) * 4 + Math.round(sw * .006),
							buttonCol, (int) Math.round(sw * .013), 140);
					g.drawCircOut(Math.round(sw * .163), Math.round(sh * .93),
							(baseGrowth / 5) * 3 + Math.round(sw * .006),
							buttonCol, (int) Math.round(sw * .013), 140);
					g.drawCircOut(Math.round(sw * .84), Math.round(sh * .93),
							baseGrowth + Math.round(sw * .006), buttonCol,
							(int) Math.round(sw * .013), 120);
					g.drawCircOut(Math.round(sw * .84), Math.round(sh * .93),
							(baseGrowth / 5) * 4 + Math.round(sw * .006),
							buttonCol, (int) Math.round(sw * .013), 140);
					g.drawCircOut(Math.round(sw * .84), Math.round(sh * .93),
							(baseGrowth / 5) * 3 + Math.round(sw * .006),
							buttonCol, (int) Math.round(sw * .013), 140);
				}

				// LIGHTNING
				if (lightning) {
					if (lightningDuration < 20) {

						paint13.setAlpha(200 - lightningDuration * 10);
						if (TState != TutState.Three)
							g.drawString("-" + String.valueOf(10 * scoreMult), killx,
									killy - (int) Math.round(sw * .019), paint13);

						g.drawLine(lane, (int) Math.round(sh * .81), killx, killy
								+ (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);
						g.drawLine(lane * 2, (int) Math.round(sh * .81), killx,
								killy + (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);
						g.drawLine(lane * 3, (int) Math.round(sh * .81), killx,
								killy + (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);
						g.drawLine(lane * 4, (int) Math.round(sh * .81), killx,
								killy + (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);
						g.drawLine(lane * 5, (int) Math.round(sh * .81), killx,
								killy + (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);
						g.drawLine(lane * 6, (int) Math.round(sh * .81), killx,
								killy + (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);
						g.drawLine(lane * 7, (int) Math.round(sh * .81), killx,
								killy + (int) Math.round(sw * .048), Color.BLUE,
								200 - lightningDuration * 10, 12);

						g.drawCircFill(lane, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);
						g.drawCircFill(lane * 2, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);
						g.drawCircFill(lane * 3, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);
						g.drawCircFill(lane * 4, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);
						g.drawCircFill(lane * 5, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);
						g.drawCircFill(lane * 6, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);
						g.drawCircFill(lane * 7, (int) Math.round(sh * .83),
								(int) Math.round(sw * .043), Color.BLUE,
								200 - lightningDuration * 10);

						if (typePass)
							g.drawImage(Assets.pos,
									tcx - (int) Math.round(sw * .05), tcy,
									200 - lightningDuration * 10);
						else
							g.drawImage(Assets.neg,
									tcx - (int) Math.round(sw * .05), tcy,
									200 - lightningDuration * 10);

						if (lightningDuration == 1)
							g.drawRect(0, 0, g.getWidth() + 1, g.getHeight() + 1,
									Color.rgb(255, 215, 0));
						lightningDuration++;
					} else {
						lightning = false;
						lightningDuration = 0;
					}
				}
			}

			// HEADER
			if (topFreeze) {
				// if (freezeDur < 250) {
				if (i < 30) {
					i += 1;
				} else {
					i = 0;
					flashUpdate++;
					if (flashUpdate == 3)
						flashUpdate = 0;
				}
				paint8.setAlpha(f.getNumber() * 10);
				g.drawRect2(-5, 0, g.getWidth() + 1, (int) f.getTopBar() + 1,
						Color.rgb(255, 215, 0), paint8);

				if (f.isBarShrink() && i != 0) {
					g.drawRect(-5, (int) f.getTopBar(), g.getWidth() + 1, i,
							Color.BLUE, 255 - (i * 7));
					f.setBarShrink(false);
				}

				else
					g.drawRect2(-5, (int) f.getTopBar(), g.getWidth() + 1, i,
							Color.rgb(255, 215, 0), paint8);

				g.drawCircOut((int) Math.round(sw * .5), 0, i * 50,
						Color.YELLOW, 10, f.getNumber() * 10);
			}

			else {
				if (j < 50) {
					j += 2;
				} else {
					j = 0;
				}
				lg = new LinearGradient(sh / 2, -(sh / 240), sh / 2,
						(float) (sh * .033) + Math.round(j)/2, Color.BLUE,
						Color.alpha(0), android.graphics.Shader.TileMode.CLAMP);
				//				System.out.println("CR: " + circleRad);
				paint9.setShader(lg);
				g.drawLine(0, (int)(j*.01), g.getWidth(), (int)(j*.01), Color.BLUE, 255, 3, paint9);
				g.drawLine(0, (int)(j*.2), g.getWidth(), (int)(j*.2), Color.BLUE, 255, 3, paint9);
				g.drawLine(0, (int)(j*.4), g.getWidth(), (int)(j*.4), Color.BLUE, 255, 3, paint9);
				g.drawLine(0, (int)(j*.6), g.getWidth(), (int)(j*.6), Color.BLUE, 255, 3, paint9);
				g.drawLine(0, (int)(j*.8), g.getWidth(), (int)(j*.8), Color.BLUE, 255, 3, paint9);
				g.drawLine(0, (int)j, g.getWidth(), (int)j, Color.BLUE, 255, 3, paint9);
				magnet.update(10);
			}
			// NEW TUTSTUFF

			// TSTATE ONE
			if (TState == TutState.One){
				if (n == null){
					n = new Message(fingerx, (int) (fingery- (sh*.1)), 
							100, 1f, "MOVE THIS!", .075f, g, Color.MAGENTA);
					ptime = new PosTimer(3000); 
					fingery = (float) (sh*.75);
				}

				if (n != null && n.isAlive()){
					if (touch && ptime!=null){
						ptime.update();
					}
					n.drawMessage();
					n.setX(fingerx);
					n.setY((int) (int) (fingery- (sh*.1)));
					if (ptime.getTrigger()){
						n.triggerFade();
					}
				}
				else {
					if (m == null){
						m = new Message(sw/2, (int)(.415*sh), 1f, .5f , "NICE ONE", .1f, g, Color.RED);
						// PLAY SUCCESS BOOM					
					}
					m.drawMessage();
					m.growM(.2f);
					if (!m.isAlive()){
						TState = TutState.Two;
						n = null;
						m = null;
						ptime = null;
					}
				}
			}

			// TSTATE TWO
			if (TState == TutState.Two){

				if (m == null)
					m = new Message(sw/2, (int)(sh*.415) , 1f, .5f, "GET POINTS!", .1f, g, Color.MAGENTA);						
				if (m.isAlive())
					m.drawMessage();

				else			
					if (score < 15){
						if (pieces.size() == 0){
							pieces.add(new Pieces((randomInt + 1) * lane,
									(int) Math.round(sh * .8), randomBool, this));
							painty.setTypeface(Assets.font);
							painty.setTextSize(Math.round(sh * .6));
							painty.setTextAlign(Paint.Align.CENTER);
							painty.setAntiAlias(true);
							painty.setColor(Color.CYAN);
						}					
						painty.setAlpha(score * 9);
						g.drawString(String.valueOf(score), sw/2, sh/2, painty);
					}
					else{
						if (n == null){
							if (!TwoB)
								n = new Message(sw/2, (int)(.415*sh), 1f, .5f , "YA DID IT", .1f, g, Color.RED);
							else 
								n = new Message(sw/2, (int)(.415*sh), 1f, .5f , "WHOA.", .1f, g, Color.RED);
							// PLAY SUCCESS BOOM
						}
						int alp = (int) (score * 9 - n.elapsedTime*100);
						if (alp < 0)
							alp = 0;
						painty.setAlpha(alp);
						g.drawString(String.valueOf(score), sw/2, sh/2, painty);
						n.drawMessage();
						n.growM(.2f);					
						if (!n.isAlive()){
							ptime = null;							
							if (TwoB){
								TState = TutState.Three;
								n = null;
								m = null;
								ptime = null;
								TwoB = false;
							}
							else{
								if (c == null){
									String s = "NO MORE HELPING";
									c = new Message(sw/2, (int)(sh*.415) , 1f, .5f, s, .075f, g, Color.MAGENTA);
								}
								if (c.isAlive())
									c.drawMessage();
								else{
									TwoB = true;
									score = 0;
									n = null;
									c = null;
								}
							}
						}
					}
			}
			// TSTATE THREE
			if (TState == TutState.Three){
				// THINGS: lock mover, neg on white // pos on black
				// slow down the back speed
				scene.setLine(sw/2);
				if (destroyCount < 10){
					int alp = destroyCount*20;
					painty.setAlpha(alp);				
					g.drawString(String.valueOf(destroyCount), sw/2, sh/2, painty);
				}

				if (m == null){
					m = new Message(sw/2, (int)(sh*.415) , 1f, .5f, "IF YOU MISS...", .075f, g, Color.MAGENTA);	
				}
				if (m.isAlive())
					m.drawMessage();

				else 
					if (destroyCount < 10){

						if (pieces.size() == 0){
							pieces.add(new Pieces((randomInt + 1) * lane,
									(int) Math.round(sh * .8), randomBool, this));
							if (!pieces.get(0).type){
								pieces.get(0).x = lane*2;
							}
							if (pieces.get(0).type){
								pieces.get(0).x = lane*6;
							}
							pieces.get(0).setY((int)(sh*.350));
							pieces.get(0).wayback = false;
							exitCases = false;					
							freeze = false;
							ptime = null;
							paintSPRITE.setAlpha(0);
							painty.setTypeface(Assets.font);
							painty.setTextSize(Math.round(sh * .6));
							painty.setTextAlign(Paint.Align.CENTER);
							painty.setAntiAlias(true);
							painty.setColor(Color.CYAN);
						}
					}

					else {
						if (c == null){
							if (!TwoB)
								c = new Message(sw/2, (int)(sh*.415), 1f, .5f, "FASTERRRRR", .1f, g, Color.MAGENTA);
							else
								c = new Message(sw/2, (int)(sh*.415), 1f, .5f, "YEEEAAA", .1f, g, Color.MAGENTA);
						}
						int alp2 = (int) (destroyCount*20 - c.elapsedTime*200);
						if (alp2 < 0)
							alp2 = 0;
						painty.setAlpha(alp2);
						g.drawString("10", sw/2, sh/2, painty);
						c.drawMessage();
						c.growM(.2f);
						if (!c.isAlive()){
							if (!TwoB){
								TwoB = true;
								destroyCount = 0;
								c = null;
							}
							else{
								TState = TutState.Four;
								n = null;
								m = null;
								c = null;
								ptime = null;
								TwoB = false;
								destroyCount = 0;
							}
						}
					}

				if (pieces.size() != 0){
					if (pieces.get(0).type && n == null)
						n = new Message((int) Math.round(sw * .84), (int) Math.round(sh * .95) , 20f, .62f, "HERE", .05f, g, Color.MAGENTA);
					if (!pieces.get(0).type && n == null)
						n = new Message((int) Math.round(sw * .16), (int) Math.round(sh * .95) , 20f, .62f, "HERE", .05f, g, Color.MAGENTA);
					if (n.isAlive() && pieces.get(0).wayback){
						n.drawMessage();
						float grower;
						if (!TwoB) grower = 3f;
						else grower = 6f;
						n.growM(grower);						
					}
					else n = null;
				}
			}

			// TSTATE FOUR
			if (TState == TutState.Four){
				if (!touch){
					ptime = null;
					painty.setAlpha(0);
				}
				painty2.setTypeface(Assets.font);
				painty2.setTextSize(Math.round(sh * .6));
				painty2.setTextAlign(Paint.Align.CENTER);
				painty2.setAntiAlias(true);
				painty2.setColor(Color.CYAN);
				if (destroyCount < 10){
					int alp = destroyCount*20;
					painty2.setAlpha(alp);				
					g.drawString(String.valueOf(destroyCount), sw/2, sh/2, painty2);
				}
				if (m == null){
					m = new Message(sw/2, (int)(sh*.415) , 1f, .5f, "DESTROY THESE!", .075f, g, Color.RED);	
				}		
				if (m.isAlive())
					m.drawMessage();
				else {
					if (!currentTC && destroyCount < 10){
						tc = new TimeCharge((randomInt + 1) * lane,
								(int) Math.round(sh * .78), 5);
						Assets.tcDrone.play();
						currentTC = true;
						painty.setTypeface(Assets.font);
						painty.setTextSize(Math.round(sh * .075));
						painty.setTextAlign(Paint.Align.CENTER);
						painty.setAntiAlias(true);
						painty.setColor(Color.MAGENTA);
					}
					else if (currentTC) {
						int liftX = tc.getX();
						if (liftX > sw*.65)
							liftX = (int) (sw*.65);
						if (liftX < sw*.35)
							liftX = (int) (sw*.35);

						if (touch && fingerx < tc.getX()+(sw*.2) && fingerx > tc.getX()-(sw*.2) 
								&& fingery < tc.getY()+(sw*.2) && fingery > tc.getY()-(sw*.2)){
							if (ptime == null)
								ptime = new PosTimer(255);
							if (!ptime.getTrigger())
								ptime.update();
							int minusALP = (int) ptime.getRemainingMillis();
							if (minusALP > 230)
								minusALP = 230;
							painty.setAlpha((int) (230-minusALP));
							g.drawString("LIFT!", tc.getX(), (int) (tc.getY()+(sw*.3)), painty);
						}
					}
				}
				if (destroyCount == 10){
					if (c == null)
						c = new Message(sw/2, (int)(sh*.415) , 1f, .5f, "YEP.", .075f, g, Color.RED);
					if (c.isAlive()){
						int alp2 = (int) (destroyCount*20 - c.elapsedTime*200);
						if (alp2 < 0)
							alp2 = 0;
						painty2.setAlpha(alp2);
						g.drawString("10", sw/2, sh/2, painty2);
						c.drawMessage();
						c.growM(.2f);
					}
					else{
						TState = TutState.Five;
						n = null;
						m = null;
						c = null;
						ptime = null;
						TwoB = false;
						destroyCount = 0;					
					}
				}
			}
			if (TState == TutState.Five){

				if (m == null){
					m = new Message(sw/2, (int)(sh*.415) , 1f, .5f, "NOW EVERYTHING!", .075f, g, Color.RED);
				}
				
				if (m.isAlive())
					m.drawMessage();
				
				if (ptime == null)
					ptime = new PosTimer(500);								
				
				else if (ptime.getTrigger() && !freeze){
					pieces.add(new Pieces((randomInt + 1) * lane,
							(int) Math.round(sh * .8), randomBool, this));

					ptime = null;
					freeze = false;
					exitCases = false;
					newPiece = true;
					recent = true;
				}
				if (ptime != null){
				ptime.update();
				
				System.out.println(ptime.getRemainingMillis());
				}
				
//				System.out.println("freeze: " + freeze);
			}
		}

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
		g.drawCircOut(sw/2, (int) Math.round(sh * .988), scoreDeathDur * 60, Color.RED,
				10, 255 - scoreDeathDur * 6);
		g.drawCircOut(sw/2, (int) Math.round(sh * .988), scoreDeathDur * 68, Color.YELLOW,
				10, 255 - scoreDeathDur * 6);
		g.drawCircOut(sw/2, (int) Math.round(sh * .988), scoreDeathDur * 52, Color.MAGENTA,
				10, 255 - scoreDeathDur * 6);
		g.drawCircOut(sw/2, (int) Math.round(sh * .988), scoreDeathDur * 76, Color.GREEN,
				10, 255 - scoreDeathDur * 6);
	}
}


// TigerHeadTopFreeze
// 