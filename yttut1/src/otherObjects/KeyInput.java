package otherObjects;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.Game;
import main.HUD;
import main.Game.STATE;

public class KeyInput extends KeyAdapter{
	
	private Handler handler;
	private boolean[] keyDown = new boolean[4];
	
	Game game;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		
		this.game = game;
		
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				//key events for player 1
				if(key == KeyEvent.VK_W) { tempObject.setVelY(-Handler.spd); keyDown[0] = true; }
				if(key == KeyEvent.VK_S) { tempObject.setVelY(Handler.spd); keyDown[1] = true; }
				if(key == KeyEvent.VK_D) { tempObject.setVelX(Handler.spd); keyDown[2] = true; }
				if(key == KeyEvent.VK_A) { tempObject.setVelX(-Handler.spd); keyDown[3] = true; }
			}
		}
		
		if(key == KeyEvent.VK_F) 
		{
			
			if(Game.gameState == STATE.Game)
			{
				if(Game.paused == true) Game.paused = false;
				else Game.paused = true;
				
				
			}
		}
		if(Game.diff == 2) {
			if(key == KeyEvent.VK_BACK_SLASH) {
				if(HUD.bounds <= 475) HUD.bounds += 50;
				if(HUD.overBounds <= 668) HUD.overBounds += 50;
				HUD.score += 100000;
			}
			if(key == KeyEvent.VK_BACK_SPACE) Spawn.scoreKeep += 250;
		}
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
		if(key == KeyEvent.VK_SPACE) {
			if(Game.gameState == STATE.Game) Game.gameState = STATE.Shop;
			else if(Game.gameState == STATE.Shop) Game.gameState = STATE.Game;
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				//key events for player 1
				if(key == KeyEvent.VK_W) keyDown[0] = false; // tempObject.setVelY(0);
				if(key == KeyEvent.VK_S) keyDown[1] = false; // tempObject.setVelY(0);
				if(key == KeyEvent.VK_D) keyDown[2] = false; // tempObject.setVelX(0);
				if(key == KeyEvent.VK_A) keyDown[3] = false; // tempObject.setVelX(0);
				
				//vertical movement
				if(!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
				
				//horizontal movement
				if(!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);
				
			}
			
		}
	
	}

}
