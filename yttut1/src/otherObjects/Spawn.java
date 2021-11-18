package otherObjects;

import java.util.Random;

import enemies.BasicEnemy;
import enemies.EnemyBoss;
import enemies.FastEnemy;
import enemies.HardEnemy;
import enemies.SmartEnemy;
import main.Game;
import main.HUD;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	
	public static boolean bossFight = false;
	
	public static int scoreKeep = 0;
	
	
	public Spawn(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
	}
		
	
	public void tick() {
		scoreKeep++;
		
		
		if(scoreKeep >= 250) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			if(hud.getLevel() == 10) {
				EnemyBoss.bossDone = false;
			}
			if(hud.getLevel() == 1) {
				EnemyBoss.bossDone = true;
			}
			
			if(hud.getLevel() == 15) {
				EnemyBoss.bossDone = true;
			}
			if(hud.getLevel() == 16) {
				handler.clearEnemies();
			}
			if(hud.getLevel() == 25){
				EnemyBoss.bossDone = false;
			}
			if(hud.getLevel() == 29) {
				EnemyBoss.bossDone = true;
			}
			
			
			if(Game.diff == 0) {
				if(hud.getLevel() == 2) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				}else if(hud.getLevel() == 3) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				}else if(hud.getLevel() == 4) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
				}else if(hud.getLevel() == 5) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
				}else if(hud.getLevel() == 10) {
					handler.clearEnemies();
					handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 64, -104, ID.EnemyBoss, handler));
				}else if(hud.getLevel() < 10) {
					if(r.nextInt(3) >2 ) handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
					else handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				}else if(hud.getLevel() >= 16){
					if(hud.getLevel() == 25) {
						handler.clearEnemies();
						handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 64, -104, ID.EnemyBoss, handler));
						handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 64, -104, ID.EnemyBoss, handler));
					}else if(hud.getLevel() == 30){
						handler.clearEnemies();
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				
					} else if(hud.getLevel() >= 31 || hud.getLevel() <= 24){
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
						handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				
					}
				}
			} else if(Game.diff == 1 || Game.diff == 2) {
				if(hud.getLevel() == 2) {
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				}else if(hud.getLevel() == 3) {
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				}else if(hud.getLevel() == 4) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
				}else if(hud.getLevel() == 5) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
				}else if(hud.getLevel() == 10) {
					handler.clearEnemies();
					handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 64, -104, ID.EnemyBoss, handler));
				}else if(hud.getLevel() < 10){
					if(r.nextInt(3) > 2) handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
					else handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				}else if(hud.getLevel() >= 16){
					if(hud.getLevel() == 25) {
						handler.clearEnemies();
						handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 64, -104, ID.EnemyBoss, handler));
						handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 64, -104, ID.EnemyBoss, handler));
					}else if(hud.getLevel() == 30){
						handler.clearEnemies();
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				
					} else if(hud.getLevel() >= 31 || hud.getLevel() <= 24){
						handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.SmartEnemy, handler));
						handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.FastEnemy, handler));
						handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				
					}
				}
			}
			
		}
	}
	
}