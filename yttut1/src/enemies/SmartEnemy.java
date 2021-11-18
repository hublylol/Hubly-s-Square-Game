package enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import otherObjects.GameObject;
import otherObjects.Handler;
import otherObjects.ID;
import otherObjects.Trail;

public class SmartEnemy extends GameObject{
	
	private Handler handler;
	private GameObject player;

	public SmartEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		for(int i = 0; i < handler.getObject().size(); i++) {
			if(handler.getObject().get(i).getId() == ID.Player) player = handler.getObject().get(i);
		}
		
		
		

	}
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
		
		velX = (float) ((-1.0/distance) * diffX);
		velY = (float) ((-1.0/distance) * diffY);
		
		if(y <= 0|| y >= Game.HEIGHT - 64) velY *= (-1);
		if(x <= 0|| x >= Game.WIDTH - 32) velX *= (-1);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.04f, handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)x, (int)y, 16, 16);
	}
	
	
}