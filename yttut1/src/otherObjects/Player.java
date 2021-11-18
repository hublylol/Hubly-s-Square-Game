package otherObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import enemies.EnemyBoss;
import main.Game;
import main.HUD;

public class Player extends GameObject{
	
	Random r = new Random();
	Handler handler;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
	}
		
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y, 32, 32);
	}
	

	public void tick() {
		
		
		x += velX;
		y += velY;
		

		if(EnemyBoss.bossDone == false) {
			x = Game.clamp(x, 0, Game.WIDTH - 50);
			y = Game.clamp(y, 150, Game.HEIGHT - 70);
		}else {
			x = Game.clamp(x, 0, Game.WIDTH - 50);
			y = Game.clamp(y, 0, Game.HEIGHT - 70);
		}
				
		handler.addObject(new Trail(x, y, ID.Trail, Game.playerColour, 32, 32, 0.08f, handler));
			
		collision();
	}
	
	public void collision() {
		for(int i = 0; i < handler.getObject().size(); i++) {
			
			GameObject tempObject = handler.getObject().get(i);
			
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy) { // tempobject is now basic enemy 
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					if(HUD.OVERHEALTH >= 2) {
						HUD.OVERHEALTH -= 3;
						HUD.redOverHealth -= 3;
						HUD.redHealthTimer = 60;
					}else {
						HUD.HEALTH -= 3;
						HUD.redHealth -= 3;
						HUD.redHealthTimer = 60;
					}
					
					HUD.damageTaken += 3;
				}
			}
		}
	}

	public void render(Graphics g) {
		if(id == ID.Player) g.setColor(Game.playerColour);
		g.fillRect((int)x, (int)y, 32, 32);
	}
	
}
