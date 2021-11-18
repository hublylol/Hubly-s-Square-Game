package enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.Game;
import otherObjects.GameObject;
import otherObjects.Handler;
import otherObjects.ID;
import otherObjects.Trail;

public class HardEnemy extends GameObject{
	
	private Handler handler;
	private Random r = new Random();
	
	private boolean speedChange = false;

	public HardEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = r.nextInt(8);
		velY = r.nextInt(8);
		
		if(velX < 4) velX += 4;
		if(velY < 4) velY += 4;
		
		if(velX == 0) velX = -6;
		if(velY == 0) velY = -6;
		
	}
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(speedChange == false) {
			if(y <= 0|| y >= Game.HEIGHT - 64) velY = (float) (velY * -1.05);
		}else if(y <= 0|| y >= Game.HEIGHT - 64) velY = (float) (velY * -1);
		if((float) (velY) > 13) {
			velY = 13;
			speedChange = true;
		}
		if((float) (velY) < -13) {
			velY = -13;
			speedChange = true;
		}
		if(speedChange == true) {
			if(x <= 0|| x >= Game.WIDTH - 32) velX = (float) (velX * -1.05);
		}else if(x <= 0|| x >= Game.WIDTH - 32) velX = (float) (velX * -1);
		if((float) (velX) > 13) {
			velX = 13;
			speedChange = true;
		}
		if((float) (velX) < -13) {
			velX = -13;
			speedChange = true;
		}
			
		handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, 16, 16, 0.04f, handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
	
}
