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

public class EnemyBoss extends GameObject{
	
	private Handler handler;
	Random r = new Random();
	
	private int timer = 80;
	private int spawn = 6;
	
	public static boolean bossDone = true;
	
	
	public boolean bossAlive = false;

	public EnemyBoss(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		velX = 0;
		velY = 2;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 96, 96);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(timer <= 0) velY = 0;
		else timer--;
		
		if(timer <= 0) {
		
			if(bossDone == false) {
				if(velX == 0) {
					if(Game.direction == 1) {
						Game.direction = 2;
						velX = 2;
					}else if(Game.direction == 2) {
						Game.direction = 1;
						velX = -2;
					}
				}
				spawn--;
				velX += 0.001f;
				if(spawn == 0) {
					handler.addObject(new EnemyBossBullet((int) x + 48, (int)y + 48, ID.BasicEnemy, handler));
					spawn = 6;
				}
			}else if(bossDone == true) {
				velY = -2;
			}
			
		}
		
		
		//if(y <= 0|| y >= Game.HEIGHT - 64) velY *= -1;
		if(x <= 0|| x >= Game.WIDTH - 112) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.1f, handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 96, 96);
	}
	
	
}