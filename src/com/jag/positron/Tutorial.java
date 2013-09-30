package com.jag.positron;

import java.util.List;
import android.graphics.Color;
import com.jag.framework.Game;
import com.jag.framework.Graphics;
import com.jag.framework.Input.TouchEvent;
import com.jag.positron.GameScreen.GameState;

public class Tutorial extends GameScreen {
	enum TutState {
		One, Two, Three, Four;	
	}
	
	TutState tstate = TutState.One;
	
//	int fingerx = game.getGraphics().getWidth()/2;
//	int fingery = game.getGraphics().getHeight()/2;

	public Tutorial(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//UPDATE/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@Override
	public void update(float deltaTime) {		
//		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();	
		super.update(deltaTime);

		if (tstate == TutState.One){
			updateOne(touchEvents);
		}
		if (tstate == TutState.Two){
//			updateTwo(touchESSSvents);
		}
		if (tstate == TutState.Three){
//			updateThree(touchEvents);
		}
		if (tstate == TutState.Four){
//			updateFour(touchEvents);
		}
	}

	private void updateOne(List<TouchEvent> touchEvents) {
		
	}	

	private void updateTwo(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub

	}

	private void updateThree(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub

	}

	private void updateFour(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//DRAW///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void paint(float deltaTime){
		Graphics g = game.getGraphics();
		super.paint(deltaTime);


		if (tstate == TutState.One){
			drawOne(g);
		}
		if (tstate == TutState.Two){
//			drawTwo(g);
		}
		if (tstate == TutState.Three){
//			drawThree(g);
		}
		if (tstate == TutState.Four){
//			drawFour(g);
		}		
	}

	private void drawOne(Graphics g) {
//		g.drawRect(50, 50, 50, 50, Color.BLUE);
	}

	private void drawTwo(Graphics g) {
		// TODO Auto-generated method stub

	}

	private void drawThree(Graphics g) {
		// TODO Auto-generated method stub

	}

	private void drawFour(Graphics g) {
		// TODO Auto-generated method stub

	}




}
